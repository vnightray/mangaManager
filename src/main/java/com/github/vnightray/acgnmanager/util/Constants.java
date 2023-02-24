package com.github.vnightray.acgnmanager.util;

import org.apache.commons.lang3.StringUtils;

public class Constants {

    private static final String[] EXTENSIONS = {"zip", "rar", "7z", "pdf"};

    public static Boolean assertExtension(String fileName){
        if (StringUtils.isEmpty(fileName)){
            return false;
        }
        for (String extension : EXTENSIONS) {
            if (fileName.endsWith(extension)){
                return true;
            }
        }
        return false;
    }

    // todo 由文件内容判断文件真实类型而不是通过扩展名
    public static String distinguishExtension(String fileName){
        if (StringUtils.isEmpty(fileName)){
            return "UNKNOWN";
        }
        for (String extension : EXTENSIONS) {
            if (fileName.endsWith(extension)){
                return extension.toUpperCase();
            }
        }
        return "UNKNOWN";
    }
}
