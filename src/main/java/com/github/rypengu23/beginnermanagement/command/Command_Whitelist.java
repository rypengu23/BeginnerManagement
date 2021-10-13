package com.github.rypengu23.beginnermanagement.command;

import com.github.rypengu23.beginnermanagement.config.CommandMessage;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.util.ConvertUtil;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import com.github.rypengu23.beginnermanagement.util.WhitelistUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_Whitelist {

    private final ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Command_Whitelist() {
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void addWhitelist(CommandSender sender, String playerName) {

        ConvertUtil convertUtil = new ConvertUtil();
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        WhitelistUtil whitelistUtil = new WhitelistUtil();

        //権限チェック
        if (!sender.hasPermission("beginnerManagement.whitelist")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.BeginnerManagement_DoNotHavePermission);
            return;
        }

        Player player = Bukkit.getPlayer(playerName);
        //存在チェック
        if(playerDataUtil.getPlayerData(player) == null){
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.Command_Whitelist_PlayerNotFound);
            return;
        }
        if(whitelistUtil.checkWhitelist(player)){
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.Command_Whitelist_Added);
            return;
        }

        whitelistUtil.addWhitelist(player);
        whitelistUtil.updateWhitelistData();
        sender.sendMessage("§a"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{player}", player.getName(), CommandMessage.Command_Whitelist_Add));
    }
}
