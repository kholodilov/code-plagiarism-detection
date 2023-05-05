# code-plagiarism-detection

A Clojure app for parsing and tokenizing java source code. Doc2vec embeddings are build for parsed code, to allow comparison and plagiarism detection.

## Usage

lein antlr

lein run -i path/to/dir_with_java_files -o output_file

It also supports glob expressions for path:

lein run -i "path/to/dir_with_java_files/{subdir1,subdir2,subdir3}" -o output_file

For Doc2vec part please refer to doc2vec-plagiarism.ipynb.

## License

Copyright © 2016 Dmitry Kholodilov

Distributed under BSD license.
