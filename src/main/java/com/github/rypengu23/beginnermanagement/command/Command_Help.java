package com.github.rypengu23.beginnermanagement.command;

import com.github.rypengu23.beginnermanagement.config.CommandMessage;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.util.CheckUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    /**
     * helpコマンドの処理振り分け
     * @param sender
     * @param args
     */
    public void sort(CommandSender sender, String args[]){

        if(args[0].equalsIgnoreCase("help")){

            if(args.length == 1) {
                //help
                showHelp(sender, "0");
            }else if(args.length == 2){
                //help(ページ指定あり)
                showHelp(sender, args[1]);
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
     * helpコマンドか判定
     * @param command
     * @return
     */
    public boolean checkCommandExit(String command){
        CheckUtil checkUtil = new CheckUtil();
        if(checkUtil.checkNullOrBlank(command)){
            return false;
        }

        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("help");

        return commandList.contains(command.toLowerCase());
    }

    private void showHelp(CommandSender sender, String page){

        int pageNumber = 0;
        //引数が数字かチェック
        try {
            pageNumber = Integer.parseInt(page);
        } catch(NumberFormatException e){
            sender.sendMessage("§c" + messageConfig.getPrefix() +"§f "+ CommandMessage.CommandFailure);
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
