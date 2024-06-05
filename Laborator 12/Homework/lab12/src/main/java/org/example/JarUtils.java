package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

public class JarUtils {

    public static List<String> getClassNamesFromJar(String jarFilePath) throws IOException {
        List<String> classNames = new ArrayList<>();

        try (JarInputStream jarInputStream = new JarInputStream(new FileInputStream(jarFilePath))) {
            JarEntry jarEntry;
            while ((jarEntry = jarInputStream.getNextJarEntry()) != null) {
                if (jarEntry.getName().endsWith(".class")) {
                    String className = jarEntry.getName()
                            .replace('/', '.')
                            .replace(".class", "");
                    classNames.add(className);
                }
            }
        }

        return classNames;
    }
}

