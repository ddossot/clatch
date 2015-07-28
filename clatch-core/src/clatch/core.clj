(ns clatch.core
  (:require [clojure.string :as str]
            [clatch.spec :as spec]
            [play-clj.core :refer [defscreen* defgame*
                                   stage
                                   clear! update! render! set-screen!]]
            [play-clj.g2d :refer [texture]])
  (:import  [com.badlogic.gdx.utils Logger]))

(defn- get-form
  [forms sym]
  (rest
    (first
      (filter
        #(= (first %) sym)
        forms))))

(defn- log-info
  [screen & msgs]
  (.info
    (get-in screen [:clatch :logger])
    (str/join " " msgs)))

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
  [stage]
  (when-let [backdrops (get-form stage 'backdrops)]
    (backdrops->tex
      backdrops)))

(defn- project->screen
  [project boot-fn]
  (defscreen* (atom {}) (atom [])
    {:on-show
     (fn [screen0 entities]
       (let [logger (Logger. "clatch.core" Logger/INFO)
             project-stage (get-form project 'stage)
             backdrops (collect-backdrops project-stage)
             backdrop (get-in
                        (first backdrops)
                        [:clatch :id])
             screen (update! screen0
                             :renderer (stage)
                             :clatch {:logger logger
                                      :active-backdrop backdrop})]
         (log-info screen
                   "Initial backdrop:" backdrop)
         (log-info screen
                   "Booting application")
         (boot-fn screen)
         (vec backdrops)))

     :on-render
     (fn [screen entities]
       (clear!)
       (render!
         screen
         (renderable-entities screen entities))
       entities)}))

(defn project->game
  [project boot-fn]
  (let [main-screen (project->screen project boot-fn)]
    (defgame*
      {:on-create
       (fn -game-on-create
         [this]
         (set-screen! this main-screen))})))

;; TODO add support for sprites, scripts (including aliases like: forever, repeat-until...)

(defn emit-boot-fn
  [project]
  `(fn -boot-fn [screen#]
     ;; TODO generate real fn out of backdrop/sprite scripts
     (println
       "emitted boot-fn project:" '~project
       "screen:" screen#)))

(defmacro defproject
  "Defines a Clatch project. This should only be called once."
  [name description & project]
  {:pre [(symbol? name)
         (string? description)]}
  (spec/validate-project project)
  `(let [boot-fn# ~(emit-boot-fn project)]
     (defonce ~name
       {:game (project->game '~project boot-fn#)
        :description ~description})))
