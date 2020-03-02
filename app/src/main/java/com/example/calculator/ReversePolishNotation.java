package com.example.calculator;

import java.util.Stack;

//класс для подсчета мат. выражения:
//переводит в постфиксную форму и считает параллельно
public class ReversePolishNotation {

    //допустимые операторы
    private char[] operators = {'+', '-', '*', '/', '(', ')'};

    //проверка символа на оператор
    private Boolean isOperator(char t) {
        for (int i:operators) {
            if (i == t)
                return true;
        }
        return false;
    }

    //возвращает приоритет операции:
    //+, - : 1
    //*, / : 2
    private int Priority(char t) {
        return (t == '+' || t == '-') ? 1 :
                (t == '*' || t == '/') ? 2 : -1;
    }

    //метод подсчета в зависимости от операции
    private double Calculate(double b, double a, char operation) {
        switch (operation) {
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

    //метод подсчета мат. выражения, возвращает результат
    public double CountString (String str) {

        char[] array = str.toCharArray();
        String numbuff = ""; //временная строка для числа
        boolean isUnary = false; //флаг для проверки унарности операции

        Stack<Double> numbersStack = new Stack<>(); //стек чисел
        Stack<Character> operationsStack = new Stack<>(); //стек операторов

        for (int i = 0; i < array.length; i++) {
            //если символ не оператор, то это число
            if (!isOperator(array[i])) {
                while (i < array.length  && !isOperator(array[i])) {
                    numbuff += array[i]; //число забивается в строку по одному символу
                    i++;
                }
                i--;
                //если дробные числа записаны через запятую:
                numbuff = numbuff.replace(',', '.');
                if (isUnary) //если предыдущий символ был унарным минусом
                    numbersStack.push(-Double.parseDouble(numbuff)); //вставка в стек с отриц. знаком
                else numbersStack.push(Double.parseDouble(numbuff)); //вставка в стек
                numbuff = "";
            }
            //вставка оператора в стек
            else {
                int stackPriority; //приоритет операции в стеке
                if (operationsStack.isEmpty())
                    stackPriority = -1; //если операторов в стеке нет
                else stackPriority = Priority(operationsStack.peek());

                if (array[i] == '(') { //открывающая скобка всегда идет в стек
                    operationsStack.push(array[i]);
                }
                else {
                    if (array[i] == ')') { //подсчет всех операций в стеке до открывающей скобки
                        char op = operationsStack.pop();
                        while (op != '(') {
                            numbersStack.push(Calculate(numbersStack.pop(), numbersStack.pop(), op));
                            op = operationsStack.pop();
                        }
                    }
                    else {
                        if (array[i] == '-' && (i == 0 || isOperator(array[i - 1])))
                            isUnary = true; //оператор унарный, если предыдущий символ это оператор
                        else {
                            //если приоритет проверяемой операции
                            // выше приоритета последней операции из стека
                            if (Priority(array[i]) > stackPriority) {
                                operationsStack.push(array[i]);
                            }
                            else {
                                // если приоритет меньше или равен, подсчет
                                // последних двух чисел из стека и операции из стека
                                numbersStack.push(Calculate(numbersStack.pop(), numbersStack.pop(), operationsStack.pop()));
                                i--;
                            }
                        }
                    }
                }
            }
        }

        //досчитываем оставшиеся элементы из стека
        while (!operationsStack.isEmpty()) {
            numbersStack.push(Calculate(numbersStack.pop(), numbersStack.pop(), operationsStack.pop()));
        }

        return numbersStack.pop(); //возвращает результат
    }
}
