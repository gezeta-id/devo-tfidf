package net.tinselcity.devo.helpers;

import java.util.HashMap;
import java.util.Hashtable;

public class Store {

    private Hashtable<String,Hashtable<String, Float>> tfs = new Hashtable<String,Hashtable<String, Float>>();

    private int totalNumberOfFiles = 0;

    public Store(String termString) {
        String[] terms = termString.split(" ");
        for (String term : terms) {
            tfs.put(term, new Hashtable<String, Float>());
        }
    }

    public void addFileData(String term, String fileName, Float tf) {
        if (!tfs.containsKey(term)) return;

        totalNumberOfFiles++;
        if (tf != 0) {
            Hashtable<String, Float> termTable = (Hashtable<String, Float>) tfs.get(term);
            termTable.put(fileName, tf);
        }

    }
    public Float getTf(String term, String fileName) {
        Float tf = ((Hashtable<String, Float>) tfs.get(term)).get(fileName);

        return (tf != null) ? tf : 0f;
    }
    public int getTotalNumberOfFiles() {
        return totalNumberOfFiles;
    }

}
