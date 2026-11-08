package com.noobdev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.scene.layout.Region;

public class App extends Application
{ 
  @Override()
  public void start(Stage stage) {
    Quiz quiz = new Quiz(new Corpus());
    quiz.addTerm("hello", "it's a form of greeting");
    StackPane root = new StackPane();
    VBox box = new VBox(5);
    quiz.addQuestion("Hatdog this are", "hello");
    box.getChildren().addAll(
      new Label(quiz.getDefinition("hello")),
      new Label(quiz.getAnswer("Hatdog this are"))
    );
    box.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
    root.getChildren().add(box);
    root.setAlignment(Pos.CENTER);
    Scene scene = new Scene(root, 640, 350);
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
