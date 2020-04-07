package com.bronet.blockchain.fix;


import com.bronet.blockchain.util.L;

/**
 * 常量
 *
 * @packageName: cn.white.ymc.wanandroidmaster.util
 * @fileName: Constant
 * @date: 2018/7/19  14:55
 * @author: ymc
 * @QQ:745612618
 */

public class ConstantUtil {

    public static int RESULTTYPE=0;
    public static boolean TYPE = false;
    /**
     * 网络 广播
     */
    public static String NETBROADCAST = "CONNECTIVITY_CHANGE";

    /**
     * 访问网络 基础 url
     */
    public static final String BASE_URL = L.isDebug?"http://47.244.61.162/":"http://api.ncecoin.io/";//http://47.244.61.162  http://nce2019.azurewebsites.net/  http://api.ncecoin.io/
    public static final String BASE_URL2 =L.isDebug?"http://47.244.61.162:8040":"http://crawler.ncecoin.io/";
    public static final String GAME_URL ="http://192.168.31.170:8060/";
//    public static final String BASE_URL2 =L.isDebug?"http://ncecrawler.azurewebsites.net":"http://crawler.ncecoin.io/";
    /**
     * 首次启动
     */
    public static final String First="First";
    public static String TOKEN;
    public static String ID;
    public static String PWD;
    public static String Avatar;
    public static String USERNAME;
    /**
     * 项目名称
     */
    public static String ProjectName;

}
