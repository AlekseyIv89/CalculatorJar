package ru.ivanov.springCalc.Calculator.service;

import org.springframework.stereotype.Service;

@Service
public class CalcService {
    public String calculate(String input) {
        String[] numbers = input.split("\s*([+]|[*]|-|/)\s*");

        try {
            // Проверка на то, что выражение состоит из двух чисел
            if (numbers.length != 2) {
                if (numbers.length == 1)
                    throw new Exception("Строка не является математической операцией");
                else
                    throw new Exception("Формат математической операции не удовлетворяет заданию - два операнда и один оператор");
            }

            int a, b;
            boolean isAArabic = false, isBArabic = false;

            // п.2, п.4 Преобразование введеных чисел для проведения с ними математических операций
            // isAArabic, isBArabic = true - при вводе арабских чисел, false - при вводе римских чисел
            try {
                a = Integer.parseInt(numbers[0]);
                isAArabic = true;
            } catch (NumberFormatException e) {
                a = NumbersConverter.convertRomanToArabic(numbers[0]);
            }

            try {
                b = Integer.parseInt(numbers[1]);
                isBArabic = true;
            } catch (NumberFormatException e) {
                b = NumbersConverter.convertRomanToArabic(numbers[1]);
            }

            // п.3 Проверка на ввод значения чисел, они должны лежать в пределах [1, 10]
            checkInputNumber(a);
            checkInputNumber(b);

            // п.5 Проверка на ввод значений в одной системе счисления
            if (isAArabic ^ isBArabic)
                throw new Exception("Запрещено использовать одновременно разные системы счисления");

            // Вычисление результата математической операции
            int result = 0;
            if (input.contains("-"))
                result = a - b;
            else if (input.contains("+"))
                result = a + b;
            else if (input.contains("*"))
                result = a * b;
            else if (input.contains("/"))
                result = a / b;

            // п. 10 Проверка на полученный результат математической операции в римской системе счисления
            if (!isAArabic) checkResultForRomanNumbers(result);

            return isAArabic ? String.valueOf(result) : NumbersConverter.convertArabicToRoman(result);
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    private void checkInputNumber(int i) throws Exception {
        if (i < 1 || i > 10)
            throw new Exception("Введенное число \"" + i + "\" не соответствует заданию - число не входит в предел [1, 10]");
    }

    private void checkResultForRomanNumbers(int i) throws Exception {
        if (i < 1)
            throw new Exception("В римской системе нет отрицательных чисел");
    }

    static class NumbersConverter {

        static String convertArabicToRoman(int number) {
            int[] numbers = { 100, 90, 50, 40, 10, 9, 5, 4, 1 };
            String[] romanNumbers = { "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
            StringBuilder romanNumber = new StringBuilder();

            for(int i = 0; i < numbers.length; i++) {
                while(number >= numbers[i]) {
                    number -= numbers[i];
                    romanNumber.append(romanNumbers[i]);
                }
            }

            return romanNumber.toString();
        }

        static int convertRomanToArabic(String number) throws Exception {
            return switch (number) {
                case "I" -> 1;
                case "II" -> 2;
                case "III" -> 3;
                case "IV" -> 4;
                case "V" -> 5;
                case "VI" -> 6;
                case "VII" -> 7;
                case "VIII" -> 8;
                case "IX" -> 9;
                case "X" -> 10;
                default -> throw new Exception("Введенное число \""+ number +"\" не соответствует заданию - число не входит в предел [1, 10]");
            };
        }
    }
}
