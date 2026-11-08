package com.noobdev;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.Group;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.geometry.Pos;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;
import javafx.scene.layout.Region;
import javafx.scene.control.Button;
import java.util.List;
import java.util.ArrayList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class App extends Application
{ 
  @Override()
  public void start(Stage stage) {
    Quiz quiz = new Quiz(new Corpus());
    quiz.addTerm("hello", "it's a form of greeting");
    quiz.addTerm("hello1", "it's a form of greeting");
    quiz.addTerm("hello2", "it's a form");
    quiz.addTerm("hello3", "it's not form");
    StackPane root = new StackPane();
    VBox box = new VBox(5);
    quiz.addQuestion(quiz.getDefinition("hello2"), "hello2");
    List<String> option = quiz.getSimilarTerm("hello2", 4);

    Label result = new Label();
    Label question = new Label();
    result.setText("Answer: ");
    question.setText(quiz.getDefinition("hello2"));

    Button a = new Button(option.get(2));
    Button b = new Button(option.get(1));
    Button c = new Button(option.get(3));
    Button d = new Button(option.get(0));

    a.setOnAction(e -> {
      result.setText(quiz.getDefinition(a.getText()));
    });
    b.setOnAction(e -> {
      result.setText(quiz.getDefinition(b.getText()));
    });
    c.setOnAction(e -> {
      result.setText(quiz.getDefinition(c.getText()));
    });
    d.setOnAction(e -> {
      result.setText(quiz.getDefinition(d.getText()));
    });
    box.getChildren().addAll(
      question,
      result,
      a, b, c, d
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
