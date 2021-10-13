package com.github.rypengu23.beginnermanagement.util;

import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Calendar;

public class CheckUtil {

    /**
     * 引数がnullまたは空白か判定
     * @param str
     * @return
     */
    public boolean checkNullOrBlank(String str){
        if(str == null || str.equals("")){
            return true;
        }
        return false;
    }

    /**
     * 引数の時刻に引数の数値を足した場合、現在時刻より古い場合はtrue、新しい場合はfalseを返す。
     * @return
     */
    public boolean checkComparisonTime(Calendar firstLoginDate, int day, int hour, int minute){

        Calendar nowTime = Calendar.getInstance();

        firstLoginDate.add(Calendar.DATE, day);
        firstLoginDate.add(Calendar.HOUR_OF_DAY, hour);
        firstLoginDate.add(Calendar.MINUTE, minute);

        if(firstLoginDate.compareTo(nowTime) < 0){
            return true;
        }
        return false;
    }

    /**
     * 引数のファイルが書き込み可能かチェック
     * @param file
     * @return
     */
    public boolean checkBeforeWriteFile(File file){
        if (file.exists()){
            if (file.isFile() && file.canWrite()){
                return true;
            }
        }

        return false;
    }
}
