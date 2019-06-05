package com.xing.jmm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author : xingshukui
 * @email : xingshukui@890media.com
 * @date : 2019/6/5 7:20 PM
 * @desc : 内存可见性问题
 */
@Slf4j
public class JmmVolat {


    public static void main(String[] args) {
//        Demo demo = new Demo();
//        demo.testError();

        DemoVolat demoVolat = new DemoVolat();
        demoVolat.testError2();



    }


    /**
     *
     * demo.setResult(200); 对其他线程并不可见，所以程序一直结束不了
     *
     */
    static class Demo {
        int result;

        public int getResult() {
            return result;
        }

        public synchronized void setResult(int result) {
            this.result = result;
        }

        public void testError() {
            Demo demo = new Demo();
            for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    int temp = 0;
                    while (demo.getResult() < 100) {
                        temp ++;
                    }
                    log.info("temp : " + temp);
                }).start();
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("debug....");
            demo.setResult(200);
        }
    }


    /**
     *
     *
     * 执行结果：
     *
     *
     * 19:32:06.358 [main] INFO com.xing.jmm.JmmVolat - debug....
     * 19:32:06.360 [Thread-8] INFO com.xing.jmm.JmmVolat - temp2 : 632381035
     * 19:32:06.361 [Thread-5] INFO com.xing.jmm.JmmVolat - temp2 : 672755849
     * 19:32:06.360 [Thread-0] INFO com.xing.jmm.JmmVolat - temp2 : 308836309
     * 19:32:06.361 [Thread-2] INFO com.xing.jmm.JmmVolat - temp2 : 312171456
     * 19:32:06.361 [Thread-6] INFO com.xing.jmm.JmmVolat - temp2 : 656728635
     * 19:32:06.361 [Thread-4] INFO com.xing.jmm.JmmVolat - temp2 : 340795787
     * 19:32:06.361 [Thread-1] INFO com.xing.jmm.JmmVolat - temp2 : 309359525
     * 19:32:06.361 [Thread-3] INFO com.xing.jmm.JmmVolat - temp2 : 314465688
     * 19:32:06.361 [Thread-9] INFO com.xing.jmm.JmmVolat - temp2 : 641324700
     * 19:32:06.361 [Thread-7] INFO com.xing.jmm.JmmVolat - temp2 : 634961126
     *
     *
     */
    static class DemoVolat {
        volatile int result;

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public void testError2() {
            DemoVolat demoVolat = new DemoVolat();
            for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    int temp2 = 0;
                    while (demoVolat.getResult() < 100) {
                        temp2 ++;
                    }
                    log.info("temp2 : " + temp2);
                }).start();
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("debug....");
            demoVolat.setResult(200);
        }
    }



}
