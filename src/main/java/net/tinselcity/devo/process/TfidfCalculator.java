package net.tinselcity.devo.process;

import net.tinselcity.devo.helpers.Store;

import java.util.*;

public class TfidfCalculator {
    public static List<Map.Entry<String, Double>> calculateTop(int numberOfResults, Store store) {

        double totalFiles = store.getTotalNumberOfFiles();

        Hashtable<String, Double> mtfidf = new Hashtable<>();


        for (Iterator<String> it = store.getTerms(); it.hasNext(); ) {
            String term = it.next();

            double filesWithTerm = store.getNumberOfFilesContaining(term);

            double idf = Math.log((totalFiles+1) / (filesWithTerm+1));

            for (Iterator<Map.Entry<String, Double>> iter = store.getData(term); iter.hasNext(); ) {
                Map.Entry<String, Double> tfInfo = iter.next();

                String document = tfInfo.getKey();
                Double tf = tfInfo.getValue();

                double tfidf = tf * idf;

                mtfidf.compute(document, (k,v)-> (v == null) ? tfidf : v+tfidf);

            }

        }
        List<Map.Entry<String, Double>> entries = new ArrayList<Map.Entry<String, Double>>(mtfidf.entrySet());

        Comparator<Map.Entry<String, Double>> c = Comparator.comparing(Map.Entry::getValue);
        Collections.sort(entries, c.reversed());

        if (entries.size() > numberOfResults) return entries.subList(0,numberOfResults);
        else return entries;
    }
}
