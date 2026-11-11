package com.noobdev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class QuizController {
  private List<String> answerSheet;
  private List<VBox> questionList;

  @FXML
  private ScrollPane rootPane;
  @FXML
  private VBox content;
  @FXML
  private VBox questionPane;
  @FXML
  private Button submitBtn;

  public QuizController() {}

  public void attachQuiz(String []questions, Quiz quiz, Stage stage) throws Exception {
    answerSheet = new ArrayList<>(Collections.nCopies(questions.length, ""));
    questionList = new ArrayList<>();

    for (int index = 0; index < questions.length; index++) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/FXMLQuestion.fxml"));

      VBox root = (VBox)loader.load();
      QuestionController control = loader.getController();
      control.initQuiz(questions[index],
          quiz.getAnswer(questions[index]),
          index, answerSheet, quiz);
      control.buildQuiz();
      questionList.add(root);
    }

    submitBtn.setOnAction(event -> {
      Dialog<Void> dialog = new Dialog<>();

      dialog.setTitle("Results");
      dialog.initOwner(stage);

      Integer score = 0;
      for (int ansNo = 0; ansNo < answerSheet.size(); ansNo++) {
        if (quiz.getAnswer(questions[ansNo]).equals(answerSheet.get(ansNo))) {
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

    questionPane.getChildren().addAll(questionList);
  }
}
