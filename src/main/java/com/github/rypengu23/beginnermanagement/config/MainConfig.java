package com.github.rypengu23.beginnermanagement.config;

import com.github.rypengu23.beginnermanagement.model.BanTypeModel;
import com.github.rypengu23.beginnermanagement.util.CheckUtil;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;

public class MainConfig {

    private Double version;
    private String language;

    //DB
    private String hostname;
    private String db;
    private String user;
    private String password;

    //Discord利用可否
    private boolean useDiscordSRV;
    private String notifyChannelId;
    private boolean useDiscordNotify;
    private String notifyMentionId;
    private boolean useDiscordNotifyForAutoBan;
    private String notifyMentionIdForAutoBan;

    private int day;
    private int hour;
    private int minute;

    private List<String> items;
    private List<String> craft;
    private List<String> build;
    private List<String> createEntity;
    private List<String> damageEntity;

    private boolean useLavaBucket;
    private boolean useWaterBucket;
    private boolean useFlint;

    private int breakDay;
    private int breakHour;
    private int breakMinute;

    private int punishmentNumberOfTimes;
    private String punishmentReason;
    private boolean useIpBan;
    private String[] punishmentDetail;

    private ArrayList<BanTypeModel> banTypeModelList;

    public MainConfig(FileConfiguration config) {

        version = config.getDouble("version");
        language = config.getString("language");

        hostname = config.getString("database.hostname");
        db = config.getString("database.db");
        user = config.getString("database.user");
        password = config.getString("database.password");

        //Discord利用可否
        useDiscordSRV = config.getBoolean("discord.useDiscordSRV");
        notifyChannelId = config.getString("discord.notifyChannelId");
        useDiscordNotify = config.getBoolean("discord.useDiscordNotify");
        notifyMentionId = config.getString("discord.notifyMentionId");
        useDiscordNotifyForAutoBan = config.getBoolean("discord.useDiscordNotifyForAutoBan");
        notifyMentionIdForAutoBan = config.getString("discord.notifyMentionIdForAutoBan");

        day = config.getInt("time.day");
        hour = config.getInt("time.hour");
        minute = config.getInt("time.minute");

        items = Arrays.asList(config.getString("limit.items").split(","));
        craft = Arrays.asList(config.getString("limit.craft").split(","));;
        build = Arrays.asList(config.getString("limit.build").split(","));;
        createEntity = Arrays.asList(config.getString("limit.createEntity").split(","));;
        damageEntity = Arrays.asList(config.getString("limit.damageEntity").split(","));;

        useLavaBucket = config.getBoolean("limit.useLavaBucket");
        useWaterBucket = config.getBoolean("limit.useWaterBucket");
        useFlint = config.getBoolean("limit.useFlint");

        breakDay = config.getInt("break.day");
        breakHour = config.getInt("break.hour");
        breakMinute = config.getInt("break.minute");

        punishmentNumberOfTimes = config.getInt("autoBan.numberOfTimes");
        punishmentReason = config.getString("autoBan.reason");
        useIpBan = config.getBoolean("autoBan.useIpBan");
        punishmentDetail = config.getString("autoBan.detail").split(",");

        banTypeModelList = convertToModel(punishmentDetail);
    }

    public ArrayList<BanTypeModel> convertToModel(String[] banTypeRowData){

        CheckUtil checkUtil = new CheckUtil();
        ArrayList<BanTypeModel> resultList = new ArrayList<>();
        int punishmentNumber = 0;

        for(int i=0; i<banTypeRowData.length; i++){
            String data = banTypeRowData[i];

            if(data.equalsIgnoreCase("KICK")){
                //KICK
                resultList.add(new BanTypeModel(punishmentNumber++, 0, 0));
            }else if(data.equalsIgnoreCase("BAN")){
                //BAN
                resultList.add(new BanTypeModel(punishmentNumber++, 2, 0));
            }else if(data.contains("TBAN:")){
                //TBAN
                String[] work = data.split(":");
                if(work.length != 2){
                    Bukkit.getLogger().warning("Skipped loading because autoPunishment.detail in Config contains an invalid value. value:" + banTypeRowData[i]);
                    continue;
                }else if(!checkUtil.checkNumeric(work[1])){
                    Bukkit.getLogger().warning("Skipped loading because autoPunishment.detail in Config contains an invalid value. value:" + banTypeRowData[i]);
                    continue;
                }

                //値をセット
                resultList.add(new BanTypeModel(punishmentNumber++, 1, Integer.parseInt(work[1])));
            }else{
                Bukkit.getLogger().warning("Skipped loading because autoPunishment.detail in Config contains an invalid value. value:" + banTypeRowData[i]);
            }
        }

        return resultList;
    }

