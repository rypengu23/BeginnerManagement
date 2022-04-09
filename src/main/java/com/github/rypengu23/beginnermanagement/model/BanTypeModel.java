package com.github.rypengu23.beginnermanagement.model;

public class BanTypeModel {

    //順番
    private int number;

    //BAN種別（0:KICK 1:TBAN 2:BAN)
    private int banType;

    //TBAN時の規制時間(分)
    private int tBanMinutes;

    public BanTypeModel(int number, int banType, int tBanMinutes) {
        this.number = number;
        this.banType = banType;
        this.tBanMinutes = tBanMinutes;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getBanType() {
        return banType;
    }

    public void setBanType(int banType) {
        this.banType = banType;
    }

    public int gettBanMinutes() {
        return tBanMinutes;
    }

    public void settBanMinutes(int tBanMinutes) {
        this.tBanMinutes = tBanMinutes;
    }
}
