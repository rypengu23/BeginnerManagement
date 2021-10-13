package com.github.rypengu23.beginnermanagement.model;

import org.bukkit.entity.Player;

import java.util.Calendar;

public class PlayerDataModel {

    private String UUID;
    private Calendar firstLoginDate;
    private boolean Wildcard;

    public PlayerDataModel() {

    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public Calendar getFirstLoginDate() {
        return firstLoginDate;
    }

    public void setFirstLoginDate(Calendar firstLoginDate) {
        this.firstLoginDate = firstLoginDate;
    }

    public boolean isWildcard() {
        return Wildcard;
    }

    public void setWildcard(boolean wildcard) {
        Wildcard = wildcard;
    }
}
