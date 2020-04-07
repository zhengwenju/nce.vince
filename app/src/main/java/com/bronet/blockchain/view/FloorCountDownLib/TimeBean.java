package com.bronet.blockchain.view.FloorCountDownLib;

import android.os.SystemClock;

/**
 * Created by air on 2019/3/13.
 */
public class TimeBean {
    private long elapsedRealtime;

    public TimeBean(long remainTime) {
        elapsedRealtime = remainTime + SystemClock.elapsedRealtime()/1000;
    }


    public long getRainTime() {
        return elapsedRealtime - SystemClock.elapsedRealtime()/1000;
    }
}
