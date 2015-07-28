(ns clatch.desktop
  (:import  [com.badlogic.gdx Game]
            [com.badlogic.gdx.backends.lwjgl LwjglApplication]
            [org.lwjgl.input Keyboard]))

(defn start
  "Runs a Clatch project on a good ol' computer."
  [project]
  (LwjglApplication.
    (:game project)
    (:description project)
    480 360)
  (Keyboard/enableRepeatEvents true))
