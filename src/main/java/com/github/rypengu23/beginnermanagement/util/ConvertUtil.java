package com.github.rypengu23.beginnermanagement.util;

import java.util.Calendar;

public class ConvertUtil {

    /**
     * yyyy/MM/dd hh:mmをカレンダー型に変換
     *
     * @param date
     * @return
     */
    public Calendar convertCalendar(String date) {

        if(date.length() != 16){
            return null;
        }

        Calendar calendar = Calendar.getInstance();

        try {
            int year = Integer.parseInt(date.substring(0, 4));
            int month = Integer.parseInt(date.substring(5, 7)) - 1;
            int day = Integer.parseInt(date.substring(8, 10));
            int hour = Integer.parseInt(date.substring(11, 13));
            int minute = Integer.parseInt(date.substring(14));

            calendar.set(year, month, day, hour, minute, 0);

        } catch (NumberFormatException e) {
            return null;
        }

        return calendar;
    }

    public String placeholderUtil(String beforeReplaceWord, String afterReplaceWord, String message){

        if(beforeReplaceWord != null && afterReplaceWord != null && message != null) {
            return message.replace(beforeReplaceWord, afterReplaceWord);
        }else{
            return null;
        }
    }

    public String placeholderUtil(String beforeReplaceWord1, String afterReplaceWord1, String beforeReplaceWord2, String afterReplaceWord2, String message){

        message = placeholderUtil(beforeReplaceWord1, afterReplaceWord1, message);
        return placeholderUtil(beforeReplaceWord2, afterReplaceWord2, message);
    }

    public String placeholderUtil(String beforeReplaceWord1, String afterReplaceWord1, String beforeReplaceWord2, String afterReplaceWord2, String beforeReplaceWord3, String afterReplaceWord3, String message){

        message = placeholderUtil(beforeReplaceWord1, afterReplaceWord1, message);
        message = placeholderUtil(beforeReplaceWord2, afterReplaceWord2, message);
        return placeholderUtil(beforeReplaceWord3, afterReplaceWord3, message);
    }
}
