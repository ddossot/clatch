(def gdx-version "1.6.0")

(defproject net.dossot.clatch/clatch-desktop-example "1.0.0-SNAPSHOT"
  :description "Clatch desktop example."
  :url "https://www.github.com/ddossot/clatch"
  :license {:name "The MIT License (MIT)"
            :url "http://opensource.org/licenses/MIT"
            :comments "Copyright (c) 2015 David Dossot. Media files come from https://github.com/LLK/Scratch_1.4 and are licensed under the Creative Commons Attribution-ShareAlike 3.0 Generic (CC BY-SA 3.0) license."}

  :plugins [[lein-modules "0.3.11"]]

  :dependencies
  [
   [com.badlogicgames.gdx/gdx ~gdx-version]
   [com.badlogicgames.gdx/gdx-backend-lwjgl ~gdx-version]
   [com.badlogicgames.gdx/gdx-box2d ~gdx-version]
   [com.badlogicgames.gdx/gdx-box2d-platform ~gdx-version :classifier "natives-desktop"]
   [com.badlogicgames.gdx/gdx-bullet ~gdx-version]
   [com.badlogicgames.gdx/gdx-bullet-platform ~gdx-version :classifier "natives-desktop"]
   [com.badlogicgames.gdx/gdx-platform ~gdx-version :classifier "natives-desktop"]
   [net.dossot.clatch/clatch-core :version]
  ]

  :aot [net.dossot.clatch.desktop-example.core]
  :main net.dossot.clatch.desktop-example.core)
