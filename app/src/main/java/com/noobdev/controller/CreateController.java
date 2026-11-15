package com.noobdev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar;
import javafx.stage.Stage;

public class CreateController implements Controller {
  @FXML
  private ScrollPane rootPane;
  @FXML
  private VBox content;
  @FXML
  private VBox questionPane;
  @FXML
  private Label quizTitle;
  @FXML
  private Button saveBtn;
  @FXML
  private Button cancelBtn;
  @FXML
  private Button addBtn;
  @FXML
  private VBox viewQuestionList;

  private Quiz quiz;

  public CreateController() {
  }

  public void createQuiz(String title, String description, Router route) throws Exception {
    quiz = new Quiz(title);
    quiz.setDescription(description);
    quizTitle.setText(title);
    quiz.dumpData();

    saveBtn.setOnAction(event -> {
      quiz.dumpData();
      route.popScene();
    });
    cancelBtn.setOnAction(event -> {
      route.popScene();
    });

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/FXMLSelectPop.fxml"));
    Parent root = loader.load();
    SelectDialogController controller = loader.getController();
    controller.setRoute(route);

    Dialog<Void> dialog = new Dialog<>();
    controller.initDialog(quiz, dialog, this);
    controller.addOption("Create Question", "/xml/FXMLQuestionPop.fxml");
    controller.addOption("Create Terms", "/xml/FXMLTermPop.fxml");

    dialog.setTitle("Add Question");
    dialog.initOwner(route.getStage());

    addBtn.setOnAction(event -> {
      dialog.getDialogPane().setContent(root);
      dialog.showAndWait();
    });
  }

  public void getQuizList() throws Exception {
  }

  public void addQuestion(String question, String answer) {
    quiz.addQuestion(question, answer);
  }

  @Override
  public void refresh() {
    try {
      getQuizList();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
