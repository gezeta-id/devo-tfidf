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


 - There's a `samples` folder with a few movie scripts that can be used as a test set.
 
----

The main task is _complete_ now. It's _far_ from perfect though.

Now I _should_ spend a little more time cleaning up and refactoring a couple of things:

 - There's _too much_ going on in the Main class. Output should _really_ be extracted to its own class. The file watcher probably too. Both of these are easy tasks.
 - I **do** need more extensive testing. The Store clearly needs more testing. The calculators too.

In a _real-world_ situation I would certainly take on these tasks. Given that I've already spent a **fair** amount of time developing this for a test. I will leave them pending.


----