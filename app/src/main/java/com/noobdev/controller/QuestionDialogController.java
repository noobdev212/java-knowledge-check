package com.noobdev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;

public class QuestionDialogController implements DialogController {
  @FXML
  private VBox rootPane;
  @FXML
  private TextField questionTF;
  @FXML
  private TextField answerTF;
  @FXML
  private Button createBtn;
  @FXML
  private Button cancelBtn;

  private Quiz quiz;
  private Dialog<?> dialog;
  private Controller controller;

  public QuestionDialogController() {}

  public void initDialog(Quiz quiz, Dialog<?> dialog, Controller controller) {
    this.quiz = quiz;
    this.dialog = dialog;
    this.controller = controller;
  }

  @FXML
  public void createQuestion() {
    quiz.addQuestion(questionTF.getText(), answerTF.getText());
    refresh();
  }

  @FXML
  public void closeDialog() {
    refresh();
    cancelBtn.getScene().getWindow().hide();
  }

  @Override
  public void refresh() {
    questionTF.setText("");
    answerTF.setText("");
  }

}
