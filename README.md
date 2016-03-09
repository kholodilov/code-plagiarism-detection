# java-tokens

A Clojure app for parsing and tokenizing java source code.

## Usage

lein antlr

lein run path/to/dir_with_java_files output_file

It also supports glob expressions for path:

lein run "path/to/dir_with_java_files/{subdir1,subdir2,subdir3}" output_file

## License

Copyright © 2016 Dmitry Kholodilov

Distributed under BSD license.
