(ns clatch.core
  (:require [clojure.string :as str]
            [clatch.spec :as spec]
            [clatch.utils :refer :all]
            [play-clj.core :refer [defscreen* defgame*
                                   stage
                                   clear! update! render! set-screen!]]
            [play-clj.g2d :refer [texture]])
  (:import  [com.badlogic.gdx.utils Logger]))

(defn- log-info
  [screen & msgs]
  (.info
    (get-in screen [:clatch :logger])
    (str/join " " msgs)))

(defn project->boot-fn
  [project]
  (let [project-stage (get-form project 'stage)
        stage-scripts (get-form project-stage 'scripts)]
    `(fn -boot-fn [~'event-bus]
       (do
         ~@stage-scripts))))

(defn- skip-render?
  [screen entity]
  (let [{{{active-backdrop :active-backdrop} :stage} :clatch} screen
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

(defn- stage-tick
  [screen]
  (let [{{{stage-stream :stream} :stage} :clatch} screen]
    (when-let [stage-message (pop-stage-message! stage-stream)]
      ;; TODO mutate screen state
      (println "stage message:" stage-message))
    screen))

(defn- project->screen
  [project boot-fn]
  (defscreen* (atom {}) (atom [])
    {:on-show
     (fn [screen0 entities]
       (let [logger (Logger. "clatch.core" Logger/INFO)
             event-bus (make-event-bus)
             stage-stream (make-stage-stream event-bus)
             project-stage (get-form project 'stage)
             backdrops (collect-backdrops project-stage)
             backdrop-ids (map
                            #(get-in % [:clatch :id])
                            backdrops)
             active-backdrop (first backdrop-ids)
             screen (update! screen0
                             :renderer (stage)
                             :clatch {:logger logger
                                      :event-bus event-bus
                                      :stage {:active-backdrop active-backdrop
                                              :backdrop-ids backdrop-ids
                                              :stream stage-stream}})]
         (log-info screen
           "Loaded backdrop IDs:" (pr-str backdrop-ids)
           "- Initial backdrop ID:" active-backdrop)
         (log-info screen
           "Booting application")
         (boot-fn event-bus)
         (vec backdrops)))

     :on-render
     (fn [screen0 entities]
       (let [screen (stage-tick screen0)]
         (clear!)
         (render!
           screen
           (renderable-entities screen entities))
         entities))}))

(defn project->game
  [project boot-fn]
  (let [main-screen (project->screen project boot-fn)]
    (defgame*
      {:on-create
       (fn -game-on-create
         [this]
         (set-screen! this main-screen))})))

;; TODO add support for sprites, scripts (including aliases like: forever, repeat-until...)

(defmacro defproject
  "Defines a Clatch project. This should only be called once."
  [name description & project]
  {:pre [(symbol? name)
         (string? description)]}
  (spec/validate-project project)
  `(let [boot-fn# ~(project->boot-fn project)]
     (defonce ~name
       {:game (project->game '~project boot-fn#)
        :description ~description})))
