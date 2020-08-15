package net.tinselcity.devo;

import com.beust.jcommander.*;
import net.tinselcity.devo.helpers.Config;
import net.tinselcity.devo.helpers.Store;
import net.tinselcity.devo.process.FileProcessor;
import net.tinselcity.devo.process.TfidfCalculator;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.System.exit;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;


public class Main {
    private Config config;

    public Main(Config config) {
        this.config = config;
    }

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

    // Just to avoid some casting exception warnings we wrap this in a method ans suppress
    @SuppressWarnings("unchecked")
    static <T> WatchEvent<T> cast(WatchEvent<?> event) {
        return (WatchEvent<T>)event;
    }

    // TODO: All the output related methods would be better in a separate class
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


    // TODO: All the output related methods would be better in a separate class
    private static void output(List<Map.Entry<String, Double>> results) {
        clearConsole();
        System.out.println("Results updated at: " + new Date()+"\n");
        results.forEach((e)->System.out.printf("%s -> %.6f \n", e.getKey(), e.getValue()));
        System.out.println("");
    }

    private void watchDir(Path p, FileProcessor fp, int period) throws IOException {

        WatchService ws = FileSystems.getDefault().newWatchService();
        p.register(ws, ENTRY_CREATE);

        for (;;) {
            WatchKey wkey;
            try {
                wkey = ws.take();
            } catch (InterruptedException e) {
                return;
            }

            for (WatchEvent<?> event: wkey.pollEvents()) {
                if (event.kind() != ENTRY_CREATE) continue; // Just in case
                WatchEvent<Path> ev = cast(event);
                Path name = ev.context();
                if (Files.isDirectory(p.resolve(name), LinkOption.NOFOLLOW_LINKS)) return; // We ignore directories

                fp.processFile(name.toString());
            }
            wkey.reset();
        }

    }

    private void run() {

        Store store = new Store(config.terms.toLowerCase());

        FileProcessor fp = new FileProcessor(config.terms.toLowerCase(), config.directory, store);

        fp.initialLoad();

        output(TfidfCalculator.calculateTop(config.resultsToShow, store));

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(()->output(TfidfCalculator.calculateTop(config.resultsToShow, store)), 0, config.period, TimeUnit.SECONDS);

        try {
            watchDir(Paths.get(config.directory), fp, config.period);
        } catch (IOException e) { }

    }
}
