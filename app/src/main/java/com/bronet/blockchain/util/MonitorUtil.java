package com.bronet.blockchain.util;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;

public class MonitorUtil {

    public static boolean checkIsRunningInEmulator() {
        int suspectCount = 0;
        // 读基带信息
        String baseBandVersion = getProperty("gsm.version.baseband");
        if (baseBandVersion == null | "".equals(baseBandVersion)) {
            ++suspectCount;
        }

        // 读渠道信息，针对一些基于vBox的模拟器
        String buildFlavor = getProperty("ro.build.flavor");
        if (buildFlavor == null | "".equals(buildFlavor) | (buildFlavor != null && buildFlavor.contains("vbox"))) {
            ++suspectCount;
        }

        // 读处理器信息，这里经常会被处理
        String productBoard = getProperty("ro.product.board");
        if (productBoard == null | "".equals(productBoard)) {
            ++suspectCount;
        }

        // 读处理器平台，这里不常会处理
        String boardPlatform = getProperty("ro.board.platform");
        if (boardPlatform == null | "".equals(boardPlatform)) {
            ++suspectCount;
        }

        // 高通的cpu两者信息一般是一致的（发现不准，弃之）
        // if (productBoard != null && boardPlatform != null && !productBoard.equals(boardPlatform)) {
        // ++suspectCount;
        //}

        // 一些模拟器读取不到进程租信息
        String filter = exec("cat /proc/self/cgroup");
        if (filter == null || filter.length() == 0) {
            ++suspectCount;
        }
        return suspectCount >=1;
    }

    private static String getProperty(String propName) {
        String value = null;
        Object roSecureObj;
        try {
            roSecureObj = Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke(null, propName);
            if (roSecureObj != null) {
                value = (String) roSecureObj;
            }
        } catch (Exception e) {
            value = null;
        } finally {
            return value;
        }
    }

    private static String exec(String command) {
        BufferedOutputStream bufferedOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        Process process = null;
        try {
            process = Runtime.getRuntime().exec("sh");
            bufferedOutputStream = new BufferedOutputStream(process.getOutputStream());

            bufferedInputStream = new BufferedInputStream(process.getInputStream());
            bufferedOutputStream.write(command.getBytes());
            bufferedOutputStream.write('\n');
            bufferedOutputStream.flush();
            bufferedOutputStream.close();

            process.waitFor();

            String outputStr = getStrFromBufferInputSteam(bufferedInputStream);
            return outputStr;
        } catch (Exception e) {
            return null;
        } finally {
            if (bufferedOutputStream != null) {
                try {
                    bufferedOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (process != null) {
                process.destroy();
            }
        }
    }

    private static String getStrFromBufferInputSteam(BufferedInputStream bufferedInputStream) {
        if (null == bufferedInputStream) {
            return "";
        }
        int BUFFER_SIZE = 512;
        byte[] buffer = new byte[BUFFER_SIZE];
        StringBuilder result = new StringBuilder();
        try {
            while (true) {
                int read = bufferedInputStream.read(buffer);
                if (read > 0) {
                    result.append(new String(buffer, 0, read));
                }
                if (read < BUFFER_SIZE) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }


    /**
     * @return true 为模拟器  //夜神模拟器为false
     */
    public static boolean isEmulator(Context context) {
        if (context == null) {
            return false;
        }
        String url = "tel:" + "123456";
        Intent intent = new Intent();
        intent.setData(Uri.parse(url));
        intent.setAction(Intent.ACTION_DIAL);
        // 是否可以处理跳转到拨号的 Intent
        boolean canResolverIntent = intent.resolveActivity(context.getPackageManager()) != null;
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.SERIAL.equalsIgnoreCase("unknown")
                || Build.SERIAL.equalsIgnoreCase("android")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT)
                || ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkOperatorName().toLowerCase().equals("android")
                || !canResolverIntent;
    }

    /**
     * 根据部分特征参数设备信息来判断是否为模拟器
     *
     * @return true 为模拟器  //夜神模拟器为false
     */
    public static boolean isFeatures() {
        return Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.toLowerCase().contains("vbox")
                || Build.FINGERPRINT.toLowerCase().contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT);
    }


    public static boolean hasLightSensor(Context context) {
        SensorManager sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT); //光
        if (sensor == null)
            return true;
        else
            return false;
    }
}
