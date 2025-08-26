package com.tajutechgh.controller;

import com.google.gson.JsonObject;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.util.Utilities;
import com.tajutechgh.view.LoginView;
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

                if (!isInputValidated()) return;

                String name = registerView.getNameField().getText();
                String username = registerView.getUserNameField().getText();
                String password = registerView.getPasswordField().getText();

                JsonObject jsonData = new JsonObject();

                jsonData.addProperty("name", name);
                jsonData.addProperty("email", username);
                jsonData.addProperty("password", password);

                SqlUtil.registerUser(jsonData);
            }
        });

        registerView.getLoginLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                new LoginView().show();
            }
        });
    }

    private boolean isInputValidated() {

        if (registerView.getNameField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Name cannot be empty !!!");

            return false;
        }

        if (registerView.getUserNameField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Username cannot be empty !!!");

            return false;
        }

        if (registerView.getPasswordField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Password cannot be empty !!!");

            return false;
        }

        if (registerView.getRepeatPasswordField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Repeated password cannot be empty !!!");

            return false;
        }

        if (!registerView.getPasswordField().getText().equals(registerView.getRepeatPasswordField().getText())){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Passwords do not match !!!");

            return false;
        }

        if (SqlUtil.getUserByEmail(registerView.getUserNameField().getText())){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "User with this email " + registerView.getUserNameField().getText() + " already exists !!!");

            return false;
        }

        return true;
    }
}
