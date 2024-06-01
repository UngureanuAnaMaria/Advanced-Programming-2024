package org.example;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ClassAnalyzer {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface Test {
    }

    public static void main(String[] args) throws ClassNotFoundException {
        if (args.length == 0) {
            System.out.println("Please provide the fully qualified class name as an argument.");
            return;
        }

        String className = args[0];
        ClassLoader classLoader = ClassAnalyzer.class.getClassLoader();
        Class<?> clazz;
        try {
            clazz = classLoader.loadClass(className);
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + className);
            return;
        }

        //extract package info
        Package pkg = clazz.getPackage();
        if (pkg != null) {
            System.out.println("Package: " + pkg.getName());
        } else {
            System.out.println("Package: (default)");
        }

        //extract class info
        System.out.println("Class: " + clazz.getSimpleName());
        System.out.println("Modifiers: " + Modifier.toString(clazz.getModifiers()));
        System.out.println();

        //extract method info
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("Method: " + method.getName());
            System.out.println("Modifiers: " + Modifier.toString(method.getModifiers()));

            //check if method has @Test annotation and is static
            if (method.isAnnotationPresent(Test.class) && Modifier.isStatic(method.getModifiers())) {
                try {
                    method.setAccessible(true);
                    method.invoke(null); //invoke the static method with no arg
                    System.out.println("Test passed!");
                } catch (Exception e) {
                    System.out.println("Test failed: " + e.getMessage());
                }
            }

            System.out.println();
        }

        //invoke test methods
        invokeTestMethods(clazz);
    }

    public static void invokeTestMethods(Class<?> clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Test.class) && Modifier.isStatic(method.getModifiers())) {
                try {
                    method.invoke(null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

