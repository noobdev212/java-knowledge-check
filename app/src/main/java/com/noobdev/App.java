package com.noobdev;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.Parent;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Font;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.paint.Color;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class App extends Application
{
  private static Quiz quiz;

  @Override()
  public void start(Stage stage) throws Exception {
    String []questions = {
      "Who created c++?",
      "it's a form",
      "it's a form",
      "it's a form",
      "it's a form",
      "it's a form",
      "it's a form",
      "it's a form",
      "it's a form",
      "it's a form",
      "it's a form",
      "Testing the longest text ever so don't sweat it like sweating it, I like turtles alright then. So how is your weekend, Great? alright so when would this finish. I don't know I haven't written any restriction for text length so I have the freedom with no free will."
    };

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/FXMLQuiz.fxml"));
    ScrollPane root = loader.load();

    QuizController controller = loader.getController();
    controller.attachQuiz(questions, quiz, stage);
    Scene scene = new Scene(root, 600, 400);
    stage.setTitle("JKC");
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    if (Utility.resExist("config/default.yaml")) {
      quiz = new Quiz("config/default.yaml");
    }
    else {
      quiz = new Quiz(new Corpus());
    }
    launch();
  }
}
