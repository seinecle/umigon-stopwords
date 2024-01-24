/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.stopwords;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

/*
 Copyright 2008-2013 Clement Levallois
 Authors : Clement Levallois <clementlevallois@gmail.com>
 Website : http://www.clementlevallois.net


 DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.

 Copyright 2013 Clement Levallois. All rights reserved.

 The contents of this file are subject to the terms of either the GNU
 General Public License Version 3 only ("GPL") or the Common
 Development and Distribution License("CDDL") (collectively, the
 "License"). You may not use this file except in compliance with the
 License. You can obtain a copy of the License at
 http://gephi.org/about/legal/license-notice/
 or /cddl-1.0.txt and /gpl-3.0.txt. See the License for the
 specific language governing permissions and limitations under the
 License.  When distributing the software, include this License Header
 Notice in each file and include the License files at
 /cddl-1.0.txt and /gpl-3.0.txt. If applicable, add the following below the
 License Header, with the fields enclosed by brackets [] replaced by
 your own identifying information:
 "Portions Copyrighted [year] [name of copyright owner]"

 If you wish your version of this file to be governed by only the CDDL
 or only the GPL Version 3, indicate your decision by adding
 "[Contributor] elects to include this software in this distribution
 under the [CDDL or GPL Version 3] license." If you do not indicate a
 single choice of license, a recipient has the option to distribute
 your version of this file under either the CDDL, the GPL Version 3 or
 to extend the choice of license to its licensees as provided above.
 However, if you add GPL Version 3 code and therefore, elected the GPL
 Version 3 license, then the option applies only if the new code is
 made subject to such option by the copyright holder.

 Contributor(s): Clement Levallois

 */
public class Stopwords {

    private static final String[] twitterStopWords = {"rt", "w/"};
    private static final String[] commonStopWords = {"and", "for", "nbsp", "http", "https", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "20", "25", "30", "40", "50", "100", "1000"};

    private static final Map<String, Map<String, Set<String>>> cache = new HashMap();
    private static final Map<String, Set<String>> cacheTwitter = new HashMap();

