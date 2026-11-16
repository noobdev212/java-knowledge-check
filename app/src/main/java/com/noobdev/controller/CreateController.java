package com.noobdev;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

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
  private Router route;

  public CreateController() {
  }

  public void createQuiz(String title, String description, Router route) throws Exception {
    quiz = new Quiz(title);
    quiz.setDescription(description);
    quizTitle.setText(title);
    this.route = route;
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
    String[] questions = quiz.getQuestions();
    List<String> answerSheet = Arrays.stream(questions).map(q -> quiz.getAnswer(q)).collect(Collectors.toList());
    List<StackPane> questionList = new ArrayList<>();

    // populate option buttons to grid
    for (int index = 0; index < questions.length; index++) {
      final int i = index;
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/FXMLQuestion.fxml"));

      VBox root = (VBox)loader.load();
      QuestionController control = loader.getController();
      control.initQuiz(questions[index],
          quiz.getAnswer(questions[index]),
          index, answerSheet, quiz);
      control.buildQuiz();
      FXMLLoader icon = new FXMLLoader(getClass().getResource("/xml/FXMLEditIcon.fxml"));
      Button editBtn = (Button)icon.load();
      FXMLLoader load = new FXMLLoader(getClass().getResource("/xml/FXMLQuestionPop.fxml"));
      Parent diag = load.load();

      QuestionDialogController cont = load.getController();
      Dialog<Void> dialog = new Dialog<>();
      cont.initDialog(quiz, dialog, this);

      dialog.initOwner(route.getStage());
      editBtn.setOnAction(event -> {
        cont.setQuestion(questions[i], quiz.getAnswer(questions[i]));
        dialog.getDialogPane().setContent(diag);
        dialog.showAndWait();
      });

      root.setMouseTransparent(true);
      StackPane wrapper = new StackPane(root, editBtn);

      StackPane.setAlignment(editBtn, Pos.TOP_RIGHT);
      StackPane.setMargin(editBtn, new Insets(8));
      questionList.add(wrapper);
    }

    viewQuestionList.getChildren().addAll(questionList);
  }

  public void addQuestion(String question, String answer) {
    quiz.addQuestion(question, answer);
  }

  @Override
  public void refresh() {
    try {
      viewQuestionList.getChildren().clear();
      getQuizList();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
