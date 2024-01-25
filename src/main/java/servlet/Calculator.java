package servlet;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Stack;

public class Calculator {
    public static ArrayList<String> history = new ArrayList<>();

    public static final String STRING = "\"-\" means substract;\n" +
            "\"+\" means addition;";

    public static final String pattern = "(?<=\\W)|(?=\\W)";

    public static boolean isShown = false;

    public static HashMap<String, String> map = new HashMap<>();

    public static int compareOperands(String x, String y) {
        if (x.matches("[/*]") && y.matches("[-+]")) return 1;
        if (x.matches("[-+]") && y.matches("[/*]")) return -1;
        return 0;
    }

    public static String checkExpression(String s) {
        String fail = "Invalid expression";
        if (s.contains("**") || s.contains("//") || s.startsWith(")")) return fail;
        String[] check = s.split(pattern);
        int cOpen = 0;
        int cClose = 0;
        for (int i = 0; i < check.length; i++) {
            if (check[i].equals("(")) cOpen++;
            if (check[i].equals(")")) cClose++;
        }
        if (cOpen != cClose) return fail;
        return "good";
    }

    public static String checkAssignment(String temp) {
        String[] s = temp.split("\\s*=\\s*");
        if (!s[0].matches("[a-zA-Z]+")) return "Invalid identifier";
        if (!s[1].matches("[a-zA-Z]+|[0-9]+") || s.length > 2) return "Invalid assignment";
        if (s[1].matches("[a-zA-Z]+")) {
            if (map.containsKey(s[1])) {
                map.put(s[0], map.get(s[1]));
                return "good";
            } else {
                return "Unknown variable";
            }
        } else {
            map.put(s[0], s[1]);
            return "good";
        }
    }

    public static String checkCommand(String s) {
        if (s.equals("/exit")) {
            return "Bye!";
        } else if (s.equals("/help")) {
            isShown = true;
            return STRING;
        } else {
            isShown = true;
            return "Unknown command";
        }
    }

    public static BigInteger process(BigInteger x, String o, BigInteger y) {
        if (o.equals("+")) return x.add(y);
        else if (o.equals("-")) return x.subtract(y);
        else if (o.equals("*")) return x.multiply(y);
        else return x.divide(y);
    }

    public static ArrayList<String> convertInfixToPostfix(String s) {
        String[] strings = s.split(pattern);
        ArrayList<String> result = new ArrayList<>();
        Stack<String> q = new Stack<>();
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].matches("\\w+")) {
                result.add(strings[i]);
            } else if (strings[i].equals(")")) {
                String buf;
                while (!q.isEmpty() && !((buf = q.pop()).equals("("))) {
                    result.add(buf);
                }
            } else {
                if (q.isEmpty() || strings[i].equals("(") || q.peek().equals("(")) {
                    q.add(strings[i]);
                } else {
                    while (!q.isEmpty() && (compareOperands(strings[i], q.peek()) <= 0) && !q.peek().equals("(")) {
                        result.add(q.pop());
                    }
                    q.add(strings[i]);
                }
            }
        }
        while (!q.isEmpty()) {
            result.add(q.pop());
        }
        return result;
    }

    public static String delWhiteSpaces(String s) {
        StringBuilder sb = new StringBuilder();
        s.chars().filter(x -> !Character.isWhitespace(x))
                .forEach(sb::appendCodePoint);
        for (int i = 1; i < sb.length(); i++) {
            if (sb.charAt(i) == '+' && sb.charAt(i) == sb.charAt(i - 1)) {
                sb.deleteCharAt(i - 1);
                i--;
            } else if (sb.charAt(i) == '-' && sb.charAt(i) == sb.charAt(i - 1)) {
                int count = 2;
                int j = i + 1;
                for (; j < sb.length(); j++) {
                    if (sb.charAt(j) == '-') count++;
                    else break;
                }
                for (int k = i - 1; k < j - 1; k++) {
                    sb.deleteCharAt(k);
                }
                sb.setCharAt(i - 1, count % 2 == 0 ? '+' : '-');
                sb.trimToSize();
                i = j - 1;
            }
        }
        return sb.toString();
    }


    public static String calculate(String temp) {
        String[] s;

        if (temp.contains("=")) { // реализация создания переменных
            String buf = checkAssignment(temp); //проверяем правильность присваивания
            if (!buf.equals("good")) {
                return buf;
            }
        } else {
            if (temp.startsWith("/")) {//проверяем комманду
                String check = checkCommand(temp);
                return check;
            }
            String newTemp = delWhiteSpaces(temp); //избавляемся от пробелов
            String buf = checkExpression(newTemp); //проверяем правильность выражения
            if (!buf.equals("good")) {
                return buf;
            }
            s = newTemp.split(pattern);
            if (s.length >= 2) {
                ArrayList<String> list = convertInfixToPostfix(newTemp);//конвертируем в постфикскную нотацию
                Stack<String> secondStack = new Stack<>();
                isShown = false;
                BigInteger count;
                if (newTemp.startsWith("-")) secondStack.add(String.valueOf(0));
                for (String l : list) {
                    try {
                        if (l.matches("\\d+")) {
                            secondStack.add(l);
                        } else if (l.matches("\\w+")) {
                            if (map.containsKey(l)) {
                                secondStack.add(map.get(l));
                            } else {
                                return "Unknown variable";
                            }
                        } else {
                            BigInteger y = new BigInteger(secondStack.pop());
                            BigInteger x = new BigInteger(secondStack.pop());
                            count = process(x, l, y);
                            secondStack.add(String.valueOf(count));
                        }
                    } catch (Throwable t) {
                        isShown = true;
                        return "Invalid expression";
                    }
                }
                if (!isShown) {
                    return "Your result = " + secondStack.pop();
                }
            } else if (!s[0].isEmpty()) {
                try {
                    if (s[0].matches("[a-zA-Z]+")) {
                        return "Your result = " + map.getOrDefault(s[0], "Unknown variable");
                    }
                    return "Your result = " + BigInteger.valueOf(Long.valueOf(s[0])).toString();
                } catch (Throwable t) {
                    isShown = true;
                    return "Invalid expression";
                }
            }
        }
        return "Invalid expression";
    }
}