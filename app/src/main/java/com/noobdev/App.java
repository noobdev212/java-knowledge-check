package com.noobdev;

import javafx.application.Application;
import javafx.scene.control.ScrollPane;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class App extends Application
{
  @Override()
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("JKC");
    Router route = new Router(primaryStage);

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/xml/FXMLCatalog.fxml"));
    ScrollPane root = loader.load();

    CatalogController controller = loader.getController();
    controller.renderList(route);

    Scene scene = new Scene(root, 600, 400);
    route.pushScene(scene);
  }

  public static void main(String[] args) {
    launch();
  }
}
