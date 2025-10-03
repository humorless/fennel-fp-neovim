#!/usr/bin/env bb

(require '[clojure.java.io :as io]
         '[clojure.string :as str])

(def output "all.md")
(def separator "\n%\n")

;; 1. 取得符合 "day*.md" 模式的檔案列表
;;    - file-seq 會遞迴地列出檔案，但我們只取頂層檔案。
;;    - filter 找出檔名符合 "day*.md" 模式的檔案。
(def files
  (->> (io/file ".") ; 從當前目錄開始
       (file-seq)
       (filter #(.isFile %)) ; 只保留檔案
       (filter #(re-matches #"day.*\.md" (.getName %))) ; 找出 day*.md
       (sort) ; 依檔名排序 (可選)
       (map #(.toString %)))) ; 轉成字串路徑列表

;; 2. 準備要寫入的內容
;;    - 使用 map 讀取每個檔案內容，並用 separator 分隔。
(def content
  (str/join
    separator
    (map (fn [file-path]
             (try
               (slurp file-path)
               (catch Exception e
                 (println (str "Error reading file: " file-path))
                 "")))
           files)))

;; 3. 寫入到輸出檔案 (會覆蓋)
(spit output content)

(println (str "Successfully merged " (count files) " files into " output))
