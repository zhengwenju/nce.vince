package com.bronet.blockchain.util;

import java.util.Calendar;

/**
 * Created by 18514 on 2019/1/25.
 */

public class TimeUtil {
    /**
     * 获取年
     * @return
             */
    public static int getYear(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.YEAR);
    } /**
     * 获取月
     * @return
     */
    public static int getMonth(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.MONTH)+1;
    } /**
     * 获取日
     * @return
     */
    public static int getDay(){
        Calendar cd = Calendar.getInstance();
        return  cd.get(Calendar.DATE);
    }
}
