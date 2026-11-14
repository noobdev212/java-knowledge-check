package com.noobdev;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

import java.util.Stack;

public class Router {
  private static Stack<Parent> scenes;
  private static Stage stage;

  public Router(Stage stage) {
    this.stage = stage;
    scenes = new Stack<>();
    stage.setScene(new Scene(new VBox(0), 600, 400));
  }

  public void pushScene(Parent scene) {
    scenes.push(scene);
    renderScene();
  }

  public void popScene() {
    scenes.pop();
    renderScene();
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void renderScene() {
    stage.getScene().setRoot(scenes.peek());
    stage.show();
  }

  public Stage getStage() {
    return stage;
  }
}
