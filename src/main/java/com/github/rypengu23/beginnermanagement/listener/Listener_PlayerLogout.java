package com.github.rypengu23.beginnermanagement.listener;

import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class Listener_PlayerLogout implements Listener {

    @EventHandler
    /**
     * プレイヤーログアウト時、メモリアンロード
     */
    public void PlayerLogout(PlayerQuitEvent event){

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        playerDataUtil.unloadPlayerData(event.getPlayer());
    }
}
