package com.github.rypengu23.beginnermanagement.command;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.*;
import com.github.rypengu23.beginnermanagement.util.CheckUtil;
import github.scarsz.discordsrv.DiscordSRV;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Command_Config {
    private final ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Command_Config(){
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    /**
     * reloadコマンドの処理振り分け
     * @param sender
     * @param args
     */
    public void sort(CommandSender sender, String args[]){

        if(args[0].equalsIgnoreCase("reload")){

            if(args.length == 1){
                //リロード
                reloadConfig(sender);
            }else{
                //不正
                sender.sendMessage("§c["+ messageConfig.getPrefix() +"] §f" + CommandMessage.CommandFailure);
                return;
            }
        }else{
            //不正
            sender.sendMessage("§c["+ messageConfig.getPrefix() +"] §f" + CommandMessage.CommandFailure);
            return;
        }
    }

    /**
     * reloadコマンドか判定
     * @param command
     * @return
     */
    public boolean checkCommandExit(String command){
        CheckUtil checkUtil = new CheckUtil();
        if(checkUtil.checkNullOrBlank(command)){
            return false;
        }

        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("reload");

        return commandList.contains(command.toLowerCase());
    }

    private void reloadConfig(CommandSender sender){

        //権限チェック
        if(!sender.hasPermission("beginnerManagement.reload")){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.DoNotHavePermission);
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

        //完了メッセージ
        sender.sendMessage("§a"+ messageConfig.getPrefix() +" §f"+ CommandMessage.ConfigReload);
    }
}

