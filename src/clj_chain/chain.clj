(ns clj-chain.chain
    (:gen-class))

(defn block [index pHash timestamp data hash]
    {:index index 
    :previous pHash 
    :timestamp timestamp
    :data data
    :hash hash})

(defn- genesis []
    (block 0 "0" 1465154705 "my genesis block!!" "816534932c2b7154836da6afc367695e6337db8a921823784c14378abed4f7d7"))
          
(def chain (atom {:chain (seq (genesis))}))

(defn get-latest-block []
    (last (:chain @chain))
      
(defn calc-hash [index pHash timestamp data]
    (let [md (java.security.MessageDigest/getInstance "SHA-256")]
        (str (.digest md (str index pHash timestamp data)))))
      
(defn generate-next-block [blockData latestBlock]
    (let [index (inc (:index latestBlock))
        timestamp (/ (.getTime (java.util.Date.)) 100)
        pHash (:previous latestBlock)
        hash (calc-hash index pHash timestamp blockData)]
        (block index pHash timestamp blockData hash)))
      
(defn is-valid-new-block [newBlock previousBlock]
    (cond (not (= (inc (:index previousBlock)) (:index newBlock))) (do (println "invalid index") false)
        (not (= (:hash previousBlock) (:previous newBlock))) (do (println "invalid prevoius hash") false)
        (not (= (calc-hash newBlock) (:hash newBlock) )) (do (println "invalid hash") false)
        :else true))

(defn add-block [newBlock]
    (when (is-valid-new-block newBlock (get-latest-block))
        ()))

(defn replace-chain [newBlocks]
    (if 
        (and (is-valid-new-block newBlocks chain) (> (count newBlocks) (count chain)))
        (do 
            (print "received blockain is valid. replace current blockchain")
            (reset! chain newBlocks))
        (println "received blockchain is invalid")))