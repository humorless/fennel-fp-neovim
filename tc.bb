#!/usr/bin/env bb

(require '[clojure.string :as str])
;; 引入 clojure.string 庫

(def toc-data (atom []))
(def current-h1 (atom nil))
(def h1-index (atom 0))
(def quote-block (atom false))

(defn get-toc [filename]
  (let [
        ;; 嘗試讀取整個檔案內容，若失敗則退出
        content (try (slurp filename)
                     (catch Exception e
                       (println "Error reading file:" (.getMessage e))
                       (System/exit 1)))
        lines (str/split-lines content)
        ;; 定義 H1 和 H2 的正規表達式，確保標題從行首開始
        h1-pattern #"^# (.*)"
        h2-pattern #"^## (.*)"
        quote-pattern #"^```"
        ]

    ;; 遍歷每一行以解析標題
    (doseq [line lines]
      (let [quote-match (re-find quote-pattern line)
            h1-match (re-find h1-pattern line)
            h2-match (re-find h2-pattern line)]
        
        (cond
          quote-match
            (if @quote-block 
             (reset! quote-block false)
             (reset! quote-block true))
          ;; 處理 Head 1
          (and h1-match (not @quote-block)) 
          (let [title (str/trim (second h1-match))
                new-index (swap! h1-index inc)
                ;; 產生 day01.md, day02.md 等檔案名稱
                file-name (format "day%02d.md" new-index)
                h1-entry {:title title :file file-name :h2s []}]
            
            (reset! current-h1 h1-entry)
            (swap! toc-data conj @current-h1))

          ;; 處理 Head 2 (只在有目前的 H1 時才處理)
          (and @current-h1 h2-match)
          (let [h2-title (str/trim (second h2-match))]
            (swap! toc-data update-in [(dec @h1-index) :h2s] conj h2-title)))))

    ;; 輸出 Table of Contents，確保 H2 有兩個空格的縮排
    (doseq [{:keys [title file h2s]} @toc-data]
      (println (str "* [" title  "](./" file ")"))
      (doseq [h2-title h2s]
        (println (str "  * " h2-title)))    
    )))

;; Babashka 程式進入點
(let [args *command-line-args*]
  (if (empty? args)
    (println "Usage: bb a.bb <markdown-file>")
    (get-toc (first args))))
