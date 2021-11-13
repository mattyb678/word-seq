# word-seq

## Requirements

- The program accepts as arguments a list of one or more file paths 
- The program also accepts input on stdin
- The program outputs a list of the 100 most common three word sequences in the text, along with a count of how many times each occurred in the text.
  - For example: `231 - i will not, 116 - i do not, 105 - there is no, 54 - i know not, 37 - i am not …`
- The program ignores punctuation, line endings, and is case insensitive (e.g. “I love\nsandwiches.” should be treated the same as "(I LOVE SANDWICHES!!)")
- The program is capable of processing large files and runs as fast as possible.
- The program should be tested. 