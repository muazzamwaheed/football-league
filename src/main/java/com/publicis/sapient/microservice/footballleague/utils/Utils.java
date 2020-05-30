package com.publicis.sapient.microservice.footballleague.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Utils {

    public static StringBuilder formRequestUrl(String url, String apiKey, String action) {
        return new StringBuilder().append(url).append("?action=").append(action).append("&APIkey=").append(apiKey);
    }
}
