package com.example.demo.Utiliy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utility {

    public static String extractUriQueryString(String url) {
        Pattern p = Pattern.compile("^(http|https)://.+?/(.+)$");
        Matcher m = p.matcher(url);
        String uri = "";
        if(m.matches()) {
            uri = m.group(2);
        }

        return "/" + uri;
    }
}
