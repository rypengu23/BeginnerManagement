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
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Command_Whitelist {

    private final ConfigLoader configLoader;
    private MainConfig mainConfig;
    private MessageConfig messageConfig;

    public Command_Whitelist() {
        this.configLoader = new ConfigLoader();
        this.mainConfig = configLoader.getMainConfig();
        this.messageConfig = configLoader.getMessageConfig();
    }

    /**
     * whitelistコマンドの処理振り分け
     * @param sender
     * @param args
     */
    public void sort(CommandSender sender, String args[]){
        Player player = (Player)sender;

        if(args[0].equalsIgnoreCase("whitelist")){

            if(args.length == 3) {

                if(args[1].equalsIgnoreCase("add")) {
                    addWhitelist(sender, args[2]);
                }else if(args[1].equalsIgnoreCase("remove")) {
                    removeWhitelist(sender, args[2]);
                }else{
                    //不正
                    player.sendMessage("§c["+ messageConfig.getPrefix() +"] §f" + CommandMessage.CommandFailure);
                    return;
                }

            }else{
                //不正
                player.sendMessage("§c["+ messageConfig.getPrefix() +"] §f" + CommandMessage.CommandFailure);
                return;
            }
        }else{
            //不正
            player.sendMessage("§c["+ messageConfig.getPrefix() +"] §f" + CommandMessage.CommandFailure);
            return;
        }
    }

    /**
     * whitelistコマンドか判定
     * @param command
     * @return
     */
    public boolean checkCommandExit(String command){
        CheckUtil checkUtil = new CheckUtil();
        if(checkUtil.checkNullOrBlank(command)){
            return false;
        }

        ArrayList<String> commandList = new ArrayList<>();
        commandList.add("whitelist");

        return commandList.contains(command.toLowerCase());
    }

    private void addWhitelist(CommandSender sender, String playerName) {

        ConvertUtil convertUtil = new ConvertUtil();
        InfoDao infoDao = new InfoDao();

        //権限チェック
        if (!sender.hasPermission("beginnerManagement.whitelist")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.DoNotHavePermission);
            return;
        }

        //プレイヤー情報取得
        PlayerDataModel playerData = infoDao.getPlayerDataFromPlayerName(playerName);
        //DB存在チェック
        if(playerData == null){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.Command_Info_PlayerNotFound);
            return;
        }

        String uuid = playerData.getUUID();

        //既に設定済みかチェック
        if (playerData.isWhitelist()) {
            //設定済みの場合
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + convertUtil.placeholderUtil("{player}", playerName, CommandMessage.Command_Whitelist_Added));
            return;
        }

        //ホワイトリストフラグをセット
        infoDao.enableWhiteList(uuid);

        //メモリリロード
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            PlayerDataUtil playerDataUtil = new PlayerDataUtil();
            playerDataUtil.loadPlayerData(player);
        }

        sender.sendMessage("§a"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{player}", playerName, CommandMessage.Command_Whitelist_Add));
    }

    private void removeWhitelist(CommandSender sender, String playerName) {

        ConvertUtil convertUtil = new ConvertUtil();
        InfoDao infoDao = new InfoDao();

        //権限チェック
        if (!sender.hasPermission("beginnerManagement.whitelist")) {
            sender.sendMessage("§c" + messageConfig.getPrefix() + " §f" + CommandMessage.DoNotHavePermission);
            return;
        }

        //プレイヤー情報取得
        PlayerDataModel playerData = infoDao.getPlayerDataFromPlayerName(playerName);
        //DB存在チェック
        if(playerData == null){
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ CommandMessage.Command_Info_PlayerNotFound);
            return;
        }

        String uuid = playerData.getUUID();

        //既に設定済みかチェック
        if(!playerData.isWhitelist()){
            //設定済みの場合
            sender.sendMessage("§c"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{player}", playerName, CommandMessage.Command_Whitelist_Removed));
            return;
        }

        //ホワイトリストフラグをセット
        infoDao.disableWhiteList(uuid);

        //メモリリロード
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            PlayerDataUtil playerDataUtil = new PlayerDataUtil();
            playerDataUtil.loadPlayerData(player);
        }

        sender.sendMessage("§a"+ messageConfig.getPrefix() +" §f"+ convertUtil.placeholderUtil("{player}", playerName, CommandMessage.Command_Whitelist_Remove));
    }
}
