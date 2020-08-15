# devo-tfidf

This is a small utility to monitor text files and perform _tf/idf_ analysis on them on some specific terms.

## Requirements

 - Java 1.8+
 - Maven 3.3.x.

## Building

This is a Maven application. It's configured to be assembled into a _single jar file_.

To build the application, run the `assembly:assembly` goal in the application's directory:

```bash
> mvn clean assembly:assembly
```

## Usage

Once compiled and packaged, a `tfidf.jar` file containing the application is generated in the `target` folder. This is an executable JAR file.

Run it from the command line:

```bash
> java -jar target/tfidf.jar -n 8 -i 300 -d samples -t "death hope software"
```

`tfidf.jar` takes the following parameters:

  - `-d <directory>` or `--dir <directory>`
      Directory containing the documents to be processed. **Required**
  - `-i <number>` or `--refreshInterval <number>`
      Refresh interval, in seconds. **Optional**. Default: 300.
  - `-n <number of results>` or `--show <number of results>`
      Top n results to be shown. **Optional**. Default: 5.
  - `-t "term1 term2 ..."` or `--terms "term1 term2 ..."`
      Set TT of terms to be analyzed, space separated. **Required**

Once started the application will stay running until interrupted, showing the most current results.

It processes new files as they appear in the watched directory, but results are only updated respecting the `--refreshInterval` option.

## Samples included

The `samples` folder contains a number of random movie scripts which can be used as document corpus for testing purposes.
