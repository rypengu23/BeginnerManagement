package com.github.rypengu23.beginnermanagement.listener;

import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Listener_PlayerJoinEvent implements Listener {

    @EventHandler
    /**
     * 新規プレイヤーログイン時、データ取得
     */
    public void PlayerLogin(PlayerJoinEvent event){

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        playerDataUtil.loadPlayerData(event.getPlayer());
    }
}
