package com.github.rypengu23.beginnermanagement.listener;

import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.util.ConvertUtil;
import com.github.rypengu23.beginnermanagement.util.DiscordUtil;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import com.github.rypengu23.beginnermanagement.util.WhitelistUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.text.SimpleDateFormat;

public class Listener_Entity implements Listener {

    private ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Listener_Entity() {
        updateConfig();
    }

    public void updateConfig() {
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    @EventHandler
    /**
     * 設置したエンティティがConfigに登録されている場合、無効化する。
     */
    public void CheckCreateEntity(PlayerInteractEvent event) {

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();
        updateConfig();

        Player player = event.getPlayer();

        //権限を持っている場合
        if(player.hasPermission("beginnerManagement.allow")){
            return;
        }

        //右クリックか判定
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        //Configに記載されていない場合
        if (!mainConfig.getCreateEntity().contains(player.getInventory().getItemInMainHand().getType().name())) {
            return;
        }

        //登録されていないユーザーの場合
        if (playerDataUtil.getPlayerData(player) == null) {
            return;
        }

        //ホワイトリストに記載されている場合
        WhitelistUtil whitelistUtil = new WhitelistUtil();
        if (whitelistUtil.checkWhitelist(player)) {
            return;
        }

        //所定の時間が経過している場合
        if (playerDataUtil.checkOpen(player)) {
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(playerDataUtil.getOpenDate(player).getTime());

        event.setCancelled(true);
        player.sendMessage("§c" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        if (mainConfig.isUseDiscordSRV()) {
            discordUtil.sendMessageConsoleChannel("[BeginnerManagement]" + player.getDisplayName() + ":" + "Dangerous acts(Entity)");
        }
    }

    @EventHandler
    /**
     * エンティティがプレイヤーによって攻撃された場合、無効化する。
     */
    public void CheckDamageEntity(EntityDamageByEntityEvent event) {

        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();
        updateConfig();

        //プレイヤーか判定
        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getDamager();

        //権限を持っている場合
        if(player.hasPermission("beginnerManagement.allow")){
            return;
        }

        //Configに記載されていない場合
        boolean flag = true;
        for(String entityName:mainConfig.getDamageEntity()){
            if(event.getEntityType().name().equalsIgnoreCase(entityName)){
                flag = false;
            }
        }
        if (flag){
            return;
        }

        //登録されていないユーザーの場合
        if (playerDataUtil.getPlayerData(player) == null) {
            return;
        }

        //ホワイトリストに記載されている場合
        WhitelistUtil whitelistUtil = new WhitelistUtil();
        if (whitelistUtil.checkWhitelist(player)) {
            return;
        }

        //所定の時間が経過している場合
        if (playerDataUtil.checkOpen(player)) {
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(playerDataUtil.getOpenDate(player).getTime());

        event.setCancelled(true);
        player.sendMessage("§c" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        if (mainConfig.isUseDiscordSRV()) {
            discordUtil.sendMessageConsoleChannel("[BeginnerManagement]" + player.getDisplayName() + ":" + "Dangerous acts(Entity)");
        }
    }
}
