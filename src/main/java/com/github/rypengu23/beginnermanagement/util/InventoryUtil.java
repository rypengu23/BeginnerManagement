package com.github.rypengu23.beginnermanagement.util;

import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.model.PlayerDataModel;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class InventoryUtil {

    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public InventoryUtil(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    /**
     * 引数のアイテムを引数のプレイヤーがチェスト等から取得する権限があるか判定
     * @param player
     * @param item
     * @return
     */
    public boolean checkItemPermission(Player player, Material item){

        CheckUtil checkUtil = new CheckUtil();
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();

        PlayerDataModel playerDataModel = playerDataUtil.getPlayerData(player);

        //プレイヤーが危険行為制限時間を超えているかどうか
        if(checkUtil.checkComparisonTime(playerDataModel.getFirstLoginDate(), mainConfig.getDay(), mainConfig.getHour(), mainConfig.getHour())){
            //超えている場合
            return true;
        }
        return false;

    }
}
