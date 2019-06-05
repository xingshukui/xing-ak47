package com.xing.jmm;

/**
 * @author : xingshukui
 * @email : xingshukui@890media.com
 * @date : 2019/6/5 7:10 PM
 * @desc :
 *
 * 重排序问题：这个很难模拟，只能解释一下
 *
 */
public class JmmSequence {


    /**
     * 执行顺序 ： 1和2 可以重排序，没有数据依赖性， 3和4也，没有数据依赖性，也可以重排序,所以执行结果不确定
     */
    class Demo {

        private int anInt = 0;
        private boolean b = false;



        void write() {
            anInt = 1;// 1
            b = true;// 2
        }

        void read() {
            if (b) { // 3
                anInt = 2;// 4
            }
        }
    }


}
