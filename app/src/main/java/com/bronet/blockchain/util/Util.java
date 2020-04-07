package com.bronet.blockchain.util;

import android.Manifest;
import android.app.Service;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.bronet.blockchain.adapter.Feedback;
import com.bronet.blockchain.data.InvestList;
import com.bronet.blockchain.data.MutualModelList;
import com.bronet.blockchain.data.NodeLog;
import com.bronet.blockchain.data.PrizeRecordModel;
import com.bronet.blockchain.data.RechargeRecordBean;
import com.bronet.blockchain.data.TradeBuyList;
import com.bronet.blockchain.data.TransferMineLog;
import com.bronet.blockchain.data.TransferNodeLog;
import com.bronet.blockchain.data.TransferRewardLog;
import com.bronet.blockchain.data.UserMineLog;
import com.bronet.blockchain.data.WithdrawLog;
import com.bronet.blockchain.data.WithdrawLog2;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Dajavu on 25/10/2017.
 */

public class Util {

    public static String imei;
    public static String phone;
    public static int Dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static String formatFloat(float value) {
        return String.format(Locale.getDefault(), "%.3f", value);
    }

    public static String base64() {

        String base64;
        //base64编码
        String strBase64 = Base64.encodeToString("".getBytes(), Base64.DEFAULT);
        //base64解码
        String str2 = new String(Base64.decode(strBase64.getBytes(), Base64.DEFAULT));

        return "";
    }

//    public static boolean isNumber(String str) {
//        String reg = "^[0-9]+(.[0-9]+)?$";
//        return str.matches(reg);
//    }

    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
            L.test(ex.getMessage());
        }
        return null;
    }

    private static ConnectivityManager mConnectivityManager = null;

    public static String getIPAddress(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);//获取系统的连接服务
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if ((info.getType() == ConnectivityManager.TYPE_MOBILE)) {
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }
            } else if ((info.getType() == ConnectivityManager.TYPE_WIFI)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else { //当前无网络连接,请在设置中打开网络
            return null;
        }
        return null;
    }

    public static String getNetType(Context context) {
        mConnectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        String nettype = "";
        NetworkInfo info = mConnectivityManager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                nettype = "mobile";
            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                nettype = "wifi";
            }
        }
        return nettype;
    }

    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    public static  String getPhoneIMEI() {
        if(imei!=null) {
            return imei;
        }else {
            return "";
        }
    }
    public static String getAndroidId(Context context){
        String m_szAndroidID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    public static InputFilter inputFilter= new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
//                Toast.makeText(re,"不支持输入表情",Toast.LENGTH_LONG).show();
                return "";
            }
            return null;
        }
    };
    public static InputFilter filterSpeChat = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
            String speChat = "[`~!@#_$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）— +|{}【】‘；：”“’。，、？]";
            Pattern pattern = Pattern.compile(speChat);
            Matcher matcher = pattern.matcher(charSequence.toString());
            if (matcher.find()) return "";
            else return null;
        }
    };


    private static String[] platforms = {
            "http://pv.sohu.com/cityjson",
            "http://pv.sohu.com/cityjson?ie=utf-8",
            "http://ip.chinaz.com/getip.aspx"
    };
    public static String[] getOutNetIP(Context context, int index) {
        if (index < platforms.length) {
            BufferedReader buff = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(platforms[index]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);//读取超时
                urlConnection.setConnectTimeout(5000);//连接超时
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {//找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                    InputStream is = urlConnection.getInputStream();
                    buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = buff.readLine()) != null) {
                        builder.append(line);
                    }

                    buff.close();//内部会关闭 InputStream
                    urlConnection.disconnect();

                    Log.e("xiaoman", builder.toString());
                    if (index == 0 || index == 1) {
                        //截取字符串
                        int satrtIndex = builder.indexOf("{");//包含[
                        int endIndex = builder.indexOf("}");//包含]
                        String json = builder.substring(satrtIndex, endIndex + 1);//包含[satrtIndex,endIndex)
                        JSONObject jo = new JSONObject(json);
                        String ip = jo.getString("cip");
                        String ipAddress = jo.getString("cname");

                        boolean isGet =TextUtils.isEmpty(ipAddress)||TextUtils.isEmpty(ip)||ipAddress.contains("CHINA")||ipAddress.contains("国内未能识别的地区");
                        if(isGet){
                            return getTaobaoOutNetIP(ip);
                        }else {
                            L.test("sohu get ip============>>>>ip:"+ip);
                            L.test("sohu get ip============>>>>ipAddress:"+ipAddress);
                            return new String[]{ip, ipAddress};
                        }
                    } else if (index == 2) {
                        JSONObject jo = new JSONObject(builder.toString());
                        String ipAddress = jo.getString("cname");
                        String ip = jo.getString("ip");
                        boolean isGet =TextUtils.isEmpty(ipAddress)||TextUtils.isEmpty(ip)||ipAddress.contains("CHINA")||ipAddress.contains("国内未能识别的地区");
                        if(isGet){
                            return getTaobaoOutNetIP(ip);
                        }else {
                            return new String[]{ip, ipAddress};
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            return getInNetIp(context);
        }
        return null;
    }


    public static String[] getTaobaoOutNetIP(String tag) {
            BufferedReader buff = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://ip.taobao.com/service/getIpInfo2.php?ip=myip");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setReadTimeout(5000);//读取超时
                urlConnection.setConnectTimeout(5000);//连接超时
                urlConnection.setDoInput(true);
                urlConnection.setUseCaches(false);

                int responseCode = urlConnection.getResponseCode();
                L.test("taobao get ip============>>>>responseCode:"+responseCode);
                if (responseCode == HttpURLConnection.HTTP_OK) {//找到服务器的情况下,可能还会找到别的网站返回html格式的数据
                    InputStream is = urlConnection.getInputStream();
                    buff = new BufferedReader(new InputStreamReader(is, "UTF-8"));//注意编码，会出现乱码
                    StringBuilder builder = new StringBuilder();
                    String line = null;
                    while ((line = buff.readLine()) != null) {
                        builder.append(line);
                    }

                    buff.close();//内部会关闭 InputStream
                    urlConnection.disconnect();

                    L.test("taobao get ip============>>>>data:"+builder.toString());
                    JSONObject jo = new JSONObject(builder.toString());
                    String country = jo.getString("country");
                    String area = jo.getString("area");
                    String region = jo.getString("region");
                    String city = jo.getString("city");
                    String ip = jo.getString("ip");
                    return new String[]{"taoip:"+ip,"taobao ad:"+country+area+region+city};
                }
            } catch (Exception e) {
                L.test("taobao get ip============>>>>e:"+e.getLocalizedMessage());
                return new String[]{tag,"null"};
//                e.printStackTrace();
            }
            return  null;
    }

    public static String[] getInNetIp(Context context) {
        //获取wifi服务
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }

        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);

        return new String[]{ip};
    }

    //这段是转换成点分式IP的码
    private static String intToIp(int ip) {
        return (ip & 0xFF) + "." + ((ip >> 8) & 0xFF) + "." + ((ip >> 16) &     0xFF) + "." + (ip >> 24 & 0xFF);
    }

    public static  String getAppVersion(Context context){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packInfo.versionName;
    }

    public static String getOsModel(){
        StringBuffer deviceSb = new StringBuffer();
        deviceSb.append(Build.MANUFACTURER+" ");
        deviceSb.append(Build.MODEL);
        deviceSb.append(" OS Version: ");
        deviceSb.append(Build.VERSION.RELEASE);
        deviceSb.append("_");
        deviceSb.append(Build.VERSION.SDK_INT);
        L.test("getOsModel==========>>>"+deviceSb.toString());
        return deviceSb.toString();
    }

