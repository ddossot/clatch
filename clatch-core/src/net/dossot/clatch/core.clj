(ns net.dossot.clatch.core
  (:require [play-clj.core :refer [defscreen* defgame*
                                   stage
                                   clear! update! render! set-screen!]]
            [play-clj.g2d :refer [texture]]))

(defn- skip-render?
  [screen entity]
  (let [{{active-backdrop :active-backdrop} :clatch} screen
        {{entity-type :type entity-id :id} :clatch} entity]
    (cond
      (and (= entity-type :backdrop) (not= entity-id active-backdrop)) true
      :else false)))

(defn- renderable-entities
  [screen entities]
  (remove
    (partial skip-render? screen)
    entities))

(defn- backdrops->tex
  [backdrops]
  (assert
    (even? (count backdrops))
    "An even number of backdrops :key/file pairs must be provided")
  (map
    (fn -backdrop->tex
      [[id file]]
      (assert
        (keyword? id)
        "Backdrop identifier must be a :keyword")
      ;; TODO assert file existence otherwise NPE :(
      (assoc (texture file)
             :clatch {:type :backdrop
                      :id id}))
    (partition 2 backdrops)))

(defn- collect-backdrops
  [specs]
  (reduce
    (fn -backdrop-collector
      [acc [sym & args]]
      (if
        (= sym 'backdrops)
        (concat acc (backdrops->tex args))
        acc))
    []
    specs))

(defn- specs->screen
  [specs]
  (defscreen* (atom {}) (atom [])
    {:on-show
     (fn [screen entities]
       (let [backdrops (collect-backdrops specs)
             backdrop (get-in
                        (first backdrops)
                        [:clatch :id])]
         (println
           "Initial backdrop:"
           backdrop)
         (update! screen
                  :renderer (stage)
                  :clatch {:active-backdrop backdrop})
         (vec backdrops)))

     :on-render
     (fn [screen entities]
       (clear!)
       (render!
         screen
         (renderable-entities screen entities))
       entities)}))

(defn specs->game
  [specs]
  (let [main-screen (specs->screen specs)]
    (defgame*
      {:on-create
       (fn -game-on-create
         [this]
         (set-screen! this main-screen))})))

;; TODO add support for sprites, scripts (including aliases like: forever, repeat-until...)

(defmacro defproject
  "Defines a Clatch project. This should only be called once."
  [name description & specs]
  {:pre [(symbol? name)
         (string? description)]}
  `(defonce ~name
     {:game (specs->game '~specs)
      :description ~description}))
