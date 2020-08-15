package net.tinselcity.devo.helpers;

import java.util.*;

public class Store {

    private Set<String> filesVisited = new HashSet<String>();

    private Hashtable<String,Hashtable<String, Double>> tfs = new Hashtable<String,Hashtable<String, Double>>();


    public Store(String termString) {
        String[] terms = termString.split(" ");
        for (String term : terms) {
            tfs.put(term, new Hashtable<String, Double>());
        }
    }

    public void addFileData(String term, String fileName, Double tf) {
        if (!tfs.containsKey(term)) return;

        filesVisited.add(fileName);

        if (tf != 0) {
            Hashtable<String, Double> termTable = (Hashtable<String, Double>) tfs.get(term);
            termTable.put(fileName, tf);
        }
    }

    public Double getTf(String term, String fileName) {
        Double tf = ((Hashtable<String, Double>) tfs.get(term)).get(fileName);

        return (tf != null) ? tf : 0f;
    }

    public Iterator<String> getTerms() {
        return tfs.keySet().iterator();
    }

    public Iterator<Map.Entry<String, Double>> getData(String term) {
        return tfs.get(term).entrySet().iterator();
    }

    public int getTotalNumberOfFiles() {
        return filesVisited.size();
    }

    public int getNumberOfFilesContaining(String term) {
        return tfs.get(term).size();
    }

}
