/*
 * Copyright Clement Levallois 2021-2023. License Attribution 4.0 Intertnational (CC BY 4.0)
 */
package net.clementlevallois.stopwords;

import java.util.Optional;

/**
 *
 * @author LEVALLOIS
 */
public class ResourcePath {

    public static String returnRootResources() {
        String PATHLOCALE;
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            String userName = Optional.ofNullable(System.getenv("USERNAME"))
                    .orElse("Unknown User");
            if (userName.equals("clevallois")) {
                userName = userName + ".GOBELINS";
            }

            PATHLOCALE = "C:\\Users\\" + userName + "\\open\\nocode-app-functions\\umigon-static-files\\";
        } else {
            PATHLOCALE = "/home/waouh/nocodeapp-next/umigon-static-files/";
        }

        return PATHLOCALE;
    }

}
