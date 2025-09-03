package com.tajutechgh.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UrlEncode {

    public static String getUrlEncoder(String parameter){

        return URLEncoder.encode(parameter, StandardCharsets.UTF_8);
    }
}
