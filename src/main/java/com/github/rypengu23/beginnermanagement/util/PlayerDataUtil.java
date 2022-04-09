package com.github.rypengu23.beginnermanagement.util;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.ConsoleMessage;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.dao.InfoDao;
import com.github.rypengu23.beginnermanagement.model.PlayerDataModel;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Calendar;
import java.util.UUID;

public class PlayerDataUtil {
    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public PlayerDataUtil(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    /**
     * プレイヤーデータを作成・読み込み
     * @param player
     */
    public void loadPlayerData(Player player) {

        InfoDao infoDao = new InfoDao();
        String uuid = player.getUniqueId().toString();
        String playerName = player.getName();

        PlayerDataModel playerDataModel = infoDao.getPlayerData(uuid);

        if (playerDataModel == null) {
            //新規の場合
            infoDao.insertNewPlayerInfo(uuid,playerName);
            playerDataModel = infoDao.getPlayerData(uuid);
            Bukkit.getLogger().info("[BeginnerManagement] " + ConsoleMessage.PlayerDataUtil_CreateNewPlayerData);
        }else{
            //既存の場合
            //名前変更確認
            if(playerDataModel.getPlayerName() == null){
                infoDao.updateNewPlayerName(uuid, playerName);
                playerDataModel = infoDao.getPlayerData(uuid);
            }else if(!playerDataModel.getPlayerName().equals(playerName)) {
                PlayerDataModel duplicatePlayerData = infoDao.getPlayerDataFromPlayerName(playerName);
                if(duplicatePlayerData!=null){
                    //更新後IDがほかユーザーに設定されている場合
                    String duplicatePlayerNewName = Bukkit.getOfflinePlayer(UUID.fromString(duplicatePlayerData.getUUID())).getName();
                    infoDao.updateNewPlayerName(duplicatePlayerData.getUUID(), duplicatePlayerNewName);
                }
                infoDao.updateNewPlayerName(uuid, playerName);
                playerDataModel = infoDao.getPlayerData(uuid);
            }
        }

        //メモリにセット
        BeginnerManagement.playerDataList.put(playerDataModel.getUUID(), playerDataModel);
        BeginnerManagement.playerNumberOfViolations.put(playerDataModel.getUUID(), 0);
    }

    /**
     * プレイヤーデータをアンロード
     * @param player
     */
    public void unloadPlayerData(Player player) {

        //メモリから削除
        String uuid = player.getUniqueId().toString();

        BeginnerManagement.playerDataList.remove(uuid);
        BeginnerManagement.playerNumberOfViolations.remove(uuid);
    }

    /**
     * 引数のプレイヤーのブロック破壊制限時刻を取得
     * @param playerDataModel
     * @return
     */
    public Calendar getRestrictionLiftDate(PlayerDataModel playerDataModel){

        //初回ログイン日を取得
        Calendar firstLoginDate = Calendar.getInstance();
        firstLoginDate.setTime(playerDataModel.getFirstLoginDate());

        //制限終了時刻を計算
        Calendar limitDate = (Calendar) firstLoginDate.clone();
        limitDate.add(Calendar.DAY_OF_MONTH, mainConfig.getDay());
        limitDate.add(Calendar.HOUR_OF_DAY, mainConfig.getHour());
        limitDate.add(Calendar.MINUTE, mainConfig.getMinute());

        return limitDate;
    }

    /**
     * 引数のプレイヤーが制限時刻を過ぎているか
     * @param playerDataModel
     * @return
     */
    public boolean checkRestrictionLiftDate(PlayerDataModel playerDataModel){

        //初回ログイン日を取得
        Calendar firstLoginDate = Calendar.getInstance();
        firstLoginDate.setTime(playerDataModel.getFirstLoginDate());

        //制限終了時刻を計算
        Calendar limitDate = (Calendar) firstLoginDate.clone();
        limitDate.add(Calendar.DAY_OF_MONTH, mainConfig.getDay());
        limitDate.add(Calendar.HOUR_OF_DAY, mainConfig.getHour());
        limitDate.add(Calendar.MINUTE, mainConfig.getMinute());

        //現在時刻
        Calendar now = Calendar.getInstance();

        //現在時刻が制限終了時刻を過ぎているか
        int check = now.compareTo(limitDate);

        if(check > 0){
            return true;
        }

        return false;
    }

    /**
     * 破壊可能時刻を取得
     * @param playerDataModel
     * @return
     */
    public Calendar getBreakPossibleDate(PlayerDataModel playerDataModel){

        //初回ログイン日を取得
        Calendar firstLoginDate = Calendar.getInstance();
        firstLoginDate.setTime(playerDataModel.getFirstLoginDate());

        //ブロック破壊制限終了時刻を計算
        Calendar breakLimitDate = (Calendar) firstLoginDate.clone();
        breakLimitDate.add(Calendar.DAY_OF_MONTH, mainConfig.getBreakDay());
        breakLimitDate.add(Calendar.HOUR_OF_DAY, mainConfig.getBreakHour());
        breakLimitDate.add(Calendar.MINUTE, mainConfig.getBreakMinute());

        return breakLimitDate;
    }

    /**
     * 現在時刻が破壊可能時刻を過ぎているかどうか
     * @param playerDataModel
     * @return
     */
    public boolean checkBreakPermission(PlayerDataModel playerDataModel){

        //初回ログイン日を取得
        Calendar firstLoginDate = Calendar.getInstance();
        firstLoginDate.setTime(playerDataModel.getFirstLoginDate());

        //ブロック破壊制限終了時刻を計算
        Calendar breakLimitDate = (Calendar) firstLoginDate.clone();
        breakLimitDate.add(Calendar.DAY_OF_MONTH, mainConfig.getBreakDay());
        breakLimitDate.add(Calendar.HOUR_OF_DAY, mainConfig.getBreakHour());
        breakLimitDate.add(Calendar.MINUTE, mainConfig.getBreakMinute());

        //現在時刻
        Calendar now = Calendar.getInstance();

        //現在時刻がブロック破壊制限終了時刻を過ぎているか
        int check = now.compareTo(breakLimitDate);

        if(check > 0){
            return true;
        }

        return false;
    }

    /**
     * プレイヤー名からUUIDを取得
     * @param playerName
     * @return
     */
    public PlayerDataModel getUUID(String playerName) {
        InfoDao infoDao = new InfoDao();

        PlayerDataModel playerData = infoDao.getPlayerDataFromPlayerName(playerName);
        return playerData;
    }
}