    public Map getConfigTypeList() {
        Map<String, String> map = new HashMap<>();
        map.put("version", "double");
        map.put("language", "String");

        map.put("database.hostname", "String");
        map.put("database.db", "String");
        map.put("database.user", "String");
        map.put("database.password", "String");

        map.put("discord.useDiscordSRV", "boolean");
        map.put("discord.notifyChannelId", "String");
        map.put("discord.useDiscordNotify", "boolean");
        map.put("discord.notifyMentionId", "String");
        map.put("discord.useDiscordNotifyForAutoBan", "boolean");
        map.put("discord.notifyMentionIdForAutoBan", "String");

        map.put("time.day", "int");
        map.put("time.hour", "int");
        map.put("time.minute", "int");

        map.put("limit.items", "String");
        map.put("limit.craft", "String");
        map.put("limit.build", "String");
        map.put("limit.createEntity", "String");
        map.put("limit.damageEntity", "String");

        map.put("limit.useLavaBucket", "boolean");
        map.put("limit.useWaterBucket", "boolean");
        map.put("limit.useFlint", "boolean");

        map.put("break.day", "int");
        map.put("break.hour", "int");
        map.put("break.minute", "int");

        map.put("autoBan.numberOfTimes", "int");
        map.put("autoBan.reason", "String");
        map.put("autoBan.useIpBan", "boolean");
        map.put("autoBan.detail", "String");

        return map;
    }

    public Double getVersion() {
        return version;
    }

    public String getLanguage() {
        return language;
    }

    public String getHostname() {
        return hostname;
    }

    public String getDb() {
        return db;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public boolean isUseDiscordSRV() {
        return useDiscordSRV;
    }

    public String getNotifyChannelId() {
        return notifyChannelId;
    }

    public boolean isUseDiscordNotify() {
        return useDiscordNotify;
    }

    public String getNotifyMentionId() {
        return notifyMentionId;
    }

    public boolean isUseDiscordNotifyForAutoBan() {
        return useDiscordNotifyForAutoBan;
    }

    public String getNotifyMentionIdForAutoBan() {
        return notifyMentionIdForAutoBan;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public List<String> getItems() {
        return items;
    }

    public List<String> getCraft() {
        return craft;
    }

    public List<String> getBuild() {
        return build;
    }

    public List<String> getCreateEntity() {
        return createEntity;
    }

    public List<String> getDamageEntity() {
        return damageEntity;
    }

    public boolean isUseLavaBucket() {
        return useLavaBucket;
    }

    public boolean isUseWaterBucket() {
        return useWaterBucket;
    }

    public boolean isUseFlint() {
        return useFlint;
    }

    public int getBreakDay() {
        return breakDay;
    }

    public int getBreakHour() {
        return breakHour;
    }

    public int getBreakMinute() {
        return breakMinute;
    }

    public int getPunishmentNumberOfTimes() {
        return punishmentNumberOfTimes;
    }

    public String getPunishmentReason() {
        return punishmentReason;
    }

    public boolean isUseIpBan() {
        return useIpBan;
    }

    public String[] getPunishmentDetail() {
        return punishmentDetail;
    }

    public ArrayList<BanTypeModel> getBanTypeModelList() {
        return banTypeModelList;
    }
}
