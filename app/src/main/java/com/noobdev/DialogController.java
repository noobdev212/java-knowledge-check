package com.noobdev;

import javafx.scene.control.Dialog;

public interface DialogController extends Controller {
  void initDialog(Quiz quiz, Dialog<?> dialog, Controller controller);
}
