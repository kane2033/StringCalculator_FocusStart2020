package com.example.calculator;

import java.util.Stack;

public class ReversePolishNotation {

    /*
    public static void main(String[] args) {
        System.out.println("кукулятор");
        //String str = "(46+-10-4)/(1+11*2)+13";
        //String str = "26/5*22.3";
        String str = "(26";
        System.out.println(str);
        System.out.println("Результат: " + ConvertAndCountRPN(str));
    }
    */



    //можно сделать класс операторов??
    private char[] operators = {'+', '-', '*', '/', '(', ')'};

    private Boolean isOperator(char t) {
        for (int i:operators) {
            if (i == t)
                return true;
        }
        return false;
    }

    private int Priority(char t) {
        return (t == '+' || t == '-') ? 1 :
                (t == '*' || t == '/') ? 2 : -1;
    }

    private double Calculate(double b, double a, char op) {
        switch (op) {
            case ('+'):
                return a + b;
            case ('-'):
                return a - b;
            case ('*'):
                return a * b;
            case ('/'):
                return a / b;
            default:
                return 0.0;
        }
    }

    //тут предполагается, что строка уже проверена на отсутсвие
    //всяких лишних вещей
    //строка - в правильном виде
    public double CountString (String str) {
        char[] arr = str.toCharArray();
        Stack<Double> out = new Stack<Double>(); //числа
        Stack<Character> oper = new Stack<Character>(); //операторы
        String numbuff = ""; //временная строка для числа
        Boolean isUnary = false;

        for (int i = 0; i < arr.length; i++) {
            //вставка числа в стек
            if (!isOperator(arr[i])) {
                while (i < arr.length  && !isOperator(arr[i])) {
                    numbuff += arr[i];
                    i++;
                }
                i--;
                //если дробные числа записаны через запятую:
                numbuff = numbuff.replace(',', '.');
                if (isUnary)
                    out.push(-Double.parseDouble(numbuff));
                else out.push(Double.parseDouble(numbuff));
                numbuff = "";
            }
            //вставка операнда в стек
            else {
                int stackPriority;
                if (oper.isEmpty())
                    stackPriority = -1;
                else stackPriority = Priority(oper.peek());

                if (arr[i] == '(') {
                    oper.push(arr[i]);
                }
                else {
                    if (arr[i] == ')') {
                        char op = oper.pop();
                        while (op != '(') {
                            out.push(Calculate(out.pop(), out.pop(), op));
                            op = oper.pop();
                        }
                    }
                    else {
                        if (arr[i] == '-' && (i == 0 || isOperator(arr[i - 1])))
                            isUnary = true;
                        else {
                            if (Priority(arr[i]) > stackPriority) {
                                oper.push(arr[i]);
                            }
                            else { // <= priority
                                out.push(Calculate(out.pop(), out.pop(), oper.pop()));
                                i--;
                            }
                        }
                    }
                }
            }
        }

        //досчитываем элементы из стека
        while (!oper.isEmpty()) {
            out.push(Calculate(out.pop(), out.pop(), oper.pop()));
        }
        return out.pop();

//        try {
//
//        }
//        catch (Throwable t) {
//            //System.out.println("А вот и эксепшн подъехал! " + t);
//            return 0.0;
//        }
    }
}
