package com.noobdev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.application.Platform;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class QuestionController {
  private String answer;
  private String question;
  private List<String> answerSheet;
  private Integer sheetNo;
  private Quiz quiz;

  @FXML
  private VBox rootPane;
  @FXML
  private Label questionLbl;
  @FXML
  private GridPane optionPane;

  public QuestionController() {}

  public void initQuiz(
      String question, 
      String answer, 
      Integer sheetNo, 
      List<String>answerSheet, 
      Quiz quiz) {
    this.quiz = quiz;
    this.answer = answer;
    this.question = question;
    this.sheetNo = sheetNo;
    this.answerSheet = answerSheet;
  }

  public void buildQuiz() throws Exception {
    List<String> answers = quiz.getSimilarTerm(answer, 4);
    List<Button> options = new ArrayList<>();

    for (int i = 0; i < answers.size(); i++) {
      final int index = i; // for lambda sake

      Button optionBtn = (Button)FXMLLoader.load(getClass().getResource("/xml/FXMLOptionBtn.fxml"));
      optionBtn.setText(answers.get(index));
      GridPane.setHgrow(optionBtn, Priority.ALWAYS);

      optionBtn.setOnAction(event -> {
        String prevAnswer = answerSheet.get(sheetNo);
        if (!prevAnswer.equals(optionBtn.getText())) {
          // unselect previous pressed button
          for (Button button: options) {
            if (button.getText().equals(prevAnswer)) {
              button.getStyleClass().remove("active-color");
            }
          }
        }
        answerSheet.set(sheetNo, answers.get(index));
        optionBtn.getStyleClass().add("active-color");
      });

      options.add(optionBtn);
      optionPane.add(optionBtn, index%2, index/2);
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

    questionLbl.setText((sheetNo+1) + ". " + question);
  }
}
