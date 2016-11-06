(defproject kholodilov/java-tokens "0.1.0-SNAPSHOT"
  :source-paths ["src/clj"]
  :java-source-paths ["gen-src"]
  :antlr-src-dir "src/antlr"
  :antlr-dest-dir "gen-src"
  :prep-tasks ["javac" "compile"]            
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.antlr/antlr4-runtime "4.5.1"]
                 [clj-glob "1.0.0"]
                 [org.clojure/tools.cli "0.3.1"]
                 [digest "1.4.5"]]
  :plugins [[lein-antlr "0.3.0"]]
  :hooks [leiningen.antlr]
  :main java-tokens.core)
