package net.tinselcity.devo;

import com.beust.jcommander.*;
import net.tinselcity.devo.helpers.Config;


public class Main {
    private Config config;

    public static void main( String[] args ) {
        Config c = new Config();

        JCommander.newBuilder()
            .addObject(c)
            .build()
            .parse(args);

        Main main = new Main(c);
        main.run();
    }

    public Main(Config config) {
        this.config = config;
    }

    private void run() {

        // For now, we just output the arguments received

        System.out.println( "Received arguments: " );
        System.out.printf( "   Results to be shown: %d\n", config.resultsToShow );
        System.out.printf( "   Refresh every: %d seconds\n", config.period );
        System.out.printf( "   Directory to watch: %s\n", config.directory );
        System.out.printf( "   Terms to analyze: %s\n\n", config.terms );
    }
}
