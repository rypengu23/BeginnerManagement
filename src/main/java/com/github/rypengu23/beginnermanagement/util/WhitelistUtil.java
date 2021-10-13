package com.github.rypengu23.beginnermanagement.util;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.ArrayList;


public class WhitelistUtil {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public static ArrayList<String> whitelistData;

    public WhitelistUtil(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    /**
     * ホワイトリストデータを更新
     */
    public void updateWhitelistData(){

        File playerDataFile = new File(BeginnerManagement.getInstance().getDataFolder().getPath() + "/whitelist.txt");

        if(!playerDataFile.exists()){
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
            }
        }

        whitelistData = getWhitelist();
    }

    /**
     * ホワイトリストを全取得
     * @return
     */
    public ArrayList<String> getWhitelist(){

        ArrayList<String> resultList = new ArrayList<>();

        try {
            File playerDataFile = new File(BeginnerManagement.getInstance().getDataFolder().getPath() + "/whitelist.txt");
            FileReader filereader = new FileReader(playerDataFile);
            BufferedReader br = new BufferedReader(filereader);

            String str;
            while((str = br.readLine()) != null){
                resultList.add(str);
            }


        } catch (IOException e) {

        }

        return resultList;

    }

    /**
     * ホワイトリストに追加
     * @param player
     */
    public void addWhitelist(Player player){

        CheckUtil checkUtil = new CheckUtil();

        try {
            File whitelistFile = new File(BeginnerManagement.getInstance().getDataFolder().getPath() + "/whitelist.txt");

            if (checkUtil.checkBeforeWriteFile(whitelistFile)){
                FileWriter filewriter = new FileWriter(whitelistFile, true);

                filewriter.write(player.getUniqueId().toString() + "\r\n");

                filewriter.close();
            }else{

            }


        } catch (IOException e) {

        }
    }

    /**
     * 引数のプレイヤーがホワイトリストに入っているか
     * @param player
     * @return
     */
    public boolean checkWhitelist(Player player){

        if(whitelistData.contains(player.getUniqueId().toString())){
            return true;
        }
        return false;
    }
}
