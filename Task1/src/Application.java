package com.zhigimont.by;
/**
 * @author Zhigimont Alexey
 * @version 1.0
 * 07.04.2019
 */

public class Application {
    private static int[]curentArray = new int[]{3,3,3,2};

    public static void main(String[] args) {
        Solutions solutions = new Solutions();
        solutions.canBeEqualTo24(curentArray);
    }
}