    public static void main(String args[]) {
        try {
            InputStream fileFromResourceAsStream = Stopwords.class.getResourceAsStream("twitter_en.txt");

            BufferedReader br = new BufferedReader(new InputStreamReader(fileFromResourceAsStream));
            String readLine = br.readLine();
            System.out.println("line: " + readLine);
            Map<String, Set<String>> stopWords = Stopwords.getStopWords("en");
            System.out.println(stopWords.toString());
//        ResourceLoader.("twitter/en.txt");
//        List<String> readAllLines = Files.readAllLines(Paths.get(url.toURI()));
//        System.out.println("text: "+readAllLines.get(0));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static Map<String, Set<String>> getStopWords(String lang) {

        if (cache.containsKey(lang)) {
            return cache.get(lang);
        }

        Set<String> stopWords = new HashSet();
        Set<String> shortStopWords = new HashSet();
        Map<String, Set<String>> pair = null;

        for (String commonStopWord : commonStopWords) {
            stopWords.add(commonStopWord);
            shortStopWords.add(commonStopWord);
        }
        for (String twitterStopWord : twitterStopWords) {
            stopWords.add(twitterStopWord);
            shortStopWords.add(twitterStopWord);
        }
        String PATHLOCALE = ResourcePath.returnRootResources();

        Path pathResource = Path.of(PATHLOCALE, "src/main/resources/net/clementlevallois/umigon/stopwords/" + lang + ".txt");
        if (Files.exists(pathResource)) {
            try {
                Stream<String> lines = Files.lines(pathResource);
                lines.forEach(l -> stopWords.add(l));
            } catch (IOException e) {
                System.out.println("exception is: " + e.toString());
                e.printStackTrace();
            }
        }
        pathResource = Path.of(PATHLOCALE, "src/main/resources/net/clementlevallois/umigon/stopwords/" + lang + "_short.txt");
        if (Files.exists(pathResource)) {
            try {
                Stream<String> lines = Files.lines(pathResource);
                lines.forEach(l -> shortStopWords.add(l));
            } catch (IOException e) {
                System.out.println("exception is: " + e.toString());
                e.printStackTrace();
            }
        }

        pair = new HashMap();
        pair.put("short", shortStopWords);
        pair.put("long", stopWords);
        cache.put(lang, pair);

        return pair;
    }

    public static Set<String> getStopWordsUsefulInSentimentAnalysis(String lang) {

        Set<String> stopWords = new HashSet();
        String PATHLOCALE = ResourcePath.returnRootResources();
        Path pathResource = Path.of(PATHLOCALE, "src/main/resources/net/clementlevallois/umigon/stopwords/" + lang + "_stopword_sentiment.txt");
        if (Files.exists(pathResource)) {
            try {
                Stream<String> lines = Files.lines(pathResource);
                lines.forEach(l -> stopWords.add(l));
            } catch (IOException e) {
                System.out.println("exception is: " + e.toString());
                e.printStackTrace();
                return stopWords;
            }
        }
        return stopWords;
    }

    public static Set<String> getScientificStopwordsInEnglish() {

        Set<String> stopWords = new HashSet();
        String PATHLOCALE = ResourcePath.returnRootResources();
        Path pathResource = Path.of(PATHLOCALE, "src/main/resources/net/clementlevallois/umigon/stopwords/scientificstopwords_en.txt");
        if (Files.exists(pathResource)) {
            try {
                Stream<String> lines = Files.lines(pathResource);
                lines.forEach(l -> stopWords.add(l));
            } catch (IOException e) {
                System.out.println("exception is: " + e.toString());
                e.printStackTrace();
                return stopWords;
            }
        }
        return stopWords;
    }

    public static Set<String> getScientificStopwordsInFrench() {

        Set<String> stopWords = new HashSet();
        String PATHLOCALE = ResourcePath.returnRootResources();
        Path pathResource = Path.of(PATHLOCALE, "src/main/resources/net/clementlevallois/umigon/stopwords/scientificstopwords_fr.txt");
        if (Files.exists(pathResource)) {
            try {
                Stream<String> lines = Files.lines(pathResource);
                lines.forEach(l -> stopWords.add(l));
            } catch (IOException e) {
                System.out.println("exception is: " + e.toString());
                e.printStackTrace();
                return stopWords;
            }
        }
        return stopWords;
    }

    public static Set<String> getTwitterStopwords(boolean longList) {
        Set<String> words = new HashSet();

        String PATHLOCALE = ResourcePath.returnRootResources();
        Path pathResource = Path.of(PATHLOCALE, "src/main/resources/net/clementlevallois/umigon/stopwords/twitter_long.txt");
        if (Files.exists(pathResource)) {
            try {
                Stream<String> lines = Files.lines(pathResource);
                lines.forEach(l -> words.add(l));
            } catch (IOException e) {
                System.out.println("exception is: " + e.toString());
                e.printStackTrace();
            }
        }
        pathResource = Path.of(PATHLOCALE, "src/main/resources/net/clementlevallois/umigon/stopwords/twitter_short.txt");
        if (Files.exists(pathResource)) {
            try {
                Stream<String> lines = Files.lines(pathResource);
                lines.forEach(l -> words.add(l));
            } catch (IOException e) {
                System.out.println("exception is: " + e.toString());
                e.printStackTrace();
            }
        }
        return words;
    }

    public static Set<String> getStopwordsValidForAllLanguages() {
        Set<String> words = new HashSet();
        words.addAll(Arrays.asList(commonStopWords));
        words.addAll(Arrays.asList(twitterStopWords));

        String PATHLOCALE = ResourcePath.returnRootResources();
        Path pathResource = Path.of(PATHLOCALE, "src/main/resources/net/clementlevallois/umigon/stopwords/stopwords_all_languages.txt");
        if (Files.exists(pathResource)) {
            try {
                Stream<String> lines = Files.lines(pathResource);
                lines.forEach(l -> words.add(l));
            } catch (IOException e) {
                System.out.println("exception is: " + e.toString());
                e.printStackTrace();
            }
        }
        return words;

    }

}
