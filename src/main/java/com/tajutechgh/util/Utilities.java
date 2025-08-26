package com.tajutechgh.util;

import javafx.scene.control.Alert;

public class Utilities {

    public static final int APP_WIDTH = 1614;
    public static final int APP_HEIGHT = 900;

    public static void showAlertDialog(Alert.AlertType alertType, String message){

        Alert alert = new Alert(alertType);

        alert.setContentText(message);
        alert.showAndWait();
    }
}
