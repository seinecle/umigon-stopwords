/*
 * Copyright Clement Levallois 2021-2023. License Attribution 4.0 Intertnational (CC BY 4.0)
 */
package net.clementlevallois.stopwords;

/**
 *
 * @author LEVALLOIS
 */
public class ResourcePath {

    public static String returnRootResources() {
        String PATHLOCALE;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            PATHLOCALE = "C:\\Users\\levallois\\open\\nocode-app-functions\\umigon-static-files\\";
        } else {
            PATHLOCALE = "/home/waouh/nocodeapp-web/umigon-static-files/";
        }
        
        return PATHLOCALE;
    }

}
