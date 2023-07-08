package org.example;

import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static java.rmi.server.RMIClassLoader.loadClass;

public class ClassLoader {

    private static Class loadClass(String path) throws MalformedURLException, ClassNotFoundException {
        File file = new File(path);
        String directoryPath = file.getParent();
        URL url = new URL("file://" + directoryPath + "/");
        URLClassLoader classLoader = new URLClassLoader(new URL[]{url});
        String fileName = file.getName();
        String className = fileName.substring(0, fileName.lastIndexOf('.'));
        Class clazz = classLoader.loadClass(className);
        return clazz;
    }
    private static void exploreDirectory(String path,List<Class> classes) throws MalformedURLException, ClassNotFoundException {
        File file = new File(path);
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(files != null)
            for(File f : files){
                exploreDirectory(f.getAbsolutePath(),classes);
            }
        }
        else if (file.isFile()){
            if(isClassFile(file)) {
                Class clazz = loadClass(file.getAbsolutePath());
                classes.add(clazz);
            }
            else if(isJarFile(file)){
                try {
                    JarFile jarFile = new JarFile(file);
                    System.out.println(file.getAbsolutePath());
                    Enumeration<JarEntry> entries = jarFile.entries();
                    URL[] urls = { new URL("jar:file:" + file.getAbsolutePath() + "!/") };
                    URLClassLoader classLoader = URLClassLoader.newInstance(urls);
                    while(entries.hasMoreElements()){
                        JarEntry entry = entries.nextElement();
                        String entryName = entry.getName();
                        if (entryName.endsWith(".class")){
                            String className = entryName.substring(0, entryName.lastIndexOf('.'));
                            Class cl = classLoader.loadClass(className);
                            classes.add(cl);
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        }
    }
    private static boolean isClassFile(File file) {
        return file.getName().endsWith(".class");
    }
    private static boolean isJarFile(File file) {
        return file.getName().endsWith(".jar");
    }
    public static void invokeTestMethods(List<Class> classes){
        int passed = 0, failed = 0;
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Test.class)) {
                System.out.println("Class: " + clazz.getName());
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.isAnnotationPresent(Test.class)) {
                        try {
                            method.invoke(null);
                            passed++;
                        } catch (Throwable e) {
                            System.out.printf("Test %s failed: %s %n",
                                    method, e.getCause());
                            failed++;
                        }
                    }
                }
            }
        }
        System.out.printf("Passed: %d, Failed %d%n", passed, failed);
    }

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("Please provide the path to the folder or JAR file.");
            return;
        }
        // Check if is jar file
        String path = args[0];
        File f = new File(path);
        if(f.getName().endsWith(".jar")){
            System.out.println("Jar file ");
        }

        List<Class> classes = new ArrayList<>();
        exploreDirectory(path,classes);
        classes.forEach(clazz->{
            System.out.println("Class: " + clazz.getName());
            System.out.println("Constructors:");
            Constructor[] constructors = clazz.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                System.out.println(constructor.toString());
            }
            System.out.println("Methods:");
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                System.out.println(method.toString());
            }
            System.out.println("Fields:");
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.toString());
            }
            System.out.println("Annotations:");
            Annotation[] annotations = clazz.getDeclaredAnnotations();
            for (Annotation annotation : annotations) {
                System.out.println(annotation.toString());
            }
            System.out.println();
        });

        invokeTestMethods(classes);
    }
}