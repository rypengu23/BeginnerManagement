package com.github.rypengu23.beginnermanagement.listener;

import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.util.ConvertUtil;
import com.github.rypengu23.beginnermanagement.util.DiscordUtil;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import com.github.rypengu23.beginnermanagement.util.WhitelistUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.text.SimpleDateFormat;

public class Listener_BlockBuild implements Listener {

    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Listener_BlockBuild() {
        updateConfig();
    }

    public void updateConfig(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    @EventHandler
    /**
     * 破壊したブロックがConfigに登録されている場合、または破壊そのものが制限されている場合、無効化する。
     */
    public void BlockBreakCheck(BlockBreakEvent event) {

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();
        updateConfig();

        Player player = event.getPlayer();
        Material item = event.getBlock().getType();

        //権限を持っている場合
        if(player.hasPermission("beginnerManagement.allow")){
            return;
        }

        //Configに記載されていない場合
        if (!mainConfig.getBuild().contains(item.name()) && !mainConfig.isBlockBreak()) {
            return;
        }

        //登録されていないユーザーの場合
        if (playerDataUtil.getPlayerData(player) == null) {
            return;
        }

        //ホワイトリストに記載されている場合
        WhitelistUtil whitelistUtil = new WhitelistUtil();
        if (whitelistUtil.checkWhitelist(player)){
            return;
        }

        //所定の時間が経過している場合
        if (playerDataUtil.checkOpen(player)) {
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(playerDataUtil.getOpenDate(player).getTime());

        event.setCancelled(true);
        player.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        if(mainConfig.isUseDiscordSRV()){
            discordUtil.sendMessageConsoleChannel("[BeginnerManagement]"+ player.getDisplayName() +":"+ "Dangerous acts(Build)");
        }
    }

    @EventHandler
    /**
     * 設置したブロックがConfigに登録されている場合、無効化する。
     */
    public void BlockPlaceCheck(BlockPlaceEvent event){

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();
        updateConfig();

        Player player = event.getPlayer();
        Material item = event.getBlock().getType();

        //権限を持っている場合
        if(player.hasPermission("beginnerManagement.allow")){
            return;
        }

        //Configに記載されていない場合
        if (!mainConfig.getBuild().contains(item.name())) {
            return;
        }

        //登録されていないユーザーの場合
        if (playerDataUtil.getPlayerData(player) == null) {
            return;
        }

        //ホワイトリストに記載されている場合
        WhitelistUtil whitelistUtil = new WhitelistUtil();
        if (whitelistUtil.checkWhitelist(player)){
            return;
        }

        //所定の時間が経過している場合
        if (playerDataUtil.checkOpen(player)) {
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(playerDataUtil.getOpenDate(player).getTime());

        event.setCancelled(true);
        player.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        if(mainConfig.isUseDiscordSRV()){
            discordUtil.sendMessageConsoleChannel("[BeginnerManagement]"+ player.getDisplayName() +":"+ "Dangerous acts(Build)");
        }
    }
}
