package com.tajutechgh.controller;

import com.tajutechgh.util.ApiUtil;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.util.Utilities;
import com.tajutechgh.view.LoginView;
import com.tajutechgh.view.RegisterView;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.HttpURLConnection;

public class LoginController {

    private LoginView loginView;

    public LoginController(LoginView loginView) {

        this.loginView = loginView;

        initialized();
    }

    // this method get called when ever the login button is clicked
    private void initialized(){

        loginView.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if (!isUserValidated()) return;

                String email = loginView.getUserNameField().getText();
                String password = loginView.getPasswordField().getText();

                SqlUtil.loginUser(email, password);
            }
        });

        loginView.getSignupLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                new RegisterView().show();
            }
        });
    }

    private boolean  isUserValidated() {

        if (loginView.getUserNameField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Username cannot be empty !!!");

            return false;
        }

        if (loginView.getPasswordField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Password cannot be empty !!!");

            return false;
        }

        return true;
    }
}
