package com.github.rypengu23.beginnermanagement.listener;

import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener_PlayerJoinEvent implements Listener {

    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Listener_PlayerJoinEvent() {
        updateConfig();
    }

    public void updateConfig(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    @EventHandler
    /**
     * 新規プレイヤーログイン時、データを更新。
     */
    public void PlayerLogin(PlayerJoinEvent event){

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        String UUID = event.getPlayer().getUniqueId().toString();

        playerDataUtil.updatePlayerData();

        if(playerDataUtil.getPlayerData(event.getPlayer()) != null){
            return;
        }

        playerDataUtil.saveNewPlayerData(event.getPlayer());
        playerDataUtil.updatePlayerData();
    }
}
