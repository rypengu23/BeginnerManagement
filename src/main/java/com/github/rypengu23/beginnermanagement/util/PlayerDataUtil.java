package com.github.rypengu23.beginnermanagement.util;

import com.github.rypengu23.beginnermanagement.BeginnerManagement;
import com.github.rypengu23.beginnermanagement.config.ConfigLoader;
import com.github.rypengu23.beginnermanagement.config.MainConfig;
import com.github.rypengu23.beginnermanagement.config.MessageConfig;
import com.github.rypengu23.beginnermanagement.model.PlayerDataModel;
import org.bukkit.entity.Player;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlayerDataUtil {
    private final ConfigLoader configLoader;
    private final MainConfig mainConfig;
    private final MessageConfig messageConfig;

    public static ArrayList<PlayerDataModel> playerData;

    public PlayerDataUtil(){
        configLoader = new ConfigLoader();
        mainConfig = configLoader.getMainConfig();
        messageConfig = configLoader.getMessageConfig();
    }

    /**
     * プレイヤーデータを更新
     */
    public void updatePlayerData(){

        File playerDataFile = new File(BeginnerManagement.getInstance().getDataFolder().getPath() + "/playerdata.txt");

        if(!playerDataFile.exists()){
            try {
                playerDataFile.createNewFile();
            } catch (IOException e) {
            }
        }

        playerData = getPlayerDateList();
    }

    /**
     * プレイヤーの初回ログイン情報等のリストを全取得
     * @return
     */
    public ArrayList<PlayerDataModel> getPlayerDateList(){

        ConvertUtil convertUtil = new ConvertUtil();

        ArrayList<PlayerDataModel> resultList = new ArrayList<>();

        try {
            File playerDataFile = new File(BeginnerManagement.getInstance().getDataFolder().getPath() + "/playerdata.txt");
            FileReader filereader = new FileReader(playerDataFile);
            BufferedReader br = new BufferedReader(filereader);

            String str;
            while((str = br.readLine()) != null){
                PlayerDataModel playerDataModel = new PlayerDataModel();

                playerDataModel.setUUID(str.split(",")[0]);
                playerDataModel.setFirstLoginDate(convertUtil.convertCalendar(str.split(",")[1]));
                if(Integer.parseInt(str.split(",")[2]) == 0){
                    playerDataModel.setWildcard(false);
                }else{
                    playerDataModel.setWildcard(true);
                }

                resultList.add(playerDataModel);
            }


        } catch (IOException e) {

        }

        return resultList;

    }

    public void saveNewPlayerData(Player player){

        CheckUtil checkUtil = new CheckUtil();
        ConvertUtil convertUtil = new ConvertUtil();

        Date nowDate = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        String time = format.format(nowDate);

        try {
            File playerDataFile = new File(BeginnerManagement.getInstance().getDataFolder().getPath() + "/playerdata.txt");

            if (checkUtil.checkBeforeWriteFile(playerDataFile)){
                FileWriter filewriter = new FileWriter(playerDataFile, true);

                if(player.isOp()) {
                    filewriter.write(player.getUniqueId().toString() + "," + time + ",1\r\n");
                }else{
                    filewriter.write(player.getUniqueId().toString() + "," + time + ",0\r\n");
                }

                filewriter.close();
            }else{

            }


        } catch (IOException e) {

        }

    }

    public boolean checkOpen(Player player){

        Calendar calendar = Calendar.getInstance();

        //プレイヤーが危険行為制限時間を超えているかどうか
        if(getOpenDate(player).compareTo(calendar) < 0){
            //超えている場合
            return true;
        }
        return false;

    }

    /**
     * 引数のプレイヤーの初回ログイン日時等の情報を取得
     * @param player
     * @return
     */
    public PlayerDataModel getPlayerData(Player player){

        try {
            for (PlayerDataModel playerDataModel : this.playerData) {
                if (playerDataModel.getUUID().equalsIgnoreCase(player.getUniqueId().toString())) {
                    return playerDataModel;
                }
            }
        }catch(NullPointerException e){
            saveNewPlayerData(player);
            updatePlayerData();
            getPlayerData(player);
        }

        return null;
    }

    /**
     * 引数のプレイヤーの危険行為開放日時の情報を取得
     * @param player
     * @return
     */
    public Calendar getOpenDate(Player player){

        for(PlayerDataModel playerDataModel:this.playerData){
            if(playerDataModel.getUUID().equalsIgnoreCase(player.getUniqueId().toString())){

                Calendar firstLoginDate = (Calendar) playerDataModel.getFirstLoginDate().clone();
                firstLoginDate.add(Calendar.DATE, mainConfig.getDay());
                firstLoginDate.add(Calendar.HOUR_OF_DAY, mainConfig.getHour());
                firstLoginDate.add(Calendar.MINUTE, mainConfig.getMinute());

                return firstLoginDate;
            }
        }

        return null;
    }


}
