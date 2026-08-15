package com.siddhant.healthreport.medicalaibot.utils;

import lombok.experimental.UtilityClass;

import java.util.HexFormat;

@UtilityClass
public class HexUtils {

    public static String bytesToHex(byte[] bytes) {
        return HexFormat.of().formatHex(bytes);
    }
}
