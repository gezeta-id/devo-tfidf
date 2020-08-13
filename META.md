_Note:_ This is a sort of _change-log_ plus some additional meta-information meant to give some insight
into the _development process_ to potential evaluators of the exercise. It wouldn't be part of the
project itself out of the context of the take-home test.

 - Project is set up with Maven. A basic `pom.xml` is created with initial configuration, and a
   generic _truth-test_ is set up to verify JUnit is working correctly. Maven wrappers are included
   as a courtesy, just in case.
 - For now, development will be done on the **master** branch.
 - I've decided to use JCommander to parse command line arguments. It's simple enough and works reasonably well.


This will be the general workflow for the application:

When calculating _tf/idf_, only _tf_ needs to go through all the document, because we need to count the number of times each of the terms appears. This is the only part of the program that requires some significant ammount of processing, as the rest can be calculated much more easily.

Also, when _adding new files_ to the analysis, _tf_ for already processed files will _not_ change. The only change happens on the _idf_ factor. With this in mind, I will keep a structure in memory where I'll store _tf_ for each term and for each document (except those with a value of `0`).

Processing new files will be done with the ServiceWatch API and this process will _only_ calculate the _tf_ for the various terms for the new document and store them in the mentioned structure.

Then, at the interval established by the user, I will calculate _idf_, then generalized _tf/idf_ and finally pick the top N results and refresh the output with that.


For tomorrow:

The FileProcessor is done and we get _tf_'s calculated.

List of to-do tasks:

 1. Write the Timed Task that calculates _idf_'s and _tf/idf_'s and outputs the result.
 1. Write the ServiceWatcher that loads up new files into the store.
 1. Get a set of test files. Even better: get two sets.
 1. Add some more tests.
 1. Refactor the helpers package into something a bit better structured.