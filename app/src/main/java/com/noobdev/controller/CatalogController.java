package com.noobdev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.Scene;

public class CatalogController {
  @FXML
  private ScrollPane rootPane;
  @FXML
  private VBox content;
  @FXML
  private Label title;
  @FXML
  private VBox contentList;

  public CatalogController() {}

  public HBox buildEntry(String title, Router route) throws Exception {
    // pre-render scene before viewing
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/FXMLQuiz.fxml"));
    ScrollPane newRoot = loader.load();
    QuizController controller = loader.getController();
    Quiz quiz = controller.attachQuiz(title, route);

    HBox cardRoot = (HBox)FXMLLoader.load(getClass().getResource("/xml/FXMLCard.fxml"));
    Label cardTitleLbl = (Label)cardRoot.lookup("#titleLbl");
    cardTitleLbl.setText(title);
    Text descText = (Text)cardRoot.lookup("#description");
    descText.setText(
        quiz.getDescription() +
        "\nTotal Question: " + String.valueOf(quiz.getQuestions().length)
      );

    Button takeBtn = (Button)cardRoot.lookup("#takeBtn");
    takeBtn.setOnAction(event -> {
      route.pushScene(new Scene(newRoot, 600, 500));
    });
    return cardRoot;
  }

  public void renderList(Router route) throws Exception {
    String[] content = Path.listNodes(Path.getRes() + "/config");
    for (int fileNo = 0; fileNo < content.length; fileNo++) {
      if (content[fileNo].endsWith(".yaml")) {
        String filename = content[fileNo].substring(0, content[fileNo].length()-5);
        contentList.getChildren().add(buildEntry(filename, route));
      }
    }
  }
}
