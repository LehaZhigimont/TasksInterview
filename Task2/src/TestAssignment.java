import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;


class TestAssignment {
    private static final Map<String, List<List<Integer>>> map = new HashMap<>();
    private static String stringRPN;

    public static void main(String[] args) {
        readLineApp();
        solution();
    }

    private static void readLineApp() {
        boolean readBool = true;
        List<String> readLines = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String line = " ";
            while (readBool) {
                if (line.equals(""))
                    readBool = false;
                line = bufferedReader.readLine();
                readLines.add(line);
            }
        } catch (IOException e) {
            exceptionMSG(e);
            return;
        }
        System.out.println("stdin:\n");
        readLines.forEach(System.out::println);
        for (String s : readLines) {
            if (!s.equals("")) {
                parsingToIntegerArray(s);
            } else {
                stringRPN = expressionRPN(readLines.get(readLines.size() - 1));
                return;
            }
        }
    }

    private static void solution() {
        rpnToStack(stringRPN);
    }

    private static List<List<Integer>> solutionMultiple(List<List<Integer>> matrixB, List<List<Integer>> matrixA) {
        List<List<Integer>> resultMultiple = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        int tempResult = 0, valueA, valueB;

        if (matrixA.get(0).size() != matrixB.size()) {
            exceptionMSG(new IllegalArgumentException());
        }
        for (int i = 0; i < matrixA.size(); i++) {
            for (int j = 0; j < matrixB.get(0).size(); j++) {
                for (int k = 0; k < matrixA.get(0).size(); k++) {
                    valueA = matrixA.get(i).get(k);
                    valueB = matrixB.get(k).get(j);
                    tempResult += valueA * valueB;
                    if (k == matrixA.get(i).size() - 1) {
                        temp.add(tempResult);
                        tempResult = 0;
                    }
                }
            }
            resultMultiple.add(temp);
            temp = new ArrayList<>();
        }
        return resultMultiple;
    }

    private static List<List<Integer>> solutionAddAndSub(List<List<Integer>> matrixB, List<List<Integer>> matrixA, String op) {
        List<List<Integer>> resultMultiple = new ArrayList<>();
        List<Integer> temp = new ArrayList<>();
        int tempResult = 0, valueA, valueB;
        if (matrixA.size() != matrixB.size()
                || matrixA.get(0).size() != matrixB.get(0).size()) {
            exceptionMSG(new IllegalArgumentException());
        }
        for (int i = 0; i < matrixA.size(); i++) {
            for (int j = 0; j < matrixB.get(0).size(); j++) {
                valueA = matrixA.get(i).get(j);
                valueB = matrixB.get(i).get(j);
                if (op.equals("+"))
                    tempResult = valueA + valueB;
                else if (op.equals("-"))
                    tempResult = valueA - valueB;

                temp.add(tempResult);
                tempResult = 0;
            }
            resultMultiple.add(temp);
            temp = new ArrayList<>();
        }
        return resultMultiple;
    }

    private static void parsingToIntegerArray(String matrix) {
        final String array = matrix.substring(3, matrix.length() - 1);
        List<List<Integer>> lines = new ArrayList<>();
        final String[] split = array.split("; ");
        for (String s :
                split) {
            try {
                final List<Integer> numbers = stream(s.split(" ")).map(Integer::parseInt).collect(toList());
                lines.add(numbers);
            } catch (Exception e) {
                exceptionMSG(e);
            }
        }
        map.put(matrix.charAt(0) + "", lines);
    }

    private static void exceptionMSG(Exception exceptionName) {
        String msgExc = "IllegalArgumentException", msg;
        if (exceptionName.getClass().equals(NumberFormatException.class))
            msg = "read matrix";
        else
            msg = "perform multiplication";

        System.out.println("\nstderr:" +
                "\n\nException caught:" + msgExc + ". Can't. " + msg);
        System.exit(0);
    }

    private static String expressionRPN(String str) {
        int priority;
        StringBuilder currentExpr = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            priority = getPriority(str.charAt(i));

            if (priority == 0) currentExpr.append(str.charAt(i));
            if (priority > 0) {
                while (!stack.empty()) {
                    if (getPriority(stack.peek()) >= priority)
                        currentExpr.append(stack.pop());
                    else
                        break;
                }
                stack.push(str.charAt(i));
            }
        }
        while (!stack.empty())
            currentExpr.append(stack.pop());
        return currentExpr.toString();
    }


    private static void rpnToStack(String rpn) {
        Stack<List<List<Integer>>> matrixStack = new Stack<>();
        String strInd;

        for (int i = 0; i < rpn.length(); i++) {
            if (getPriority(rpn.charAt(i)) == 0) {
                while (rpn.charAt(i) != '+' && rpn.charAt(i) != '-' && rpn.charAt(i) != '*') {
                    strInd = "" + rpn.charAt(i++);
                    matrixStack.push(map.get(strInd));
                    if (i == rpn.length())
                        break;
                }
            }
            if (getPriority(rpn.charAt(i)) > 0) {
                if (rpn.charAt(i) == '*') {
                    matrixStack.push(solutionMultiple(matrixStack.pop(), matrixStack.pop()));
                }
                if (rpn.charAt(i) == '+' || rpn.charAt(i) == '-') {
                    matrixStack.push(solutionAddAndSub(matrixStack.pop(), matrixStack.pop(), "" + rpn.charAt(i)));
                }
            }
        }
        printStack(matrixStack.pop());
    }

    private static int getPriority(char operator) {
        if (operator == '*') return 2;
        else if (operator == '+' || operator == '-') return 1;
        else return 0;
    }

    private static void printStack(List<List<Integer>> stack) {
        System.out.print("""

                stdout:

                [""");
        for (int i = 0; i < stack.size(); i++) {
            for (int j = 0; j < stack.get(i).size(); j++) {
                System.out.print(stack.get(i).get(j));
                if (j != stack.get(i).size() - 1)
                    System.out.print(" ");
            }
            if (i != stack.size() - 1) {
                System.out.print("; ");
            }
        }
        System.out.println("]");
    }
}