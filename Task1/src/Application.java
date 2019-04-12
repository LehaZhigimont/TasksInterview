package com.zhigimont.by;
/**
 * @author Zhigimont Alexey
 * @version 1.0
 * 07.04.2019
 */

public class Application {
    public static void main(String[] args) {
        Numbers numbers = new Numbers();
        Solutions solutions = new Solutions();
        solutions.canBeEqualTo24(numbers.getNumbers());
    }
}

