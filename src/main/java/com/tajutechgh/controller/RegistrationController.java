package com.tajutechgh.controller;

import com.tajutechgh.util.Utilities;
import com.tajutechgh.view.LonginView;
import com.tajutechgh.view.RegisterView;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

public class RegistrationController {

    private RegisterView registerView;

    public RegistrationController(RegisterView registerView) {

        this.registerView = registerView;

        initialized();
    }

    private void initialized() {

        registerView.getRegisterButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

              isInputValidated();

              String name = registerView.getNameField().getText();
              String username = registerView.getUserNameField().getText();
              String password = registerView.getPasswordField().getText();
              String repeatedPassword = registerView.getRepeatPasswordField().getText();
            }
        });

        registerView.getLoginLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                new LonginView().show();
            }
        });
    }

    private void isInputValidated() {

        if (registerView.getNameField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Name cannot be empty !!!");

            return;
        }

        if (registerView.getUserNameField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Username cannot be empty !!!");

            return;
        }

        if (registerView.getPasswordField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Password cannot be empty !!!");

            return;
        }

        if (registerView.getRepeatPasswordField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Repeated password cannot be empty !!!");

            return;
        }

        if (!registerView.getPasswordField().getText().equals(registerView.getRepeatPasswordField().getText())){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Passwords do not match !!!");

        }
    }
}
