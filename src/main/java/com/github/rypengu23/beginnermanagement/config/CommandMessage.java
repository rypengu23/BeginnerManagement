package com.github.rypengu23.beginnermanagement.config;

public class CommandMessage {

    public static String ConfigReload;
    public static String CommandFailure;
    public static String DoNotHavePermission;

    public static String Command_Info_SHOWSTATUS1;
    public static String Command_Info_SHOWSTATUS2;
    public static String Command_Info_SHOWSTATUS3;
    public static String Command_Info_SHOWSTATUS4;
    public static String Command_Info_PlayerNotFound;

    public static String Command_Whitelist_PlayerNotFound;
    public static String Command_Whitelist_Added;
    public static String Command_Whitelist_Add;
    public static String Command_Whitelist_Removed;
    public static String Command_Whitelist_Remove;

    public static String Command_Help_Line1;
    public static String Command_Help_Info;
    public static String Command_Help_AnotherInfo;
    public static String Command_Help_Whitelist;
    public static String Command_Help_reload;
    public static String Command_Help_LineLast;

    private MainConfig mainConfig;

    public CommandMessage(MainConfig mainConfig){
        this.mainConfig = mainConfig;
    }

    public void changeLanguageCommandMessages(){
        if(mainConfig.getLanguage().equals("ja")){
            ConfigReload = "Configをリロードしました。";
            CommandFailure = "不正なコマンドです。";
            DoNotHavePermission = "権限を所有していません。";

            Command_Info_SHOWSTATUS1 = "§b――――― §f{player}のログイン情報 §b―――――";
            Command_Info_SHOWSTATUS2 = "初回ログイン: {firstlogin}";
            Command_Info_SHOWSTATUS3 = "規制終了日時: {opendate}";
            Command_Info_SHOWSTATUS4 = "§b――――――――――――――――――――――――――――――――――――――――";
            Command_Info_PlayerNotFound = "指定したプレイヤーは見つかりませんでした。";

            Command_Whitelist_PlayerNotFound = "指定したプレイヤーは見つかりませんでした。";
            Command_Whitelist_Added = "指定したプレイヤーは既に追加されています。";
            Command_Whitelist_Add = "ホワイトリストに{player}を追加しました。";
            Command_Whitelist_Removed = "指定したプレイヤーはホワイトリストに追加されていません。";
            Command_Whitelist_Remove = "ホワイトリストから{player}を削除しました。";

            Command_Help_Line1 = "§b――――― §fBeginnerManagement コマンドガイド §b―――――";
            Command_Help_Info = "§e/bm info §f: 規制情報を表示。";
            Command_Help_AnotherInfo = "§e/bm info [ﾕｰｻﾞｰ名] §f: 指定したﾕｰｻﾞｰの情報を表示。";
            Command_Help_Whitelist = "§e/bm whitelist [ﾕｰｻﾞｰ名] §f: ホワイトリストに追加。";
            Command_Help_reload = "§e/bm reload §f: Configをリロード。";
            Command_Help_LineLast = "§b――――――――――――――――――――――――――――――――――――――――";

        } else if(mainConfig.getLanguage().equals("en")){
            ConfigReload = "Config reloaded.";
            CommandFailure = "Command failure.";
            DoNotHavePermission = "You do not have permission.";

            Command_Info_SHOWSTATUS1 = "§b――――― §f{player}'s information §b―――――";
            Command_Info_SHOWSTATUS2 = "First login: {firstlogin}";
            Command_Info_SHOWSTATUS3 = "Regulation end: {opendate}";
            Command_Info_SHOWSTATUS4 = "§b――――――――――――――――――――――――――――――――";
            Command_Info_PlayerNotFound = "Player not found.";

            Command_Whitelist_PlayerNotFound = "Player not found.";
            Command_Whitelist_Added = "Player have already been added.";
            Command_Whitelist_Add = "Added {player} to the white list.";
            Command_Whitelist_Removed = "Player have already been removed.";
            Command_Whitelist_Remove = "Removed {player} to the white list.";

            Command_Help_Line1 = "§b――――― §fBeginnerManagement Help §b―――――";
            Command_Help_Info = "§e/bm info §f: Show regulatory information.";
            Command_Help_AnotherInfo = "§e/bm info [username] §f: Show player information.";
            Command_Help_Whitelist = "§e/bm whitelist [username] §f: Add to white list.";
            Command_Help_reload = "§e/awt reload §f:Reload Config.";
            Command_Help_LineLast = "§b――――――――――――――――――――――――――――――――";
        }
    }
}
