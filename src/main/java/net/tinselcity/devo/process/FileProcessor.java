package net.tinselcity.devo.process;

import net.tinselcity.devo.helpers.Store;

import java.io.*;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileProcessor {


    private final Set<String> terms;
    private final Store store;
    private final String path;

    public FileProcessor(String termString, String path, Store store) {
        this.store = store;
        this.terms = Set.of(termString.split(" "));
        this.path = path;
    }

    public void processFile(String filename) {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path + "/" + filename));
            String line = reader.readLine();

            HashMap<String, Integer> occurrences = new HashMap<String, Integer>();

            Integer totalWords = 0;

            while (line != null) {
                String[] words = line.toLowerCase().split("\\P{L}+");

                for (String word: words) {
                    if (terms.contains(word)) {
                        occurrences.compute(word, (k,v)-> (v == null) ? 1 : v+1);
                    }
                    totalWords++;
                }

                line = reader.readLine();
            }

            for (String term: this.terms) {
                store.addFileData(term, filename, (double) (occurrences.get(term) != null?occurrences.get(term):0d) / totalWords);
            }


        } catch (IOException e) {
            System.out.println("Could not read file " + path + "/" + filename);
        }
    }

    public void initialLoad() {
        File[] files;

        File f = new File(path);

        files = f.listFiles(pathname -> pathname.isFile());

        for (File file: files) {
            processFile(file.getName());
        }
    }
}
