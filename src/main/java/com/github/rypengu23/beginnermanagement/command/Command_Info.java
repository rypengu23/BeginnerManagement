package com.github.rypengu23.beginnermanagement.command;

import com.github.rypengu23.beginnermanagement.config.CommandMessage;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.util.ConvertUtil;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;

public class Command_Info {

    private final ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Command_Info(){
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void showStatus(CommandSender sender){

        ConvertUtil convertUtil = new ConvertUtil();
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();

        Player player = (Player) sender;

        //権限チェック
        if(!sender.hasPermission("beginnerManagement.info")){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.BeginnerManagement_DoNotHavePermission);
            return;
        }

        //存在チェック
        if(playerDataUtil.getOpenDate(player) == null){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.Command_Info_PlayerNotFound);
            return;
        }


        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(playerDataUtil.getOpenDate(player).getTime());

        sender.sendMessage("§a"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{time}", time, messageConfig.getInfo()));
    }

    public void showStatus(CommandSender sender, String username){

        ConvertUtil convertUtil = new ConvertUtil();
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();

        //権限チェック
        if(!sender.hasPermission("beginnerManagement.anotherInfo")){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.BeginnerManagement_DoNotHavePermission);
            return;
        }

        Player player = Bukkit.getPlayer(username);
        if(playerDataUtil.getPlayerData(player) == null){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.Command_Info_PlayerNotFound);
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String firstLogin = format.format(playerDataUtil.getPlayerData(player).getFirstLoginDate().getTime());
        String openDate = format.format(playerDataUtil.getOpenDate(player).getTime());

        sender.sendMessage(convertUtil.placeholderUtil("{player}", player.getName(), CommandMessage.Command_Info_SHOWSTATUS1));
        sender.sendMessage(convertUtil.placeholderUtil("{firstlogin}", firstLogin, CommandMessage.Command_Info_SHOWSTATUS2));
        sender.sendMessage(convertUtil.placeholderUtil("{opendate}", openDate, CommandMessage.Command_Info_SHOWSTATUS3));
        sender.sendMessage(CommandMessage.Command_Info_SHOWSTATUS4);
    }
}
