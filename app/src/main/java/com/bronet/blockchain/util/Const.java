package com.bronet.blockchain.util;

import java.util.HashMap;

public class Const {
    public final static String USDT ="usdt";
    public final static String NCE ="nce";
    public final static String PID ="pid";
    public final static String UID ="uid";
    public final static String USERNAME ="username";
    public final static String ITEMS ="items";
    public final static String ETH ="eth";
    public final static String FROMTAG ="fromtag";
    public final static String BUYSELLRECORD ="buySellRecord";
    public final static String BUYSELLRECORDBEAN ="buySellRecordBean";
    public final static String AUXILIARIES_OR_KEY ="auxiliaries_or_key";
    public final static String LOGINTYPE="loginType";
    public static int REFRESHFLAG =0;
    public static String ZJC ="";
    public static String ZJC2 ="";
    public static String QTY_ADDRESS ="";
    public static String PWD ="";
    public static String YQM ="";
    public static String TB_ADDRESS ="";  //0x41ca01dfbc480b4ddfcf044585c297b96bd74586
    public static String GOOGLE_ADMOB_ID="ca-app-pub-8791296902505750~5781771493";



    static {
//         ZJC="angle please lemon smile execute comic clog build favorite antenna admit snack";
//        ZJC="craft syrup giraffe gravity cash decrease shuffle advice proud ocean slow arctic";
//        ZJC="close erase frown judge peace unaware fame frown ghost hybrid leg chef";  //82 NCE
//        ZJC="dream knife space acid romance answer toss logic velvet pair leader music";
        //ZJC="kingdom knife mechanic never south donkey balcony upset slight siren seven camp";
        //ZJC="upon exotic crucial inhale rely labor antique used analyst antenna taxi marble";
        ZJC="kingdom knife mechanic never south donkey balcony upset slight siren seven camp";  //zwj101
        //ZJC = "faint adjust repair moral maximum noise digital age camp aisle dial real"; //zwj100
//        ZJC="faint adjust repair moral maximum noise digital age camp aisle dial real";
        if(!L.isMyPwd) {
                ZJC="phrase cradle universe damp evolve bacon bronze carbon large elite surge humble";  //zwj105
//            ZJC="faint adjust repair moral maximum noise digital age camp aisle dial real";  //zwj106
        }



         QTY_ADDRESS ="";
         PWD="11111111aA";
         YQM="msbf8n"; //etg5ir
         TB_ADDRESS="";
    }

    public final static String LANGUAGE_KEY ="language_key";
    public final static String SP_ID ="login_id";

    public final static String SP_TOKEN ="ddx_token";
    public static String TOKEN ="";
    public static int IDENTI_NCE =0;
    public static String[] outIpAddressArray=null;
    public static int TransactionFromData=0; //默认是买入0  卖出是1


    public static int DelagateFromData=0; //全部委托是买入0  我的委托是1
    public static String SORT_ORDERNAME=null;
    public static int SORT_ORDERBY_NUM=0;
    public static boolean IS_FROZEN=false; //false,不冻结, true冻结
    public static String FROZEN_CONTENT=""; //冻结提醒内容
    public static int EVENT_CLICK=0;


    public static  HashMap<Integer,String> tipMap = new HashMap<>();
    public static String TRIGER_GAME_STR="枚NCE";

    public static boolean isShowNum = false;
//    public static int mutualHelpCount =0;
//    public static int feedBackCount =0;
    public final static String VERSION ="v4";

    public static int IsClosePeriod=0; //1,是关闭期,0是正常合约

}
