(ns java-tokens.core
  (:require [clojure.java.io :as io]
            [org.satta.glob :as glob]
            [clojure.tools.cli :as cli])
  (:import [java_tokens.java_lexer Java8Lexer]
           [org.antlr.v4.runtime ANTLRInputStream]))

(def cli-options
  [["-i" "--input-dirs glob" "Input dirs glob"]
   ["-o" "--output-file path" "Output path"]
   ["-n" "--name-suffix suffix" "File name suffix" :default ".java"]
   ["-e" "--end-of-program token" "End-of-program token" :default "#END_OF_PROGRAM#"]
   ["-p" "--print-path" "Print file path"]])

(defn -main [& args]
  (let [{:keys [input-dirs output-file name-suffix end-of-program print-path]} (:options (cli/parse-opts args cli-options))]
    (with-open [output (io/writer output-file)]
      (doseq [dir (glob/glob input-dirs)]
        (let [all-files (file-seq dir)
              java-files (filter #(.endsWith (.getName %) name-suffix) all-files)]
          (doseq [[counter file] (map-indexed vector java-files)]
            (if (= 0 (mod counter 1000)) (println "Processing " counter "'s file: " (.getAbsolutePath file)))
            (if print-path (.write output (str (.getAbsolutePath file) " ")))
            (let [lexer (Java8Lexer. (ANTLRInputStream. (slurp file)))
                  tokens (.getAllTokens lexer)]
                (doseq [token tokens]
                  (let [token-text (.getText token)
                        token-type (.getType token)]
                    (if (= token-type Java8Lexer/StringLiteral)
                      (do
                        (.write output "#STRING_LITERAL_START# ")
                        (.write output (subs token-text 1 (- (count token-text) 1)))
                        (.write output " #STRING_LITERAL_END# "))
                      (.write output (str token-text " "))))
                  ))
            (.write output end-of-program)
            (if (not= end-of-program "\n") (.write output " "))
          ))
        ))
  ))
