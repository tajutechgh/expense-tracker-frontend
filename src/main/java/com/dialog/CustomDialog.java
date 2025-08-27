package com.dialog;

import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

public class CustomDialog extends Dialog {

    public CustomDialog() {

        // add stylesheet
        getDialogPane().getStylesheets().addAll(getClass().getResource("/static/style.css").toExternalForm());
        getDialogPane().getStyleClass().addAll("main-background");

        getDialogPane().getButtonTypes().addAll(ButtonType.OK);
        Button okButton = (Button) getDialogPane().lookupButton(ButtonType.OK);
        okButton.setVisible(false);
        okButton.setDisable(true);

    }
}
