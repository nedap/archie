package com.nedap.archie;

/**
 * Created by pieter.bos on 21/10/15.
 */
public class Configuration {

    private static ThreadLocal<String> currentLanguage = new ThreadLocal<>();
    private static String DEFAULT_LANGUAGE = "en";


    /**
     * The language for use in logical paths
     * @return
     */
    public static String getLogicalPathLanguage() {
        String language = currentLanguage.get();
        if(language == null) {
            language = DEFAULT_LANGUAGE;
        }
        return language;
    }


    public static void setThreadLocalLanguage(String language) {
        currentLanguage.set(language);

    }
}
