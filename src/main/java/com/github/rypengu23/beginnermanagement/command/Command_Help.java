package com.github.rypengu23.beginnermanagement.command;

import com.github.rypengu23.beginnermanagement.config.CommandMessage;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Command_Help {
    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public Command_Help(){
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    public void showHelp(CommandSender sender, String page){

        int pageNumber = 0;
        //引数が数字かチェック
        try {
            pageNumber = Integer.parseInt(page);
        } catch(NumberFormatException e){
            sender.sendMessage("§c" + messageConfig.getPrefix() +"§f "+ CommandMessage.BeginnerManagement_CommandFailure);
            return;
        }

        List<String> showList = new ArrayList<>();
        showList.add(CommandMessage.Command_Help_Line1);
        //表示させるコマンドリストを取得
        if(sender.hasPermission("beginnerManagement.info")){
            showList.add(CommandMessage.Command_Help_Info);
        }
        if(sender.hasPermission("beginnerManagement.anotherInfo")){
            showList.add(CommandMessage.Command_Help_AnotherInfo);
        }
        if(sender.hasPermission("beginnerManagement.whitelist")){
            showList.add(CommandMessage.Command_Help_Whitelist);
        }
        if(sender.hasPermission("beginnerManagement.reload")){
            showList.add(CommandMessage.Command_Help_reload);
        }

        showList.add(CommandMessage.Command_Help_LineLast);

        for(String message:showList){
            sender.sendMessage(message);
        }
    }
}
