package com.noobdev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.Scene;

public class CatalogController implements Controller {
  @FXML
  private ScrollPane rootPane;
  @FXML
  private VBox content;
  @FXML
  private Label title;
  @FXML
  private VBox contentList;
  @FXML
  private Button createBtn;

  private Router route;

  public CatalogController() {}

  public void setRoute(Router route) {
    this.route = route;
  }

  private HBox buildEntry(String title) throws Exception {
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
      route.pushScene(newRoot, controller);
    });
    return cardRoot;
  }

  public void renderList() throws Exception {
    String[] content = Path.listNodes(Path.getRes() + "/config");
    for (int fileNo = 0; fileNo < content.length; fileNo++) {
      if (content[fileNo].endsWith(".yaml")) {
        String filename = content[fileNo].substring(0, content[fileNo].length()-5);
        contentList.getChildren().add(buildEntry(filename));
      }
    }

    dialogCreate();
  }

  public void dialogCreate() throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/FXMLCreatePop.fxml"));
    Parent pane = loader.load();
    CreateDialogController controller = loader.getController();
    controller.setRoute(route);

    Dialog<ButtonType> root = new Dialog<>();
    root.initOwner(route.getStage());
    root.getDialogPane().setContent(pane);

    createBtn.setOnAction(event -> {
        root.showAndWait();
    });
  }

  @Override
  public void refresh() {
    try {
      contentList.getChildren().clear();
      renderList();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
