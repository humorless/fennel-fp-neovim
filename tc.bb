#!/usr/bin/env bb

(require '[clojure.string :as str]
         '[clojure.java.io :as io])

(let [filename (first *command-line-args*)]
  (if-not filename
    (do
      (println "Usage: ./toc.bb <markdown-file>")
      (System/exit 1))
    (let [day-counter (atom 0)
          quote-block (atom false)]
      (with-open [rdr (io/reader filename)]
        (doseq [line (line-seq rdr)]
          (cond
            (str/starts-with? line "```")
            (swap! quote-block not)

            (and (not @quote-block) (str/starts-with? line "# "))
            (let [title (str/trim (subs line 2))
                  day (swap! day-counter inc)
                  day-str (format "%02d" day)]
              (println (str "* [" title "](./day" day-str ".md)")))

            (and (not @quote-block) (str/starts-with? line "## "))
            (let [title (str/trim (subs line 3))]
              (println (str "  * " title)))

            :else nil))))))