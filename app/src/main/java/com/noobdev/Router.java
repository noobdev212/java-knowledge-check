package com.noobdev;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.Parent;

import java.util.Stack;

public class Router {
  private static Stack<Parent> scenes;
  private static Stack<Controller> controllers;
  private static Stage stage;

  public Router(Stage stage) {
    this.stage = stage;
    scenes = new Stack<>();
    controllers = new Stack<>();
    stage.setScene(new Scene(new VBox(0), 600, 400));
  }

  public void pushScene(Parent scene, Controller controllers) {
    scenes.push(scene);
    this.controllers.push(controllers);
    renderScene();
  }

  public void popScene() {
    scenes.pop();
    controllers.pop();
    refresh();
    renderScene();
  }

  public void refresh() {
    if (!controllers.isEmpty()) {
      controllers.peek().refresh();
    }
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
