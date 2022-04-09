package com.github.rypengu23.beginnermanagement.listener;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.DiscordMessage;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.model.PlayerDataModel;
import com.github.rypengu23.beginnermanagement.util.AutoBanUtil;
import com.github.rypengu23.beginnermanagement.util.ConvertUtil;
import com.github.rypengu23.beginnermanagement.util.DiscordUtil;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
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
    public void checkCreateEntity(PlayerInteractEvent event) {

        //右クリックか判定
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();

        //権限を持っている場合
        if(player.hasPermission("beginnerManagement.allow")){
            //スキップ
            return;
        }

        updateConfig();
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();

        //プレイヤー情報取得
        if(!BeginnerManagement.playerDataList.containsKey(player.getUniqueId().toString())){
            playerDataUtil.loadPlayerData(player);
        }
        PlayerDataModel playerData = BeginnerManagement.playerDataList.get(player.getUniqueId().toString());

        //ホワイトリストに記載されている場合
        if (playerData.isWhitelist()){
            //スキップ
            return;
        }

        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();

        //Configに記載されているエンティティかチェック
        if (!mainConfig.getCreateEntity().contains(player.getInventory().getItemInMainHand().getType().name())) {
            return;
        }

        //所定の時間が経過している場合
        if (playerDataUtil.checkRestrictionLiftDate(playerData)) {
            //スキップ
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(playerDataUtil.getRestrictionLiftDate(playerData).getTime());

        event.setCancelled(true);
        player.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        //Discordにメッセージ送信
        if(mainConfig.isUseDiscordSRV()){
            StringBuilder message = new StringBuilder();
            if(mainConfig.isUseDiscordNotify()){
                message.append("<@");
                message.append(mainConfig.getNotifyMentionId());
                message.append(">");
            }
            message.append(messageConfig.getPrefix());
            message.append(" ");
            message.append(DiscordMessage.NotifyActOfLimitCommon);
            message.append(DiscordMessage.NotifyActOfLimit_CreateEntity);

            discordUtil.sendMessage(convertUtil.placeholderUtil("{player}", player.getDisplayName(), message.toString()));
        }

        //自動BAN判定
        AutoBanUtil autoBanUtil = new AutoBanUtil();
        autoBanUtil.plusNumberOfViolations(playerData.getUUID());
        autoBanUtil.checkPunishmentTime(player);
    }

    @EventHandler
    /**
     * エンティティがプレイヤーによって攻撃された場合、無効化する。
     */
    public void CheckDamageEntity(EntityDamageByEntityEvent event) {

        //プレイヤーか判定
        if (event.getDamager().getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getDamager();

        //権限を持っている場合
        if (player.hasPermission("beginnerManagement.allow")) {
            //スキップ
            return;
        }

        updateConfig();
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();

        //プレイヤー情報取得
        if (!BeginnerManagement.playerDataList.containsKey(player.getUniqueId().toString())) {
            playerDataUtil.loadPlayerData(player);
        }
        PlayerDataModel playerData = BeginnerManagement.playerDataList.get(player.getUniqueId().toString());

        //ホワイトリストに記載されている場合
        if (playerData.isWhitelist()) {
            //スキップ
            return;
        }

        ConvertUtil convertUtil = new ConvertUtil();
        DiscordUtil discordUtil = new DiscordUtil();

        //Configに記載されていない場合
        boolean flag = true;
        for (String entityName : mainConfig.getDamageEntity()) {
            if (event.getEntityType().name().equalsIgnoreCase(entityName)) {
                flag = false;
            }
        }
        if (flag) {
            return;
        }

        //所定の時間が経過している場合
        if (playerDataUtil.checkRestrictionLiftDate(playerData)) {
            //スキップ
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(playerDataUtil.getRestrictionLiftDate(playerData).getTime());

        event.setCancelled(true);
        player.sendMessage("§c" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{time}", time, messageConfig.getWarn()));

        //Discordにメッセージ送信
        if(mainConfig.isUseDiscordSRV()){
            StringBuilder message = new StringBuilder();
            if(mainConfig.isUseDiscordNotify()){
                message.append("<@");
                message.append(mainConfig.getNotifyMentionId());
                message.append(">");
            }
            message.append(messageConfig.getPrefix());
            message.append(" ");
            message.append(DiscordMessage.NotifyActOfLimitCommon);
            message.append(DiscordMessage.NotifyActOfLimit_DamageEntity);

            discordUtil.sendMessage(convertUtil.placeholderUtil("{player}", player.getDisplayName(), message.toString()));
        }

        //自動BAN判定
        AutoBanUtil autoBanUtil = new AutoBanUtil();
        autoBanUtil.plusNumberOfViolations(playerData.getUUID());
        autoBanUtil.checkPunishmentTime(player);
    }
}
