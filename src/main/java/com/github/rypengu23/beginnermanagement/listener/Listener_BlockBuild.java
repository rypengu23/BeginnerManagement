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
     * 破壊したブロックがConfigに登録されている場合、または破壊そのものが制限されている場合、キャンセルする。
     */
    public void BlockBreakCheck(BlockBreakEvent event) {

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

        //ブロック破壊制限チェック
        if(!playerDataUtil.checkBreakPermission(playerData)){
            event.setCancelled(true);

            //プレイヤーにメッセージ送信
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
            String time = format.format(playerDataUtil.getBreakPossibleDate(playerData).getTime());
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
                message.append(DiscordMessage.NotifyActOfLimit_Break);

                discordUtil.sendMessage(convertUtil.placeholderUtil("{player}", player.getDisplayName(), message.toString()));
            }

            //自動BAN判定
            AutoBanUtil autoBanUtil = new AutoBanUtil();
            autoBanUtil.plusNumberOfViolations(playerData.getUUID());
            autoBanUtil.checkPunishmentTime(player);

            //終了
            return;
        }

        //Configに記載されているブロックか確認
        Material item = event.getBlock().getType();
        if (!mainConfig.getBuild().contains(item.name())) {
            //スキップ
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
            message.append(DiscordMessage.NotifyActOfLimit_Break);

            discordUtil.sendMessage(convertUtil.placeholderUtil("{player}", player.getDisplayName(), message.toString()));
        }

        //自動BAN判定
        AutoBanUtil autoBanUtil = new AutoBanUtil();
        autoBanUtil.plusNumberOfViolations(playerData.getUUID());
        autoBanUtil.checkPunishmentTime(player);
    }

    @EventHandler
    /**
     * 設置したブロックがConfigに登録されている場合、無効化する。
     */
    public void BlockPlaceCheck(BlockPlaceEvent event){

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

        //Configに記載されているブロックか確認
        Material item = event.getBlock().getType();
        if (!mainConfig.getBuild().contains(item.name())) {
            //スキップ
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
            message.append(DiscordMessage.NotifyActOfLimit_Build);

            discordUtil.sendMessage(convertUtil.placeholderUtil("{player}", player.getDisplayName(), message.toString()));
        }

        //自動BAN判定
        AutoBanUtil autoBanUtil = new AutoBanUtil();
        autoBanUtil.plusNumberOfViolations(playerData.getUUID());
        autoBanUtil.checkPunishmentTime(player);
    }
}
