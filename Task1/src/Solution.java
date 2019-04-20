package com.zhigimont.by;

public class Solution {
    private char[] signs = {'+', '-', '*', '/'};
    private char[][] tempSigns = new char[64][4]; // массив для хранения всех возможных вариантов signs[];
    private boolean[] bool = new boolean[4];  // массив для проверки использования индекса
    private int[] tempIndex = new int[4];  // массив для перестановки индексов
    private int[][] tempInt = new int[24][4];
    private int countTempInt = 0;  //счетчик для записи в tempInt[][];
    private int firstElem = 0, secondElem = 1, thirdElem = 2, fourthElem = 3;

    public boolean canBeEqualTo24(int[] nums) {
        creatingMixSigns();
        creatingMixNumbers(nums, tempInt, tempIndex, firstElem, bool);
        return exampleSolutions(tempInt, tempSigns);
    }

    private void creatingMixSigns() {
        int indexArr = 0;
        for (int index1 = 0; index1 < signs.length; index1++) {
            for (int index2 = 0; index2 < signs.length; index2++) {
                for (int index3 = 0; index3 < signs.length; index3++) {
                    tempSigns[indexArr][0] = signs[index1];
                    tempSigns[indexArr][1] = signs[index2];
                    tempSigns[indexArr][2] = signs[index3];
                    indexArr++;
                }
            }
        }
    }

    /**
     * Рекурсия для нахождения комбинаций чисел. После создания временного массива комбинаций вызывает
     * метод mixSigns() который принмает данный массив;
     *
     * @param arr       целочиселнный массив чисел который был получен.
     * @param tempInt   - массив целочиселнных чисел для хранения всех сгенерированных вариантов переставлений цифр.
     * @param tempIndex - переменная для вызова индексов массива "arr".
     * @param first     - идекс с которого начинается перебор массива.
     * @param usedNumb  - булеан переменная для проверки использования ячейки массива(соответствует
     *                  длинне пришедшего массива).
     */
    private void creatingMixNumbers(int[] arr, int[][] tempInt, int[] tempIndex, int first, boolean[] usedNumb) {
        if (first == arr.length) {
            for (int index1 = 0; index1 < tempIndex.length; index1++) {
                tempInt[countTempInt][index1] = arr[tempIndex[index1]];
            }
            countTempInt++;
            return;
        }
        for (int index2 = 0; index2 < arr.length; index2++) {
            if (!usedNumb[index2]) {
                usedNumb[index2] = true;
                tempIndex[first] = index2;
                creatingMixNumbers(arr, tempInt, tempIndex, first + 1, usedNumb);
                usedNumb[index2] = false;
            }
        }
    }

    private boolean exampleSolutions(int[][] allArray, char[][] allSigns) {
        for (int[] arr : allArray) {
            for (char[] tempSigns : allSigns) {
                if (roundResult(firstSolution(arr, tempSigns)) || roundResult(secondSolution(arr, tempSigns))
                        || roundResult(thirtSolution(arr, tempSigns)) || roundResult(fourthSolution(arr, tempSigns))
                        || roundResult(fifthSolution(arr, tempSigns))) {
                    return true;
                }
            }
        }
        return false;
    }

    // a+b+c+d && (a+b)+c+d && ((a+b)+c)+d
    private double firstSolution(int[] arr, char[] tempSigns) {
        double result = 0;
        try {
            result = calc(arr[firstElem], arr[secondElem], tempSigns[firstElem]);
            result = calc(result, arr[thirdElem], tempSigns[secondElem]);
            result = calc(result, arr[fourthElem], tempSigns[thirdElem]);
            return result;
        }catch (ArithmeticException e){
            return 0;
        }
    }

    // (a+b)+(c+d) && a+b+(c+d)
    private double secondSolution(int[] arr, char[] tempSigns){
        double result = 0;
        double result1 = 0;
        try {
            result = calc(arr[firstElem], arr[secondElem], tempSigns[firstElem]);
            result1 = calc(arr[thirdElem], arr[fourthElem], tempSigns[thirdElem]);
            result = calc(result, result1, tempSigns[secondElem]);
            return result;
        }catch (ArithmeticException e){
            return 0;
        }
    }

    // (a+(b+c))+d && a+(b+c)+d
    private double thirtSolution(int[] arr, char[] tempSigns) {
        double result = 0;
        try {
            result = calc(arr[secondElem], arr[thirdElem], tempSigns[secondElem]);
            result = calc(arr[firstElem], result, tempSigns[firstElem]);
            result = calc(result, arr[fourthElem], tempSigns[thirdElem]);
            return result;
        }catch (ArithmeticException e){
            return 0;
        }
    }

    //a+((b+c)+d)
    private double fourthSolution(int[] arr, char[] tempSigns) {
        double result = 0;
        try {
            result = calc(arr[secondElem], arr[thirdElem], tempSigns[secondElem]);
            result = calc(result, arr[fourthElem], tempSigns[thirdElem]);
            result = calc(arr[firstElem], result, tempSigns[firstElem]);
            return result;
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    //a+(b+(c+d))
    private double fifthSolution(int[] arr, char[] tempSigns) {
        double result = 0;
        try {
            result = calc(arr[thirdElem], arr[fourthElem], tempSigns[thirdElem]);
            result = calc(arr[secondElem], result, tempSigns[secondElem]);
            result = calc(arr[firstElem], result, tempSigns[firstElem]);
            return result;
        } catch (ArithmeticException e) {
            return 0;
        }
    }

    private boolean roundResult(double result) {
        return Math.abs(result - 24) < 0.001;
    }

    private double calc(double a, double b, char operation) throws ArithmeticException {
        switch (operation) {
            case '+': {
                return a + b;
            }
            case '-': {
                return a - b;
            }
            case '*': {
                return a * b;
            }
            case '/': {
                if (b != 0) {
                    return a / b;
                } else
                    throw new ArithmeticException();
            }
            default: {
                throw new RuntimeException();
            }
        }
    }
}
