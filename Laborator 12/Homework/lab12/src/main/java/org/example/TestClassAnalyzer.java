package org.example;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

public class TestClassAnalyzer {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        String inputPath = "<input_path>"; //path to the folder or JAR file containing classes

        List<Class<?>> classes = getClasses(inputPath);
        for (Class<?> clazz : classes) {
            printClassPrototype(clazz);
            runTests(clazz);
        }

        for (Class<?> clazz : classes) {
            printClassPrototype(clazz);
            runTests(clazz);
            printStatistics(clazz); //pass the clazz param
        }

    }

    private static List<Class<?>> getClasses(String inputPath) throws IOException, ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();

        File file = new File(inputPath);
        if (file.isDirectory()) {
            exploreDirectory(file, classes);
        } else if (file.isFile() && file.getName().endsWith(".jar")) {
            exploreJar(file, classes);
        }

        return classes;
    }

    private static void exploreDirectory(File directory, List<Class<?>> classes) throws ClassNotFoundException {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    exploreDirectory(file, classes);
                } else if (file.getName().endsWith(".class")) {
                    String className = file.getAbsolutePath()
                            .replace(directory.getAbsolutePath() + File.separator, "")
                            .replace(".class", "")
                            .replace(File.separator, ".");
                    Class<?> clazz = Class.forName(className);
                    classes.add(clazz);
                }
            }
        }
    }

    private static void exploreJar(File jarFile, List<Class<?>> classes) throws IOException, ClassNotFoundException {
        URL jarUrl = jarFile.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});
        //assumes that the JAR file contains the compiled class files in the root directory
        List<String> classNames = JarUtils.getClassNamesFromJar(jarFile.getAbsolutePath());
        for (String className : classNames) {
            Class<?> clazz = classLoader.loadClass(className);
            classes.add(clazz);
        }
    }

    private static void printClassPrototype(Class<?> clazz) {
        System.out.println("Class: " + clazz.getName());
        System.out.println("Prototype:");
        for (Method method : clazz.getDeclaredMethods()) {
            System.out.println("\t" + method.toString());
        }
        System.out.println();
    }

    private static void runTests(Class<?> clazz) {
        System.out.println("Running tests for class: " + clazz.getName());
        int totalTests = 0;
        int passedTests = 0;

        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class) && method.getReturnType().equals(void.class)) {
                totalTests++;
                try {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    method.setAccessible(true);
                    method.invoke(instance);
                    passedTests++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed tests: " + passedTests);
        System.out.println();
    }

    private static void printStatistics(Class<?> clazz) {
        int totalTests = 0;
        int passedTests = 0;

        //count the total number of tests and the number of passed tests
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Test.class) && method.getReturnType().equals(void.class)) {
                totalTests++;
                try {
                    Object instance = clazz.getDeclaredConstructor().newInstance();
                    method.setAccessible(true);
                    method.invoke(instance);
                    passedTests++;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        //calculate the pass rate
        double passRate = (double) passedTests / totalTests * 100;

        System.out.println("Statistics:");
        System.out.println("Total tests: " + totalTests);
        System.out.println("Passed tests: " + passedTests);
        System.out.printf("Pass rate: %.2f%%\n", passRate);
    }


}
