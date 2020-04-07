package com.bronet.blockchain;

import com.bronet.blockchain.util.L;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    private Timer mTimer = new Timer();
    private TimerTask mTimerTask;

    private int j;

    @Test
    public void addition_isCorrect() {
//        System.out.println("1Timer======>>>");

//        Runnable runnable = new Runnable() {
//            public void run() {
//                System.out.println("aaaaaaaaaaaaaaa============>>"+new Date());
//            }
//
//        };
//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.scheduleAtFixedRate(runnable, 0, 2, TimeUnit.SECONDS);


        isData();

    }

    private static boolean isData(){
        String num="-1342199603";
        if(isNumber1(num)){
            System.out.println("true==========>>>"+true);
        }else {
            System.out.println("false==========>>>"+false);
        }
        return true;
    }

    public static boolean isNumber1(String str) {// 判断整型
        return str.matches("^\\d+$$")||str.matches("\\d+\\.\\d+$");
    }


    private void sa(int i) {
        System.out.println("2Timer======>>>" + i);
    }
}