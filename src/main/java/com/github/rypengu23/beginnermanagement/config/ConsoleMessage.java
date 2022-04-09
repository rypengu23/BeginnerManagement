package com.github.rypengu23.beginnermanagement.config;

public class ConsoleMessage {

    public static String BeginnerManagement_startupPlugin;
    public static String BeginnerManagement_startupCompPlugin;

    public static String BeginnerManagement_loadDiscordSRV;
    public static String BeginnerManagement_loadCompDiscordSRV;
    public static String BeginnerManagement_loadFailureDiscordSRV;
    public static String BeginnerManagement_loadDiscord;
    public static String BeginnerManagement_loadCompDiscord;
    public static String BeginnerManagement_loadFailureDiscord;

    public static String BeginnerManagement_startupScheduler;

    public static String ConfigUpdater_CheckUpdateConfig;
    public static String ConfigUpdater_UpdateConfig;
    public static String ConfigUpdater_NoConfigUpdates;

    public static String PlayerDataUtil_CreateNewPlayerData;

    public static String DiscordUtil_FailureSendMessage;
    public static String DiscordUtil_ConnectDiscord;
    public static String DiscordUtil_ReconnectDiscord;
    public static String DiscordUtil_SuccessConnect;
    public static String DiscordUtil_FailureConnect;

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
            BeginnerManagement_loadDiscordSRV = "Discordへの接続を行います";
            BeginnerManagement_loadCompDiscordSRV = "Discordへの接続が完了しました。";
            BeginnerManagement_loadFailureDiscordSRV = "Discordへの接続に失敗しました。";

            BeginnerManagement_startupScheduler = "スケジューラを起動。";

            ConfigUpdater_CheckUpdateConfig = "Configの更新確認を行います。";
            ConfigUpdater_UpdateConfig = "古いバージョンのConfigです。アップデートを行います。";
            ConfigUpdater_NoConfigUpdates = "Configは最新バージョンです。";

            PlayerDataUtil_CreateNewPlayerData = "新しいプレイヤーデータを作成しました。";

            DiscordUtil_FailureSendMessage = "Discordへのメッセージ送信に失敗しました。";
            DiscordUtil_ConnectDiscord = "DiscordSRVへ接続します。";
            DiscordUtil_ReconnectDiscord = "DiscordSRVへ再接続します。";
            DiscordUtil_SuccessConnect = "DiscordSRVに接続しました。";
            DiscordUtil_FailureConnect = "DiscordSRVの接続に失敗しました。";

        } else if(mainConfig.getLanguage().equals("en")){

            BeginnerManagement_startupPlugin = "Plugin startup.";
            BeginnerManagement_startupCompPlugin = "Plugin startup complete.";

            BeginnerManagement_loadDiscordSRV = "Load:DiscordSRV";
            BeginnerManagement_loadCompDiscordSRV = "Load complete:DiscordSRV";
            BeginnerManagement_loadFailureDiscordSRV = "Load failure:DiscordSRV";
            BeginnerManagement_loadDiscord = "Discordへの接続を行います";
            BeginnerManagement_loadCompDiscord = "Discordへの接続が完了しました。";
            BeginnerManagement_loadFailureDiscord = "Discordへの接続に失敗しました。";

            BeginnerManagement_startupScheduler = "Scheduler startup.";

            ConfigUpdater_CheckUpdateConfig = "Check for Config updates.";
            ConfigUpdater_UpdateConfig = "Update Config.";
            ConfigUpdater_NoConfigUpdates = "No Config updates.";

            PlayerDataUtil_CreateNewPlayerData = "Created new player data.";

            DiscordUtil_FailureSendMessage = "Failed to send a message to Discord.";
            DiscordUtil_ConnectDiscord = "Connect to Discord SRV...";
            DiscordUtil_ReconnectDiscord = "Reconnect to Discord SRV...";
            DiscordUtil_SuccessConnect = "Connected to Discord SRV.";
            DiscordUtil_FailureConnect = "Failed to connect to Discord.";
        }
    }
}
