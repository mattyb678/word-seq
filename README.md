# word-seq

## How to run

`./mvnw package` will create a runnable jar which can be run with `java -jar target/word-seq.jar <file> <file2>`.
You can also specify the `-l` option to vary the number of sequences that are printed, `java -jar target/word-seq.jar -l 10 <file>`.


I also created a native image using GraalVM, you can run that, on macOS, by running `bin/word-seq <file>`

### Test files

There are a number of test files included. The first 7 are basic tests, files 8-11 are larger files, books and a generated file.
They are located inside the `src/main/resources` directory.

## Requirements

- The program accepts as arguments a list of one or more file paths 
- The program also accepts input on stdin
- The program outputs a list of the 100 most common three word sequences in the text, along with a count of how many times each occurred in the text.
  - For example: `231 - i will not, 116 - i do not, 105 - there is no, 54 - i know not, 37 - i am not …`
- The program ignores punctuation, line endings, and is case insensitive (e.g. “I love\nsandwiches.” should be treated the same as "(I LOVE SANDWICHES!!)")
- The program is capable of processing large files and runs as fast as possible.
- The program should be tested. 