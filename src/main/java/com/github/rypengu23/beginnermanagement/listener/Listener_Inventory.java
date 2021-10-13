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
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.text.SimpleDateFormat;

public class Listener_Inventory implements Listener {

    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Listener_Inventory() {
        updateConfig();
    }

    public void updateConfig(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    @EventHandler
    /**
     * インベントリ内でクリックしたアイテムがConfigに登録されている場合、無効化する。
     */
    public void CheckInventoryClick(InventoryClickEvent event){

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();
        updateConfig();

        Player player = (Player) event.getWhoClicked();
        Material item = event.getCurrentItem().getType();


        //Configに記載されていない場合
        if (!mainConfig.getItems().contains(item.name())) {
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
        player.closeInventory();
        player.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        if(mainConfig.isUseDiscordSRV()){
            discordUtil.sendMessageConsoleChannel("[BeginnerManagement]"+ player.getDisplayName() +":"+ "Dangerous acts(Inventory)");
        }
    }

    @EventHandler
    /**
     * インベントリ内でドラッグしたアイテムがConfigに登録されている場合、無効化する。
     */
    public void CheckInventoryDrag(InventoryDragEvent event){

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();
        updateConfig();

        Player player = (Player) event.getWhoClicked();
        Material item = event.getOldCursor().getType();


        //Configに記載されていない場合
        if (!mainConfig.getItems().contains(item.name())) {
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
        player.closeInventory();
        player.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        if(mainConfig.isUseDiscordSRV()){
            discordUtil.sendMessageConsoleChannel("[BeginnerManagement]"+ player.getDisplayName() +":"+ "Dangerous acts(Inventory)");
        }
    }

    @EventHandler
    /**
     * インベントリ内でドロップしたアイテムがConfigに登録されている場合、無効化する。
     */
    public void CheckInventoryDrop(PlayerDropItemEvent event){

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();
        updateConfig();

        Player player = event.getPlayer();
        Material item = event.getItemDrop().getItemStack().getType();


        //Configに記載されていない場合
        if (!mainConfig.getItems().contains(item.name())) {
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
        player.closeInventory();
        player.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        if(mainConfig.isUseDiscordSRV()){
            discordUtil.sendMessageConsoleChannel("[BeginnerManagement]"+ player.getDisplayName() +":"+ "Dangerous acts(Inventory)");
        }
    }
}
