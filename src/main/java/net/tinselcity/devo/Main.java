package net.tinselcity.devo;

import com.beust.jcommander.*;
import net.tinselcity.devo.helpers.Config;
import net.tinselcity.devo.helpers.Store;
import net.tinselcity.devo.process.FileProcessor;
import net.tinselcity.devo.process.TfidfCalculator;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;


public class Main {
    private Config config;

    public static void main( String[] args ) {
        Config c = new Config();

        try {
            JCommander.newBuilder()
                    .addObject(c)
                    .build()
                    .parse(args);

        } catch (ParameterException pe) {
            System.out.println("\n"+pe.getMessage()+"\n");

            //Todo: Create a simple wrapper script and change this to reflect that name
            pe.getJCommander().setProgramName("java -jar target/tfidf-1.0-SNAPSHOT.jar");
            pe.getJCommander().usage();

            exit(-1);
        }

        Main main = new Main(c);
        main.run();
    }

    public Main(Config config) {
        this.config = config;
    }

    private final static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) { }
    }

    private static void output(List<Map.Entry<String, Double>> results) {
        clearConsole();
        results.forEach((e)->System.out.printf("%s -> %.6f \n", e.getKey(), e.getValue()));
    }

    private void run() {

        Store store = new Store(config.terms.toLowerCase());

        FileProcessor fp = new FileProcessor(config.terms.toLowerCase(), config.directory, store);

        fp.initialLoad();

        output(TfidfCalculator.calculateTop(config.resultsToShow, store));

        //Todo:
        /*
         * 1. Set up the main output loop that calculates and prints results every config.period seconds
         * 2. Set up the ServiceWatcher that processes new files into the store
         */
    }
}
