package com.fly.tour.test.presenter;

/**
 * Description: <Test><br>
 * Author:      mxdl<br>
 * Date:        2020/3/9<br>
 * Version:     V1.0.0<br>
 * Update:     <br>
 */
public class Test {
    public static void main(String[] args) {
        Test test = new Test();
        try {
            System.out.println(test.test());
            System.out.println("ok");
        }catch (Exception e){
            System.out.println("error");
        }
    }

    public String test() {
        System.out.println("00000");
        if (true)
            throw new RuntimeException("small bug");

        System.out.println("22222");
        return "ok";
    }
}
