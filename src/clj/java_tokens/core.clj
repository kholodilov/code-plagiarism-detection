(ns java-tokens.core
  (:require [clojure.java.io :as io]
            [org.satta.glob :as glob])
  (:import [java_tokens.java_lexer Java8Lexer]
           [org.antlr.v4.runtime ANTLRInputStream]))

(defn -main [& args]
  (let [input-dirs (glob/glob (first args))
        output-file (second args)]
    (with-open [output (io/writer output-file)]
      (doseq [dir input-dirs]
        (let [all-files (file-seq dir)
              java-files (filter #(.endsWith (.getName %) ".java") all-files)]
          (doseq [[counter file] (map-indexed vector java-files)]
            (if (= 0 (mod counter 1000)) (println "Processing " counter "'s file: " (.getAbsolutePath file)))
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
            (.write output "#END_OF_PROGRAM# ")
          ))
        ))
  ))
