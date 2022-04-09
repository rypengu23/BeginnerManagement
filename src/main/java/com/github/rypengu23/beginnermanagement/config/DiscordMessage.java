package com.github.rypengu23.beginnermanagement.config;

public class DiscordMessage {

    public static String NotifyActOfLimitCommon;
    public static String NotifyActOfLimit_Item;
    public static String NotifyActOfLimit_Craft;
    public static String NotifyActOfLimit_Build;
    public static String NotifyActOfLimit_DamageEntity;
    public static String NotifyActOfLimit_CreateEntity;
    public static String NotifyActOfLimit_UseLavaBucket;
    public static String NotifyActOfLimit_UseWaterBucket;
    public static String NotifyActOfLimit_UseFlint;

    public static String NotifyActOfLimit_Break;

    public static String NotifyAutoKick;
    public static String NotifyAutoTBan;
    public static String NotifyAutoBan;

    private MainConfig mainConfig;

    public DiscordMessage(MainConfig mainConfig){
        this.mainConfig = mainConfig;
    }

    public void changeLanguageDiscordMessages() {
        if (mainConfig.getLanguage().equals("ja")) {
            NotifyActOfLimitCommon = "プレイヤー({player})の危険行為を検知 種別：";
            NotifyActOfLimit_Item = "インベントリ操作";
            NotifyActOfLimit_Craft = "クラフト";
            NotifyActOfLimit_Build = "建築";
            NotifyActOfLimit_DamageEntity = "エンティティへの攻撃";
            NotifyActOfLimit_CreateEntity = "エンティティ設置";
            NotifyActOfLimit_UseLavaBucket = "マグマバケツ使用";
            NotifyActOfLimit_UseWaterBucket = "水バケツ使用";
            NotifyActOfLimit_UseFlint = "火打石・ファイヤーチャージ使用";

            NotifyActOfLimit_Break = "ブロック破壊";

            NotifyAutoKick = "プレイヤー({player})を自動でキックしました。";
            NotifyAutoTBan = "プレイヤー({player})を自動で期限付きBANしました。";
            NotifyAutoBan = "プレイヤー({player})を自動でBANしました。";

        } else {
            NotifyActOfLimitCommon = "Detects dangerous behavior of the player! Type：";
            NotifyActOfLimit_Item = "Inventory operation";
            NotifyActOfLimit_Craft = "Craft";
            NotifyActOfLimit_Build = "Build";
            NotifyActOfLimit_DamageEntity = "Attack on an entity";
            NotifyActOfLimit_CreateEntity = "Entity placement";
            NotifyActOfLimit_UseLavaBucket = "Use magma bucket";
            NotifyActOfLimit_UseWaterBucket = "Use water bucket";
            NotifyActOfLimit_UseFlint = "Use flint or fire charge";

            NotifyActOfLimit_Break = "Block destruction";

            NotifyAutoKick = "Automatically kicked the player ({player}).";
            NotifyAutoTBan = "Automatically banned the player ({player}) for a limited time.";
            NotifyAutoBan = "Automatically banned the player ({player}).";
        }
    }
}
