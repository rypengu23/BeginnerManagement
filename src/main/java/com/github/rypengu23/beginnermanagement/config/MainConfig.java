package com.github.rypengu23.beginnermanagement.config;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainConfig {

    private Double version;
    private String language;

    //DiscordSRV利用可否
    private boolean useDiscordSRV;

    private int day;
    private int hour;
    private int minute;

    private boolean blockBreak;

    private List<String> items;
    private List<String> craft;
    private List<String> build;
    private List<String> createEntity;
    private List<String> damageEntity;

    private boolean useLavaBucket;
    private boolean useWaterBucket;
    private boolean useFlint;

    public MainConfig(FileConfiguration config) {

        version = config.getDouble("version");
        language = config.getString("language");

        //DiscordSRV利用可否
        useDiscordSRV = config.getBoolean("setting.useDiscordSRV");

        day = config.getInt("time.day");
        hour = config.getInt("time.hour");
        minute = config.getInt("time.minute");

        blockBreak = config.getBoolean("limit.blockBreak");

        items = Arrays.asList(config.getString("limit.items").split(","));
        craft = Arrays.asList(config.getString("limit.craft").split(","));;
        build = Arrays.asList(config.getString("limit.build").split(","));;
        createEntity = Arrays.asList(config.getString("limit.createEntity").split(","));;
        damageEntity = Arrays.asList(config.getString("limit.damageEntity").split(","));;

        useLavaBucket = config.getBoolean("limit.useLavaBucket");
        useWaterBucket = config.getBoolean("limit.useWaterBucket");
        useFlint = config.getBoolean("limit.useFlint");

    }

    public Map getConfigTypeList() {
        Map<String, String> map = new HashMap<>();
        map.put("version", "double");
        map.put("language", "String");

        map.put("setting.useDiscordSRV", "boolean");

        map.put("time.day", "int");
        map.put("time.hour", "int");
        map.put("time.minute", "int");

        map.put("limit.blockBreak", "String");
        map.put("limit.items", "String");
        map.put("limit.craft", "String");
        map.put("limit.build", "String");
        map.put("limit.createEntity", "String");
        map.put("limit.damageEntity", "String");

        map.put("limit.useLavaBucket", "boolean");
        map.put("limit.useWaterBucket", "boolean");
        map.put("limit.useFlint", "boolean");


        return map;
    }

    public Double getVersion() {
        return version;
    }

    public String getLanguage() {
        return language;
    }

    public boolean isUseDiscordSRV() {
        return useDiscordSRV;
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

    public boolean isBlockBreak() {
        return blockBreak;
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
}
