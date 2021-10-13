package com.github.rypengu23.beginnermanagement.config;

public class ConsoleMessage {

    public static String BeginnerManagement_startupPlugin;
    public static String BeginnerManagement_startupCompPlugin;

    public static String BeginnerManagement_loadDiscordSRV;
    public static String BeginnerManagement_loadCompDiscordSRV;
    public static String BeginnerManagement_loadFailureDiscordSRV;

    public static String BeginnerManagement_startupScheduler;

    public static String ConfigUpdater_CheckUpdateConfig;
    public static String ConfigUpdater_UpdateConfig;
    public static String ConfigUpdater_NoConfigUpdates;

    private MainConfig mainConfig;

    public ConsoleMessage(MainConfig mainConfig){
        this.mainConfig = mainConfig;
    }

    public void changeLanguageConsoleMessages(){
        if(mainConfig.getLanguage().equals("ja")){

            BeginnerManagement_startupPlugin = "プラグインを起動します。";
            BeginnerManagement_startupCompPlugin = "プラグインが起動しました。";

            BeginnerManagement_loadDiscordSRV = "DiscordSRVの読み込みを行います。";
            BeginnerManagement_loadCompDiscordSRV = "DiscordSRVの読み込みが完了しました。";
            BeginnerManagement_loadFailureDiscordSRV = "DiscordSRVの読み込みに失敗しました。";

            BeginnerManagement_startupScheduler = "スケジューラを起動。";

            ConfigUpdater_CheckUpdateConfig = "Configの更新確認を行います。";
            ConfigUpdater_UpdateConfig = "古いバージョンのConfigです。アップデートを行います。";
            ConfigUpdater_NoConfigUpdates = "Configは最新バージョンです。";



        } else if(mainConfig.getLanguage().equals("en")){

            BeginnerManagement_startupPlugin = "Plugin startup.";
            BeginnerManagement_startupCompPlugin = "Plugin startup complete.";

            BeginnerManagement_loadDiscordSRV = "Load:DiscordSRV";
            BeginnerManagement_loadCompDiscordSRV = "Load complete:DiscordSRV";
            BeginnerManagement_loadFailureDiscordSRV = "Load failure:DiscordSRV";

            BeginnerManagement_startupScheduler = "Scheduler startup.";

            ConfigUpdater_CheckUpdateConfig = "Check for Config updates.";
            ConfigUpdater_UpdateConfig = "Update Config.";
            ConfigUpdater_NoConfigUpdates = "No Config updates.";
        }
    }
}
