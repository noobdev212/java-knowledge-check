package com.noobdev;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Dialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Button;

public class CreateDialogController implements Controller {
  @FXML
  private VBox rootPane;
  @FXML
  private TextField nameTF;
  @FXML
  private TextField descTF;
  @FXML
  private ComboBox<String> algoCB;
  @FXML
  private Button createBtn;
  @FXML
  private Button cancelBtn;

  private String title, description;
  private Router route;

  public CreateDialogController() {}

  public void setRoute(Router route) {
    this.route = route;
  }

  @FXML
  public void build() {
    algoCB.getItems().setAll("test", "test2");
  }

  @FXML
  private void closeDialog() {
    cancelBtn.getScene().getWindow().hide();
  }

  @FXML
  private void createAssessment() throws Exception {
    title = nameTF.getText();
    description = descTF.getText();
    createBtn.getScene().getWindow().hide();

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/FXMLCreate.fxml"));
    Parent root = loader.load();

    CreateController controller = loader.getController();
    route.pushScene(root, controller);
    controller.createQuiz(title, description, route);
  }

  @Override
  public void refresh() {}
}
