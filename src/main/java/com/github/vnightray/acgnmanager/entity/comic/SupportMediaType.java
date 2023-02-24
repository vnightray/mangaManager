package com.github.vnightray.acgnmanager.entity.comic;

import org.apache.commons.lang3.StringUtils;

public enum SupportMediaType {
    PDF("pdf"), ZIP("zip"), RAR("rar"), Z7("z7"), UNKNOWN("unknown");
    public String mediaType;
    SupportMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

//    public SupportMediaType convertToSupportMediaType(String extension){
//        if (StringUtils.isEmpty(extension)){
//            return UNKNOWN;
//        }
//        switch (extension){
//            case "pdf": return PDF;
//            case "zip": return ZIP;
//            case "rar": return RAR;
//            case "7z": return Z7;
//            default: return UNKNOWN;
//        }
//    }
}
