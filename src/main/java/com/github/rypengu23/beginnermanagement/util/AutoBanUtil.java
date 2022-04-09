package com.github.rypengu23.beginnermanagement.util;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.*;
import com.github.rypengu23.beginnermanagement.dao.InfoDao;
import com.github.rypengu23.beginnermanagement.model.BanTypeModel;
import com.github.rypengu23.beginnermanagement.model.PlayerDataModel;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public class AutoBanUtil {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public AutoBanUtil(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    /**
     * プレイヤーの危険行為回数を加算
     * @param uuid
     */
    public void plusNumberOfViolations(String uuid){

        if(BeginnerManagement.playerNumberOfViolations.containsKey(uuid)) {
            int numberOfViolations = BeginnerManagement.playerNumberOfViolations.get(uuid);
            BeginnerManagement.playerNumberOfViolations.put(uuid, numberOfViolations + 1);
        }
    }

    /**
     * プレイヤーの危険行為回数が設定値に達したか判定
     * 達していた場合、自動BANを行う。
     * @param player
     * @return
     */
    public boolean checkPunishmentTime(Player player){

        String uuid = player.getUniqueId().toString();

        if(BeginnerManagement.playerNumberOfViolations.containsKey(uuid)) {
            int numberOfViolations = BeginnerManagement.playerNumberOfViolations.get(uuid);
            if(numberOfViolations >= mainConfig.getPunishmentNumberOfTimes()){
                return autoBan(player);
            }
        }

        return false;
    }

    /**
     * 自動でBANを行う
     * @param player
     * @return
     */
    public boolean autoBan(Player player){

        String uuid = player.getUniqueId().toString();

        //キー存在チェック
        if(!BeginnerManagement.playerDataList.containsKey(uuid)) {
            return false;
        }

        ArrayList<BanTypeModel> banTypeModelList = mainConfig.getBanTypeModelList();
        PlayerDataModel playerData = BeginnerManagement.playerDataList.get(uuid);
        int banCount = playerData.getPunishmentNumberOfTimes();

        int banType = -1;
        int tBanMinutes = -1;

        for(int i=0; i<banTypeModelList.size(); i++){
            BanTypeModel banTypeModel = banTypeModelList.get(i);
            if(banTypeModel.getNumber() == banCount){
                banType = banTypeModel.getBanType();
                tBanMinutes = banTypeModel.gettBanMinutes();
                break;
            }

            //最後になっても該当するものが無い(設定値以上にBANを行う場合)
            if(i == banTypeModelList.size() - 1){
                banType = banTypeModel.getBanType();
                tBanMinutes = banTypeModel.gettBanMinutes();
            }
        }

        //値の取得に失敗した場合、終了
        if(banType == -1 || tBanMinutes == -1){
            return false;
        }

        return ban(player, banType, tBanMinutes);
    }

    /**
     * BANを実行
     * @param player
     * @param banType
     * @param tBanMinutes
     * @return
     */
    public boolean ban(Player player, int banType, int tBanMinutes){

        InfoDao infoDao = new InfoDao();
        String uuid = player.getUniqueId().toString();

        if(banType == 0){
            //KICK
            player.kickPlayer(mainConfig.getPunishmentReason());
            if(mainConfig.isUseDiscordNotifyForAutoBan()){
                sendDiscord(player, banType);
                infoDao.countUpPunishmentNumberOfTimes(uuid);
            }
            return true;
        }

        String ip = player.getAddress().getHostName();
        BanList uuidBanList = Bukkit.getBanList(BanList.Type.NAME);
        BanList ipBanList = Bukkit.getBanList(BanList.Type.IP);

        if(banType == 1){
            //TBAN
            uuidBanList.addBan(uuid, mainConfig.getPunishmentReason(), getExpires(tBanMinutes), null);
            player.kickPlayer(mainConfig.getPunishmentReason());
            if(mainConfig.isUseIpBan()){
                ipBanList.addBan(ip, mainConfig.getPunishmentReason(), getExpires(tBanMinutes), null);
            }
            if(mainConfig.isUseDiscordNotifyForAutoBan()){
                sendDiscord(player, banType);
            }
            infoDao.countUpPunishmentNumberOfTimes(uuid);
            return true;
        }else if(banType == 2){
            //BAN
            uuidBanList.addBan(uuid, mainConfig.getPunishmentReason(), null, null);
            player.kickPlayer(mainConfig.getPunishmentReason());
            if(mainConfig.isUseIpBan()){
                ipBanList.addBan(ip, mainConfig.getPunishmentReason(), null, null);
            }
            if(mainConfig.isUseDiscordNotifyForAutoBan()){
                sendDiscord(player, banType);
            }
            infoDao.countUpPunishmentNumberOfTimes(uuid);
            return true;
        }

        return false;
    }

    public void sendDiscord(Player player, int banType){

        DiscordUtil discordUtil = new DiscordUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        StringBuilder message = new StringBuilder();

        if(mainConfig.isUseDiscordNotifyForAutoBan()){
            message.append("<@");
            message.append(mainConfig.getNotifyMentionId());
            message.append(">");
        }
        message.append(messageConfig.getPrefix());
        message.append(" ");
        if(banType == 0) {
            //Kick
            message.append(DiscordMessage.NotifyAutoKick);
        }else if(banType == 1){
            //TBAN
            message.append(DiscordMessage.NotifyAutoTBan);
        }else if(banType == 2){
            //BAN
            message.append(DiscordMessage.NotifyAutoBan);
        }

        discordUtil.sendMessage(convertUtil.placeholderUtil("{player}", player.getDisplayName(), message.toString()));
    }

    /**
     * Configの値から、TBAN時の期限を計算
     * @param minutes
     * @return
     */
    public Date getExpires(int minutes){

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, minutes);

        return calendar.getTime();
    }

    public void resetCount(){

        Runnable resetUtil = new Runnable() {
            @Override
            public void run() {

                Collection<? extends Player> playerList = Bukkit.getServer().getOnlinePlayers();

                for(Player player: playerList){
                    String uuid = player.getUniqueId().toString();
                    if(BeginnerManagement.playerNumberOfViolations.containsKey(uuid)){
                        BeginnerManagement.playerNumberOfViolations.put(uuid, 0);
                    }
                }
            }
        };

        BeginnerManagement.resetCount = Bukkit.getServer().getScheduler().runTaskTimer(BeginnerManagement.getInstance(), resetUtil, 0, 12000L);

    }
}
