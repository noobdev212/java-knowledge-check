package com.noobdev;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.geometry.Pos;
import javafx.scene.layout.Priority;
import javafx.scene.control.Label;
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
  public void start(Stage stage) {
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

    List<String> sheet = new ArrayList<>(Collections.nCopies(questions.length, ""));

    List<VBox> qp = new ArrayList<>();
    for (int index = 0; index < questions.length; index++) {
      qp.add(questionPane(questions[index], sheet, index));
      qp.get(index).setStyle("""
          -fx-background-color: #bebaba1a;
          -fx-padding: 3em 6em;
          -fx-margin: 4em 0em;
          -fx-background-radius: 5;
      """);
      qp.get(index).setMaxWidth(1000);
    }

    Button submitBtn = new Button("Submit");
    submitBtn.setStyle("""
        -fx-background-color: #111613;
        -fx-text-fill: #cccccc;
        -fx-font: 20px Arial;
        -fx-padding: 0.5em 3em;
    """);
    submitBtn.setOnAction(event -> {
      Dialog<Void> dialog = new Dialog<>();

      dialog.setTitle("Results");
      dialog.initOwner(stage);

      Integer score = 0;
      for (int ansNo = 0; ansNo < sheet.size(); ansNo++) {
        if (quiz.getAnswer(questions[ansNo]).equals(sheet.get(ansNo))) {
          score++;
        }
      }

      dialog.getDialogPane().setContent(
          new Label("Score: " + score)
      );

      dialog.getDialogPane()
            .getButtonTypes()
            .add(ButtonType.OK);

      dialog.showAndWait();
    });

    VBox rootQuestion = new VBox(15);
    rootQuestion.getChildren().addAll(qp);
    rootQuestion.getChildren().add(submitBtn);
    rootQuestion.setPadding(new Insets(20));
    rootQuestion.setAlignment(Pos.CENTER);

    ScrollPane root = new ScrollPane();
    root.setContent(rootQuestion);
    root.setFitToWidth(true);
    root.setPadding(new Insets(8));

    Scene scene = new Scene(root, 600, 400);
    stage.setTitle("JKC");
    stage.setScene(scene);
    stage.show();
  }

  private static void initQuestion() {
    try {
      quiz.addQuestion("Testing the longest text ever so don't sweat it like sweating it, I like turtles alright then. So how is your weekend, Great? alright so when would this finish. I don't know I haven't written any restriction for text length so I have the freedom with no free will.", "Bjarne Stroustrup");
      quiz.dumpData("default.yaml");
    } catch (Exception e) {
      System.out.println("already initialized or terms have already existed");
    }
  }

  private VBox questionPane(String question, List<String> answerSheet, Integer answerNo) {
    GridPane grid = new GridPane();
    grid.setHgap(4);
    grid.setVgap(4);

    ColumnConstraints col1 = new ColumnConstraints();
    col1.setPercentWidth(50);

    ColumnConstraints col2 = new ColumnConstraints();
    col2.setPercentWidth(50);

    grid.getColumnConstraints().addAll(col1, col2);

    List<String> answers = quiz.getSimilarTerm(quiz.getAnswer(question), 4);
    List<Button> options = new ArrayList<>();
    for (int i = 0; i < answers.size(); i++) {
      final int index = i; // for lambda sake

      options.add(new Button(answers.get(index)));
      options.get(index).setStyle("-fx-background-color: #555a4c40");
      options.get(index).setOnAction(event -> {
        String prevAnswer = answerSheet.get(answerNo);
        if (!prevAnswer.equals("")) {
          for (Button button: options) {
            if (button.getText().equals(prevAnswer)) {
                button.setStyle("-fx-background-color: #555a4c40");
            }
          }
        }
        answerSheet.set(answerNo, answers.get(index));
        options.get(index).setStyle("-fx-background-color: #b9c0d5");
      });

      options.get(index).setWrapText(true);
      options.get(index).setMaxWidth(Double.MAX_VALUE);
      options.get(index).setAlignment(Pos.CENTER_LEFT);
      options.get(index).setPadding(new Insets(20));

      grid.add(options.get(index), index%2, index/2);
      GridPane.setHgrow(options.get(index), Priority.ALWAYS);
    }

    // make sure to match all button heights during render
    Platform.runLater(() -> {
      double tallest = 0;
      for (Button btn : options) {
        double height = btn.prefHeight(btn.getWidth());
        if (height > tallest) {
          tallest = height;
        }
      }

      for (Button btn : options) {
        btn.setPrefHeight(tallest);
      }
    });

    Label paragraph = new Label((answerNo+1) + ". " + question);
    paragraph.setFont(new Font(18d));
    paragraph.setWrapText(true);

    return new VBox(8, paragraph, grid);
  }

  public static void main(String[] args) {
    if (Utility.resExist("default.yaml")) {
      quiz = new Quiz("default.yaml");
    }
    else {
      quiz = new Quiz(new Corpus());
    }
    initQuestion();
    launch();
  }
}
