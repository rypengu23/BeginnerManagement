package com.github.rypengu23.beginnermanagement.model;

import java.util.Date;

public class PlayerDataModel {

    private String UUID;
    private String playerName;
    private Date firstLoginDate;
    private int punishmentNumberOfTimes;
    private boolean whitelist;

    public PlayerDataModel() {

    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Date getFirstLoginDate() {
        return firstLoginDate;
    }

    public void setFirstLoginDate(Date firstLoginDate) {
        this.firstLoginDate = firstLoginDate;
    }

    public int getPunishmentNumberOfTimes() {
        return punishmentNumberOfTimes;
    }

    public void setPunishmentNumberOfTimes(int punishmentNumberOfTimes) {
        this.punishmentNumberOfTimes = punishmentNumberOfTimes;
    }

    public boolean isWhitelist() {
        return whitelist;
    }

    public void setWhitelist(boolean whitelist) {
        this.whitelist = whitelist;
    }
}
