package org.example;

public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) {
        if (b == 0) {
            throw new IllegalArgumentException("Cannot divide by zero!");
        }
        return a / b;
    }

    @ClassAnalyzer.Test
    public static void testAddition() {
        Calculator calculator = new Calculator();
        int result = calculator.add(3, 4);
        assert result == 7 : "Addition test failed!";
        System.out.println("Addition test passed!");
    }

    @ClassAnalyzer.Test
    public static void testDivision() {
        Calculator calculator = new Calculator();
        int result = calculator.divide(10, 2);
        assert result == 5 : "Division test failed!";
        System.out.println("Division test passed!");
    }

    public static void main(String[] args) {
        // Invoke test methods
        ClassAnalyzer.invokeTestMethods(Calculator.class);
    }
}

