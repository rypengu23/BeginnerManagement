package com.github.rypengu23.beginnermanagement;

import com.github.rypengu23.beginnermanagement.command.*;
import com.github.rypengu23.beginnermanagement.config.*;
import com.github.rypengu23.beginnermanagement.listener.*;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import com.github.rypengu23.beginnermanagement.util.WhitelistUtil;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BeginnerManagement extends JavaPlugin {

    //バージョン
    public static double pluginVersion = 1.0;

    //インスタンス
    private static BeginnerManagement instance = null;

    //Config
    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    //DiscordSRV
    public static DiscordSRV discordSRV;

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

        //プレイヤーデータの読み込み
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        playerDataUtil.updatePlayerData();

        WhitelistUtil whitelistUtil = new WhitelistUtil();
        whitelistUtil.updateWhitelistData();

        if (mainConfig.isUseDiscordSRV()) {
            //DiscordSRV接続
            try {
                Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.BeginnerManagement_loadDiscordSRV);
                discordSRV = (DiscordSRV) Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV");
                Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.BeginnerManagement_loadCompDiscordSRV);
            } catch (NoClassDefFoundError e) {
                Bukkit.getLogger().warning("[BeginnerManagement] " + ConsoleMessage.BeginnerManagement_loadFailureDiscordSRV);
            }
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

                if (args.length == 1) {

                    if (args[0].equalsIgnoreCase("info")) {
                        Command_Info command_info = new Command_Info();
                        command_info.showStatus(sender);

                    } else if (args[0].equalsIgnoreCase("reload")) {
                        Command_Config command_config = new Command_Config();
                        command_config.reloadConfig(sender);

                    } else if (args[0].equalsIgnoreCase("help")) {
                        //helpコマンド ページ1
                        Command_Help command_help = new Command_Help();
                        command_help.showHelp(sender, "0");
                    } else {
                        //コマンドの形式が不正な場合
                        sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.BeginnerManagement_CommandFailure);
                    }

                } else if (args.length == 2) {

                    if (args[0].equalsIgnoreCase("info")) {
                        Command_Info command_info = new Command_Info();
                        command_info.showStatus(sender, args[1]);
                    }else if (args[0].equalsIgnoreCase("whitelist")) {
                        Command_Whitelist command_whitelist = new Command_Whitelist();
                        command_whitelist.addWhitelist(sender, args[1]);
                    }
                }


            } else {
                //引数が無ければバージョン情報
                sender.sendMessage("§a" + messageConfig.getPrefix() + " §fBeginnerManagement Ver1.0");
                sender.sendMessage("§a" + messageConfig.getPrefix() + " §fDeveloper: rypengu23");
            }

        }
        return false;
    }
}
