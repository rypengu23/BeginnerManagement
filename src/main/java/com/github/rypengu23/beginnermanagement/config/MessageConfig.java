package com.github.rypengu23.beginnermanagement.config;

import org.bukkit.configuration.file.FileConfiguration;

public class MessageConfig {

    private Double version;
    private String language;

    private String prefix;

    private String info;
    private String infoNotLimit;
    private String warn;

    public MessageConfig(FileConfiguration config) {

        version = config.getDouble("version");
        language = config.getString("language");

        prefix = config.getString("prefix");

        info = config.getString("info");
        info = config.getString("infoNotLimit");
        warn = config.getString("warn");
    }

    public Double getVersion() {
        return version;
    }

    public String getLanguage() {
        return language;
    }

    public String getPrefix() {
        return prefix;
    }

    public String getInfo() {
        return info;
    }

    public String getInfoNotLimit() {
        return infoNotLimit;
    }

    public String getWarn() {
        return warn;
    }
}
