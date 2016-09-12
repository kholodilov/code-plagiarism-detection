# java-tokens

A Clojure app for parsing and tokenizing java source code.

## Usage

lein antlr

lein run -i path/to/dir_with_java_files -o output_file

It also supports glob expressions for path:

lein run -i "path/to/dir_with_java_files/{subdir1,subdir2,subdir3}" -o output_file

## License

Copyright © 2016 Dmitry Kholodilov

Distributed under BSD license.
