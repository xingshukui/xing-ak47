package com.xing.jmm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author : xingshukui
 * @email : xingshukui@163.com
 * @date : 2019/6/5 6:31 PM
 * @desc :
 *
 * 并发问题
 *
 *
 */
@Slf4j
public class JmmSync {


    /**
     * 未同步
     */
    static class Demo {
        private int aInt = 0;
        private boolean b = false;


        //synchronized加不加结果是一样的
        synchronized void write() {
            log.info("write start ...");
            try {
                TimeUnit.SECONDS.sleep(3);//等待3s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            aInt = 1;
            b =true;
            log.info("write end ...");
        }

        void read() {
            log.info("read start ...");

            try {
                TimeUnit.SECONDS.sleep(2);//读 等待2s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (b) {
                int bInt = aInt * aInt;
                log.info("bInt : " + bInt);
            }
            log.info("read end ...");
        }

    }

    /**
     * result:
     * 18:46:28.069 [Thread-0] INFO com.xing.jmm.JmmSequence - read start ...
     * 18:46:28.073 [Thread-1] INFO com.xing.jmm.JmmSequence - write start ...
     * 18:46:30.073 [Thread-0] INFO com.xing.jmm.JmmSequence - read end ...
     * 18:46:31.077 [Thread-1] INFO com.xing.jmm.JmmSequence - write end ...
     *
     * 并没有执行：log.info("bInt : " + bInt);
     */
    private static void test() {
        Demo demo = new Demo();
        new Thread(demo::write).start();
        new Thread(demo::read).start();
    }



    /**
     * 同步
     */
    static class DemoSync {
        private int aInt = 0;
        private boolean b = false;


        synchronized void write() {
            log.info("write start ....");

            try {
                TimeUnit.SECONDS.sleep(3);//等待3s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            aInt = 1;
            b =true;
            log.info("write end ...");
        }

        synchronized void read() {
            log.info("read start ...");

            try {
                TimeUnit.SECONDS.sleep(2);//等待2s
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (b) {
                int bInt = aInt * aInt;
                log.info("bInt : " + bInt);
            }
            log.info("read end ...");
        }

    }


    public static void main(String[] args) {
//        JmmSync.test();
        JmmSync.testSync();
    }

    /**
     * result:
     *
     * 19:08:11.292 [Thread-0] INFO com.xing.jmm.JmmSequence - write start ....
     * 19:08:14.298 [Thread-0] INFO com.xing.jmm.JmmSequence - write end ...
     * 19:08:14.299 [Thread-1] INFO com.xing.jmm.JmmSequence - read start ...
     * 19:08:16.302 [Thread-1] INFO com.xing.jmm.JmmSequence - bInt : 1
     * 19:08:16.302 [Thread-1] INFO com.xing.jmm.JmmSequence - read end ...
     *
     *
     */
    private static void testSync() {
        DemoSync demo = new DemoSync();
        new Thread(demo::write).start();
        new Thread(demo::read).start();
    }



}
