package com.bronet.blockchain.ui;

/**
 * Anti-Connection Point
 */
public class ContinuousUtil {
    private static long lastClickTime;
    private final static int SPACE_TIME =2000;

    public static void initLastClickTime() {
        lastClickTime = 0;
    }

    public synchronized static boolean isDoubleClick() {
        long currentTime = System.currentTimeMillis();
        boolean isClickDouble;
        if (currentTime - lastClickTime >
                SPACE_TIME) {
            isClickDouble = false;
        } else {
            isClickDouble = true;
        }
        lastClickTime = currentTime;
        return isClickDouble;
    }

}
