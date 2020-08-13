package net.tinselcity.devo;

import com.beust.jcommander.*;
import net.tinselcity.devo.helpers.Config;
import net.tinselcity.devo.helpers.Store;
import net.tinselcity.devo.process.FileProcessor;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

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

    private void run() {

        Store store = new Store(config.terms.toLowerCase());

        FileProcessor fp = new FileProcessor(config.terms.toLowerCase(), config.directory, store);

        fp.initialLoad();

        //Todo:
        /*
         * 1. Set up the main output loop that calculates and prints results every config.period seconds
         * 2. Set up the ServiceWatcher that processes new files into the store
         */
    }
}
