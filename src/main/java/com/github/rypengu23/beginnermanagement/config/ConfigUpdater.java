package com.github.rypengu23.beginnermanagement.config;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.util.CheckUtil;
import jp.jyn.jbukkitlib.config.YamlLoader;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

public class ConfigUpdater {

    private Plugin plugin;

    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public ConfigUpdater() {
        this.plugin = BeginnerManagement.getInstance();
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public boolean configUpdateCheck() {

        boolean checkFlag = false;

        Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.ConfigUpdater_CheckUpdateConfig);
        if (mainConfig.getVersion().compareTo(BeginnerManagement.pluginVersion) < 0) {
            Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.ConfigUpdater_UpdateConfig);
            migrateNewConfig(1);
            checkFlag = true;
        }
        if (messageConfig.getVersion().compareTo(BeginnerManagement.pluginVersion) < 0) {
            Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.ConfigUpdater_NoConfigUpdates);
            migrateNewConfig(2);
            checkFlag = true;
        }
        return checkFlag;
    }


    /**
     * Configを新しいバージョンに移行。
     * type=1 : MainConfig
     * type=2 : MessageConfig
     *
     * @param type
     */
    public void migrateNewConfig(int type) {

        CheckUtil checkUtil = new CheckUtil();
        Configuration configuration;

        String configFileName = "config.yml";
        String messageFileNameEn = "message_en.yml";
        String messageFileNameJa = "message_ja.yml";

        YamlLoader mainLoader = new YamlLoader(plugin, configFileName);
        YamlLoader messageLoaderEn = new YamlLoader(plugin, messageFileNameEn);
        YamlLoader messageLoaderJa = new YamlLoader(plugin, messageFileNameJa);

        HashMap<String, YamlLoader> updateConfigList = new HashMap<>();
        if (type == 1) {
            updateConfigList.put(configFileName, mainLoader);
        } else {
            updateConfigList.put(messageFileNameEn, messageLoaderEn);
            updateConfigList.put(messageFileNameJa, messageLoaderJa);
        }

        for (Map.Entry<String, YamlLoader> entry : updateConfigList.entrySet()) {
            //現在のConfigを読み込み
            configuration = entry.getValue().getConfig();

            String fileName = entry.getKey();

            //古いConfigをバックアップ
            File oldConfigFile = new File(plugin.getDataFolder(), fileName);
            if(!oldConfigFile.exists()){
                //ファイルが存在しない場合
                continue;
            }

            //ファイル名用に現在日時取得
            //ファイル名用日付取得
            Date nowDate = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd_HHmm");

            Double version = 1.0;
            if (type == 1) {
                version = mainConfig.getVersion();
            } else {
                version = messageConfig.getVersion();
            }

            //古いConfigのファイル名変更
            oldConfigFile.renameTo(new File(plugin.getDataFolder(), fileName.split("\\.")[0] + "_Ver" + version + "-" + format.format(nowDate) + ".yml.old"));

            //古いConfigを削除
            //configFile.delete();

            //新しいConfigの配置
            configLoader.reloadConfig();

            //ファイルを取得
            File configFile = new File(plugin.getDataFolder(), fileName);

            //Configを全行取得
            List<String> ymlLines = new ArrayList<>();
            try {
                ymlLines = Files.readAllLines(configFile.toPath(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                Bukkit.getLogger().warning("[BeginnerManagement] New config file read failure.");
            }

            //Configを削除
            configFile.delete();

            //新しいymlファイルの作成
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                Bukkit.getLogger().warning("[BeginnerManagement] New config file create failure.");
            }

            //書き出し

            Map<String, String> typeList = mainConfig.getConfigTypeList();
            try {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(configFile), StandardCharsets.UTF_8);
                BufferedWriter bufferedWriter = new BufferedWriter(osw);

                String sectionName = "";

                for (String line : ymlLines) {

                    //空白の場合、空白出力してスキップ
                    if (line.trim().length() == 0) {
                        bufferedWriter.append("");
                        bufferedWriter.newLine();
                        continue;
                    }
                    //コメント行の場合、内容出力してスキップ
                    if ("#".equals(String.valueOf(line.trim().charAt(0)))) {
                        bufferedWriter.append(line);
                        bufferedWriter.newLine();
                        continue;
                    }
                    //パラメーター名の右の値を削除して置き換え
                    //左右の値を取得
                    String[] lineContent = line.split(":", 2);

                    //セクション外の値である場合、記憶したセクション名をリセット
                    if (!checkUtil.checkNullOrBlank(sectionName) && !String.valueOf(lineContent[0].charAt(0)).equals(" ")) {
                        sectionName = "";
                    }

                    //:より右に何も無いor空白の場合、セクション名を記憶し、スキップ
                    if (lineContent[1].trim().length() == 0) {
                        sectionName = lineContent[0];
                        bufferedWriter.append(line);
                        bufferedWriter.newLine();
                        continue;
                    }

                    try {
                        if(configuration.get(lineContent[0].trim())==null){
                            //古いConfigに値が無い項目の場合
                            //内容出力してスキップ
                            bufferedWriter.append(line);
                            bufferedWriter.newLine();
                            continue;
                        }
                    } catch (NullPointerException e) {
                        bufferedWriter.append(line);
                        bufferedWriter.newLine();
                        continue;
                    }

                    String sectionWork = "";
                    if (checkUtil.checkNullOrBlank(sectionName)) {
                        sectionWork = sectionName;
                    } else {
                        sectionWork = sectionName + ".";
                    }

                    //型を識別
                    if (type == 1) {
                        //config.yml
                        if ((sectionWork + lineContent[0]).equalsIgnoreCase("version")) {
                            //バージョン：更新
                            bufferedWriter.append(lineContent[0] + ": " + BeginnerManagement.pluginVersion);
                        } else if (typeList.get(sectionWork + lineContent[0].trim()).equals("string")) {
                            //String:文字列を""で囲んで出力
                            bufferedWriter.append(lineContent[0] + ": \"" + configuration.getString(sectionWork + lineContent[0].trim()) + "\"");
                        } else if (typeList.get(sectionWork + lineContent[0].trim()).equals("int")) {
                            //int:
                            bufferedWriter.append(lineContent[0] + ": " + configuration.getInt(sectionWork + lineContent[0].trim()));
                        } else if (typeList.get(sectionWork + lineContent[0].trim()).equals("double")) {
                            //double:
                            bufferedWriter.append(lineContent[0] + ": " + configuration.getDouble(sectionWork + lineContent[0].trim()));
                        } else if (typeList.get(sectionWork + lineContent[0].trim()).equals("boolean")) {
                            //boolean
                            bufferedWriter.append(lineContent[0] + ": " + configuration.getBoolean(sectionWork + lineContent[0].trim()));
                        }
                    } else {
                        //message_xx.yml
                        if ((sectionWork + lineContent[0]).equalsIgnoreCase("version")) {
                            //バージョン：更新
                            bufferedWriter.append(lineContent[0] + ": " + BeginnerManagement.pluginVersion);
                        } else {
                            //String:文字列を""で囲んで出力
                            bufferedWriter.append(lineContent[0] + ": \"" + configuration.getString(sectionWork + lineContent[0].trim()) + "\"");
                        }
                    }
                    bufferedWriter.newLine();

                }

                //クローズ
                bufferedWriter.close();
            } catch (IOException e) {
                Bukkit.getLogger().warning("[BeginnerManagement] New config file get failure.");
            }

        }

    }

}
