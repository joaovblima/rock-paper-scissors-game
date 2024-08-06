(ns rock-paper-scissors.game)

(defn -main [& args]
  (println "Welcome to the Rock, Paper, Scissors championship")
  (loop []
    (println "Ready to play? Type y, n or q to quit.")
    (let [line (read-line)]
      (cond
        (= "y" line)
        (do
          (println "Let's start the game")
          (play-game)
          (recur))

          (= "n" line)
          (do
            (println "Your're not ready? Then I'll wait.")
            (Thread/sleep 5000)
            (recur))

          (= "q" line)
          (println "See you next time!")

          :else
          (do
            (println "I don't undesrtand yout input.")
            (recur))))))

(def ->move {"r" :rock
             "p" :paper
             "s" :scissors})

(def moves [:rock :paper :scissors])

(def beats #{[:rock :scissors]
             [:scissors :paper]
             [:paper :rock]})

(defn play-round [player-move]
  (let [computer-move :rock]
    {:player player-move
     :computer computer-move
     :result (cond
               (= player-move computer-move)
               :draw

               (contains? beats [player-move computer-move])
               :win

               :else
               :lose)}))

(defn play-game []
  (loop [score {:win 0 :draw 0 :lose 0}]
    (println (str "Wins: " (:win score) ", Losses: " (:lose score) ", Draws: " (:draw score)))
    (println "Choose (r)ock, (p)aper, (s)cissors, or (q)uit.")
    (let [line (read-line)
          move (get ->move line)]
      (cond
        (= line "q")
        (println "Quitting game")

        (nil? move)
        (do
          (println line "is an invalid move.")
          (recur score))
        :else
        (let [result (play-round move)
              rstring (cond
                        (= :win (:result result))
                        "you win!"
                        (= :lose (:result result))
                        "you lose!"
                        (= :draw (:result result))
                        "it's a draw")]
          (println (str "You: " (name(:player result)) ", Computer: " (name(:computer result)) ", " rstring))
          (recur (update score (:result result) inc)))))))

(comment
  (-main)
  (play-game))
