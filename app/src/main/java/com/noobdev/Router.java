package com.noobdev;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Stack;

public class Router {
  private static Stack<Scene> scenes;
  private static Stage stage;

  public Router(Stage stage) {
    this.stage = stage;
    scenes = new Stack<>();
  }

  public void pushScene(Scene scene) {
    scenes.push(scene);
    renderScene();
  }

  public void popScene() {
    scenes.pop();
    if (!scenes.isEmpty()) {
      renderScene();
    }
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void renderScene() {
    stage.setScene(scenes.peek());
    stage.show();
  }

  public Stage getStage() {
    return stage;
  }
}