//    public static boolean isLetterDigit(String str) {
//        boolean isDigit = false;//定义一个boolean值，用来表示是否包含数字
//        boolean isLetter = false;//定义一个boolean值，用来表示是否包含字母
//        for (int i = 0; i < str.length(); i++) {
//            if (Character.isDigit(str.charAt(i))) {   //用char包装类中的判断数字的方法判断每一个字符
//                isDigit = true;
//            } else if (Character.isLetter(str.charAt(i))) {  //用char包装类中的判断字母的方法判断每一个字符
//                isLetter = true;
//            }
//        }
//        String regex = "^[a-zA-Z0-9]{8,16}$";
//        boolean isRight = isDigit && isLetter && str.matches(regex);
//        return isRight;
//    }

    public static boolean isLetterDigit(String pass){
        String regex = "^[a-zA-Z0-9]{8,16}$";
        if (pass.matches(".*[a-z]{1,}.*") && pass.matches(".*[A-Z]{1,}.*") && pass.matches(".*\\d{1,}.*") && pass.matches(regex)) {
            return true;
        }
        return false;
    }

    //保留两位
    public static String mDecimalFormat(double d){
        DecimalFormat df = new DecimalFormat("0.00");
        String str = df.format(d);
        return str;
    }
    //保留两位
    public static String mDecimal8Format(BigDecimal d){
        DecimalFormat df = new DecimalFormat("0.00000000");
        String str = df.format(d);
        return str;
    }
    public static List<TradeBuyList.ResultBean.ItemsBean>  ridRepeat(List<TradeBuyList.ResultBean.ItemsBean> list) {
        List<TradeBuyList.ResultBean.ItemsBean> listNew = new ArrayList<>();
        for (TradeBuyList.ResultBean.ItemsBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<PrizeRecordModel.ResultBean>  ridRepeat2(List<PrizeRecordModel.ResultBean> list) {
        List<PrizeRecordModel.ResultBean> listNew = new ArrayList<>();
        for (PrizeRecordModel.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<WithdrawLog.Result>  ridRepeat3(List<WithdrawLog.Result> list) {
        List<WithdrawLog.Result> listNew = new ArrayList<>();
        for (WithdrawLog.Result str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<WithdrawLog2.ResultBean>  ridRepeat9(List<WithdrawLog2.ResultBean> list) {
        List<WithdrawLog2.ResultBean> listNew = new ArrayList<>();
        for (WithdrawLog2.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<RechargeRecordBean.ResultBean>  ridRepeat4(List<RechargeRecordBean.ResultBean> list) {
        List<RechargeRecordBean.ResultBean> listNew = new ArrayList<>();
        for (RechargeRecordBean.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<InvestList.Result>  ridRepeat5(List<InvestList.Result> list) {
        List<InvestList.Result> listNew = new ArrayList<>();
        for (InvestList.Result str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }

    public static List<MutualModelList.ResultBean>  ridRepeat6(List<MutualModelList.ResultBean> list) {
        List<MutualModelList.ResultBean> listNew = new ArrayList<>();
        for (MutualModelList.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }

    public static List<Feedback.Data>  ridRepeat20(List<Feedback.Data> list) {
        List<Feedback.Data> listNew = new ArrayList<>();
        for (Feedback.Data str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<NodeLog.ResultBean>  nodelog(List<NodeLog.ResultBean> list) {
        List<NodeLog.ResultBean> listNew = new ArrayList<>();
        for (NodeLog.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<TransferNodeLog.ResultBean>  TransferNodeLog(List<TransferNodeLog.ResultBean> list) {
        List<TransferNodeLog.ResultBean> listNew = new ArrayList<>();
        for (TransferNodeLog.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<TransferMineLog.ResultBean>  TransferMineLog(List<TransferMineLog.ResultBean> list) {
        List<TransferMineLog.ResultBean> listNew = new ArrayList<>();
        for (TransferMineLog.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }

    public static List<TransferRewardLog.ResultBean>  TransferRewardLog(List<TransferRewardLog.ResultBean> list) {
        List<TransferRewardLog.ResultBean> listNew = new ArrayList<>();
        for (TransferRewardLog.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }
    public static List<UserMineLog.ResultBean>  minelog(List<UserMineLog.ResultBean> list) {
        List<UserMineLog.ResultBean> listNew = new ArrayList<>();
        for (UserMineLog.ResultBean str : list) {
            if (!listNew.contains(str)) {
                listNew.add(str);
            }
        }
        return listNew;
    }

//    public static void ridRepeat1(List<String> list) {
//        System.out.println("list = [" + list + "]");
//        List<String> listNew = new ArrayList<String>();
//        for (String str : list) {
//            if (!listNew.contains(str)) {
//                listNew.add(str);
//            }
//        }
//        System.out.println("listNew = [" + listNew + "]");
//    }

    /**
     * 1是模拟器,0是真机
     * @param context
     * @return
     */
    public static String isMonitor(Context context) {
        boolean isM1 = MonitorUtil.hasLightSensor(context);
        boolean isM2 = MonitorUtil.checkIsRunningInEmulator();
        boolean isM3 = MonitorUtil.isEmulator(context);
        if(isM1){
//            if(!isM2||!isM3){
//                return "0";
//            }
            return "1";
        }
        return "0";
    }

    public static boolean isData(String str) {
        return str.matches("^\\d+$$")||str.matches("\\d+\\.\\d+$");
    }




}
