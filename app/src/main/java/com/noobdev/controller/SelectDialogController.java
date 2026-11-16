package com.noobdev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.Button;

public class SelectDialogController implements DialogController {
  @FXML
  private VBox rootPane;
  @FXML
  private VBox content;
  @FXML
  private Button cancelBtn;

  private Quiz quiz;
  private Dialog<?> dialog;
  private Controller controller;
  private Router route;

  public SelectDialogController() {}

  public void initDialog(Quiz quiz, Dialog<?> dialog, Controller controller) {
    this.quiz = quiz;
    this.dialog = dialog;
    this.controller = controller;
  }

  public void setRoute(Router route) {
    this.route = route;
  }

  public void addOption(String name, String path) throws Exception {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
    Parent root = loader.load();
    DialogController controller = loader.getController();
    Dialog<Void> dialog = new Dialog<>();
    controller.initDialog(quiz, dialog, this);

    dialog.initOwner(route.getStage());

    Button button = new Button(name);
    button.setOnAction(event -> {
      dialog.getDialogPane().setContent(root);
      dialog.showAndWait();
    });

    content.getChildren().add(button);
  }

  @FXML
  private void closeDialog() {
    controller.refresh();
    cancelBtn.getScene().getWindow().hide();
  }

  @Override
  public void refresh() {}
}
