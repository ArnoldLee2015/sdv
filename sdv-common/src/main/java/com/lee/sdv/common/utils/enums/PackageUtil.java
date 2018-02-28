/*
 * Copyright (c) 2016 www.lee.com All rights reserved.
 * 本软件源代码版权归lee所有,未经许可不得任意复制与传播.
 */
package com.lee.sdv.common.utils.enums;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;


/**
 * 获取java类名
 *
 * @author lipeng
 */
public class PackageUtil {
    /**
     * 返回包下所有的类
     *
     * @param packagePath 包名
     * @return List<String> 包下所有的类
     */
    public static List<String> getPackageClasses(String packagePath) {
        return getPackageClasses(packagePath, true);
    }

    /**
     * 返回包下所有的类
     *
     * @param packagePath   包名全路径
     * @param classWithPath 返回全路径开关 true 自动带上包名
     * @return List<String> 包下所有的类
     */
    public static List<String> getPackageClasses(String packagePath, boolean classWithPath) {
        List<String> classNames = getClassName(packagePath);
        List<String> result = new ArrayList<String>(classNames.size());
        for (String className : classNames) {
            result.add(classWithPath ? className : className.substring(className.lastIndexOf(".") + 1));
        }
        return result;
    }

    private static List<String> getClassName(String packageName) {
        List<String> fileNames = new ArrayList<String>();
        String filePath = PackageUtil.class.getResource("").getPath();
        if (StringUtils.indexOf(filePath, ".jar!") > 0) {
            filePath = StringUtils.substringBeforeLast(filePath, ".jar!") + ".jar";
            filePath = StringUtils.substring(filePath, 6);
            String packageNamePath = StringUtils.replace(packageName, ".", "/");
            fileNames = getClasssFromJarFile(filePath, packageNamePath);
        } else {
            filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
            fileNames = getClassName(filePath, null);
        }
        return fileNames;
    }

    private static List<String> getClassName(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<String>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        for (File childFile : childFiles) {
            if (childFile.isDirectory()) {
                myClassName.addAll(getClassName(childFile.getPath(), myClassName));
            } else {
                String childFilePath = childFile.getPath();
                childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                childFilePath = childFilePath.replace("\\", ".");
                myClassName.add(childFilePath);
            }
        }
        return myClassName;
    }


    /**
     * 从jar文件中读取指定目录下面的所有的class文件
     *
     * @param jarPath  jar文件存放的位置
     * @param filePath 指定的文件目录
     * @return 所有的的class的对象
     */
    public static List<String> getClasssFromJarFile(String jarPath, String filePath) {
        List<String> clazzs = new ArrayList<String>();

        JarFile jarFile = null;
        try {
            jarFile = new JarFile(jarPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        List<JarEntry> jarEntryList = new ArrayList<JarEntry>();

        Enumeration<JarEntry> ee = jarFile.entries();
        while (ee.hasMoreElements()) {
            JarEntry entry = (JarEntry) ee.nextElement();
            // 过滤我们出满足我们需求的东西
            if (entry.getName().startsWith(filePath) && entry.getName().endsWith(".class")) {
                jarEntryList.add(entry);
            }
        }
        for (JarEntry entry : jarEntryList) {
            String className = entry.getName().replace('/', '.');
            className = className.substring(0, className.length() - 6);
            clazzs.add(className);
        }

        return clazzs;
    }

}