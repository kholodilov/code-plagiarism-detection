(ns java-tokens.core
  (:import [java_tokens Java8Lexer]
           [org.antlr.v4.runtime ANTLRInputStream]))

(defn -main [& args]
  (let [file (first args)
        lexer (Java8Lexer. (ANTLRInputStream. (slurp file)))]
    (doseq [token (.getAllTokens lexer)]
      (println (.getText token)))
  ))
