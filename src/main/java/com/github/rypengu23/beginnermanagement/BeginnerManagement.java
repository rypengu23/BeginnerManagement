package com.github.rypengu23.beginnermanagement;

import com.github.rypengu23.beginnermanagement.command.*;
import com.github.rypengu23.beginnermanagement.config.*;
import com.github.rypengu23.beginnermanagement.dao.ConnectDao;
import com.github.rypengu23.beginnermanagement.listener.*;
import com.github.rypengu23.beginnermanagement.model.PlayerDataModel;
import com.github.rypengu23.beginnermanagement.util.AutoBanUtil;
import com.github.rypengu23.beginnermanagement.util.DiscordUtil;
import github.scarsz.discordsrv.DiscordSRV;
import github.scarsz.discordsrv.dependencies.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public final class BeginnerManagement extends JavaPlugin {

    //バージョン
    public static double pluginVersion = 2.1;

    //インスタンス
    private static BeginnerManagement instance = null;

    //Config
    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    //DiscordSRV
    public static DiscordSRV discordSRV;
    public static TextChannel textChannel;

    //メモリ
    public static HashMap<String, PlayerDataModel> playerDataList = new HashMap<>();
    public static HashMap<String, Integer> playerNumberOfViolations = new HashMap<>();

    //タスク
    public static BukkitTask resetCount;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        if (configLoader == null) {
            configLoader = new ConfigLoader();
        }
        configLoader.reloadConfig();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();

        //起動メッセージ
        //Startup message
        Bukkit.getLogger().info("[BeginnerManagement] == BeginnerManagement Ver" + pluginVersion + " ==");
        Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.BeginnerManagement_startupPlugin);

        //Configの更新確認
        ConfigUpdater configUpdater = new ConfigUpdater();
        if (configUpdater.configUpdateCheck()) {
            configLoader = new ConfigLoader();
            configLoader.reloadConfig();
            mainConfig = configLoader.getMainConfig();
            messageConfig = configLoader.getMessageConfig();
        }

        //DB接続
        ConnectDao connectDao = new ConnectDao();
        connectDao.connectionCheck();

        if (mainConfig.isUseDiscordSRV()) {
            //DiscordSRV接続
            DiscordUtil discordUtil = new DiscordUtil();
            discordUtil.connectDiscord();
        }

        //リスナー
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new Listener_PlayerJoinEvent(), this);
        pm.registerEvents(new Listener_BlockBuild(), this);
        pm.registerEvents(new Listener_Craft(), this);
        pm.registerEvents(new Listener_Inventory(), this);
        pm.registerEvents(new Listener_Entity(), this);
        pm.registerEvents(new Listener_UseBucket(), this);
        pm.registerEvents(new Listener_UseFlint(), this);

        //コマンド入力時の入力補助
        //Command tab complete
        TabComplete tabComplete = new TabComplete();
        getCommand("beginnermanagement").setTabCompleter(tabComplete);
        TabComplete tabComplete2 = new TabComplete();
        getCommand("bm").setTabCompleter(tabComplete2);

        //違反回数リセット
        AutoBanUtil autoBanUtil = new AutoBanUtil();
        autoBanUtil.resetCount();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static BeginnerManagement getInstance() {
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (label.equalsIgnoreCase("beginnermanagement") || label.equalsIgnoreCase("bm")) {
            configLoader = new ConfigLoader();
            //引数があるかどうか
            if (args.length != 0) {
                Command_Info command_info = new Command_Info();
                Command_Config command_config = new Command_Config();
                Command_Help command_help = new Command_Help();
                Command_Whitelist command_whitelist = new Command_Whitelist();

                if(command_info.checkCommandExit(args[0])){
                    command_info.sort(sender, args);
                }else if(command_config.checkCommandExit(args[0])){
                    command_config.sort(sender, args);
                }else if(command_help.checkCommandExit(args[0])){
                    command_help.sort(sender, args);
                }else if(command_whitelist.checkCommandExit(args[0])){
                    command_whitelist.sort(sender, args);
                } else {
                    //コマンドの形式が不正な場合
                    sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.CommandFailure);
                }

            } else {
                //引数が無ければバージョン情報
                sender.sendMessage("§a" + messageConfig.getPrefix() + " §fBeginnerManagement Ver"+ pluginVersion);
                sender.sendMessage("§a" + messageConfig.getPrefix() + " §fDeveloper: rypengu23");
            }

        }
        return false;
    }
}
