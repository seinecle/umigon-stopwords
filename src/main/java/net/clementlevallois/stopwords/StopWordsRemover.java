/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.clementlevallois.stopwords;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author C. Levallois
 */
public final class StopWordsRemover {

    private String entryWord;
    private boolean multipleWord;
    private int minWordLength;

    private final int maxAcceptedGarbage = 3;
    private int nbStopWords = 5000;
    private int nbStopWordsShort = 500;

    private Set<String> setStopWordsFieldSpecificOrShort = new HashSet();
    private Set<String> setStopWordsShort = new HashSet();
    private Set<String> setStopwordsFieldSpecific = new HashSet();
    private Set<String> setStopWords = new HashSet();
    private Set<String> setKeepWords = new HashSet();
    private Set<String> setRemoveWords = new HashSet();
    private List<String> listGeneralStopwordsLarge = new ArrayList();
    private List<String> listGeneralStopwordsShort = new ArrayList();
    private List<String> stopwordsLong = new ArrayList();
    private Map<String, Set<String>> stopWordsLongAndShort;

    public static void main(String[] args) throws Exception {
        Set<String> fieldSpecificTerms = new HashSet();
        fieldSpecificTerms.add("twitter");
        StopWordsRemover rem = new StopWordsRemover(3, "en");
        rem.addFieldSpecificStopWords(fieldSpecificTerms);
        Set<String> scientificStopwordsInEnglish = Stopwords.getScientificStopwordsInEnglish();
        rem.addFieldSpecificStopWords(scientificStopwordsInEnglish);
        boolean shouldItBeRemoved = rem.shouldItBeRemoved("of textual");
        System.out.println(shouldItBeRemoved);
    }

    public StopWordsRemover(int minWordLength, String lang) {
        stopWordsLongAndShort = Stopwords.getStopWords(lang);
        stopwordsLong = new ArrayList((Set<String>) stopWordsLongAndShort.get("long"));
        this.minWordLength = minWordLength;
        nbStopWordsShort = Math.min(nbStopWordsShort, Math.max(0, (stopwordsLong.size() - 1)));
        nbStopWords = Math.min(5000, Math.max(0, (stopwordsLong.size() - 1)));
        try {
            init();
        } catch (IOException ex) {
            System.out.println("ex: " + ex);
        }
    }

    public void addStopWordsToKeep(Set<String> wordsToKeep) {
        if (wordsToKeep != null) {
            setKeepWords.addAll(wordsToKeep);
        }
    }

    public void addWordsToRemove(Set<String> wordsToRemove) {
        if (wordsToRemove != null) {
            setRemoveWords.addAll(wordsToRemove);
        }
    }

    public void useUSerSuppliedStopwords(Set<String> userSuppliedStopwords, boolean userStopwordsReplaceDefault) {
        if (userStopwordsReplaceDefault) {
            setStopWordsFieldSpecificOrShort = new HashSet(userSuppliedStopwords);
            setStopWordsShort = new HashSet(userSuppliedStopwords);
            setStopwordsFieldSpecific = new HashSet(userSuppliedStopwords);
            setStopWords = new HashSet(userSuppliedStopwords);
        } else {
            setStopWordsFieldSpecificOrShort.addAll(userSuppliedStopwords);
            setStopWordsShort.addAll(userSuppliedStopwords);
            setStopWords.addAll(userSuppliedStopwords);
        }
    }

    public void addFieldSpecificStopWords(Set<String> fieldSpecificStopWordsToRemove) {
        if (fieldSpecificStopWordsToRemove != null) {
            setStopWordsFieldSpecificOrShort.addAll(fieldSpecificStopWordsToRemove);
            setStopWords.addAll(fieldSpecificStopWordsToRemove);
        }
    }

    private void init() throws IOException {
        setKeepWords = new HashSet();
        setStopWordsShort = new HashSet();

        listGeneralStopwordsLarge = stopwordsLong.subList(0, nbStopWords);
        listGeneralStopwordsShort = stopwordsLong.subList(0, nbStopWordsShort);

        setStopWords.addAll(listGeneralStopwordsLarge);
        setStopWords.addAll(Stopwords.getStopwordsValidForAllLanguages());
        if (stopWordsLongAndShort.get("short").isEmpty()) {
            setStopWordsShort.addAll(listGeneralStopwordsShort);
        } else {
            setStopWordsShort.addAll(stopWordsLongAndShort.get("short"));
        }
        setStopWordsFieldSpecificOrShort.addAll(setStopWordsShort);
    }

    public boolean shouldItBeRemoved(String term) {

        boolean write = true;
        entryWord = term;
        multipleWord = entryWord.contains(" ");

        if (multipleWord) {
            String[] wordsNGrams = entryWord.split(" ");
            int wordsNGramsLength = wordsNGrams.length;

            for (String wordsNGram : wordsNGrams) {
                if (wordsNGram.length() < minWordLength) {
                    write = false;
                    break;
                }
            }

            if (wordsNGramsLength == 2
                    && ((setStopWordsFieldSpecificOrShort.contains(wordsNGrams[0].toLowerCase().trim())
                    || setStopWordsFieldSpecificOrShort.contains(wordsNGrams[1].toLowerCase().trim())))) {
                write = false;

            }

            if (wordsNGramsLength > 2) {
                int scoreGarbage = 0;

                for (int i = 0; i < wordsNGramsLength; i++) {

                    String currentTerm = wordsNGrams[i].toLowerCase().trim();

                    if ((i == 0 | i == (wordsNGramsLength - 1)) && setStopWordsFieldSpecificOrShort.contains(currentTerm)) {
                        scoreGarbage = maxAcceptedGarbage + 1;
                        continue;
                    }

                    if ((i == 0 | i == (wordsNGramsLength - 1)) && setStopWordsShort.contains(currentTerm)) {
                        write = false;
                        continue;
                    }

                    if (setStopWordsShort.contains(currentTerm)) {
                        scoreGarbage = scoreGarbage + 3;
                        continue;
                    }

                    if (setStopwordsFieldSpecific.contains(currentTerm)) {
                        scoreGarbage = scoreGarbage + 2;
                        continue;
                    }

                }

                if (setStopWords.contains(entryWord)) {
                    scoreGarbage = maxAcceptedGarbage + 1;
                }

                if (scoreGarbage > maxAcceptedGarbage) {

                    write = false;
                }
            }

        } else if (setStopWords.contains(entryWord) & !setKeepWords.contains(entryWord)) {
            write = false;
        }

        if (setKeepWords.contains(entryWord)) {
            write = true;
        }
        if (setRemoveWords.contains(entryWord)) {
            write = false;
        }

        return !write;

    }
}
