package com.github.rypengu23.beginnermanagement.command;

import com.github.rypengu23.beginnermanagement.config.CommandMessage;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.dao.InfoDao;
import com.github.rypengu23.beginnermanagement.model.PlayerDataModel;
import com.github.rypengu23.beginnermanagement.util.CheckUtil;
import com.github.rypengu23.beginnermanagement.util.ConvertUtil;
import com.github.rypengu23.beginnermanagement.util.PlayerDataUtil;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Command_Info {

    private final ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Command_Info(){
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    /**
     * infoコマンドの処理振り分け
     * @param sender
     * @param args
     */
    public void sort(CommandSender sender, String args[]){

        if(args[0].equalsIgnoreCase("info")){

            if(args.length == 1 && sender instanceof Player){
                //自分の情報表示
                Player player = (Player)sender;
                showStatus(player);
            }else if(args.length == 2){
                //他人の情報表示
                showStatus(sender, args[1]);
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
     * infoコマンドか判定
     * @param command
     * @return
     */
    public boolean checkCommandExit(String command){
        CheckUtil checkUtil = new CheckUtil();
        if(checkUtil.checkNullOrBlank(command)){
            return false;
        }

        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("info");

        return commandList.contains(command.toLowerCase());
    }

    /**
     * 自分のステータスを表示
     * @param player
     */
    private void showStatus(Player player){

        ConvertUtil convertUtil = new ConvertUtil();
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        InfoDao infoDao = new InfoDao();


        //権限チェック
        if(!player.hasPermission("beginnerManagement.info")){
            player.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.DoNotHavePermission);
            return;
        }

        //プレイヤー情報取得
        PlayerDataModel playerData = infoDao.getPlayerData(player.getUniqueId().toString());
        //DB存在チェック
        if(playerData == null){
            player.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.Command_Info_PlayerNotFound);
            return;
        }


        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(playerDataUtil.getRestrictionLiftDate(playerData).getTime());

        player.sendMessage("§a"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{time}", time, messageConfig.getInfo()));
    }

    /**
     * ID指定でステータスを表示
     * @param sender
     * @param playerName
     */
    private void showStatus(CommandSender sender, String playerName){

        ConvertUtil convertUtil = new ConvertUtil();
        PlayerDataUtil playerDataUtil = new PlayerDataUtil();
        InfoDao infoDao = new InfoDao();

        //権限チェック
        if(!sender.hasPermission("beginnerManagement.anotherInfo")){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.DoNotHavePermission);
            return;
        }

        //プレイヤー情報取得
        PlayerDataModel playerData = infoDao.getPlayerDataFromPlayerName(playerName);
        //DB存在チェック
        if(playerData == null){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.Command_Info_PlayerNotFound);
            return;
        }

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String firstLogin = format.format(playerData.getFirstLoginDate());
        String openDate = format.format(playerDataUtil.getRestrictionLiftDate(playerData).getTime());

        sender.sendMessage(convertUtil.placeholderUtil("{player}", playerName, CommandMessage.Command_Info_SHOWSTATUS1));
        sender.sendMessage(convertUtil.placeholderUtil("{firstlogin}", firstLogin, CommandMessage.Command_Info_SHOWSTATUS2));
        sender.sendMessage(convertUtil.placeholderUtil("{opendate}", openDate, CommandMessage.Command_Info_SHOWSTATUS3));
        sender.sendMessage(CommandMessage.Command_Info_SHOWSTATUS4);
    }
}
