package com.github.rypengu23.beginnermanagement.command;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.*;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import com.github.rypengu23.beginnermanagement.util.WhitelistUtil;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

public class Command_Config {
    private final ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Command_Config(){
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void reloadConfig(CommandSender sender){

        //権限チェック
        if(!sender.hasPermission("beginnerManagement.reload")){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.BeginnerManagement_DoNotHavePermission);
            return;
        }

        //Config再読み込み
        configLoader.reloadConfig();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();

        //DiscordSRV再読み込み
        if(mainConfig.isUseDiscordSRV()){
            try {
                Bukkit.getLogger().info("[BeginnerManagement] "+ ConsoleMessage.BeginnerManagement_loadDiscordSRV);
                BeginnerManagement.discordSRV = (DiscordSRV) Bukkit.getServer().getPluginManager().getPlugin("DiscordSRV");
                Bukkit.getLogger().info("[BeginnerManagement] "+ ConsoleMessage.BeginnerManagement_loadCompDiscordSRV);
            }catch(NoClassDefFoundError e){
                Bukkit.getLogger().warning("[BeginnerManagement] "+ ConsoleMessage.BeginnerManagement_loadFailureDiscordSRV);
            }
        }

        //プレイヤーデータ再読み込み
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        playerDataUtil.updatePlayerData();
        WhitelistUtil whitelistUtil = new WhitelistUtil();
        whitelistUtil.updateWhitelistData();

        //完了メッセージ
        sender.sendMessage("§a"+ messageConfig.getPrefix() +" §f"+ CommandMessage.BeginnerManagement_ConfigReload);
    }
}

