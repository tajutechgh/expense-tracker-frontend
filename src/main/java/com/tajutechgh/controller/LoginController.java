package com.tajutechgh.controller;

import com.tajutechgh.util.ApiUtil;
import com.tajutechgh.util.Utilities;
import com.tajutechgh.view.LonginView;
import com.tajutechgh.view.RegisterView;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.HttpURLConnection;

public class LoginController {

    private LonginView longinView;

    public LoginController(LonginView longinView) {

        this.longinView = longinView;

        initialized();
    }

    // this method get called when ever the login button is clicked
    private void initialized(){

        longinView.getLoginButton().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                isUserValidated();

                String email = longinView.getUserNameField().getText();
                String password = longinView.getPasswordField().getText();

                // authenticate email and password
                HttpURLConnection conn = null;

                try {

                    conn = ApiUtil.fetchApi("/api/v1/users/login?email=" + email + "&password=" + password, ApiUtil.RequestMethod.POST, null);

                    if (conn.getResponseCode() != 200){

                        System.out.println("Failed To Authenticate!");

                        Utilities.showAlertDialog(Alert.AlertType.ERROR, "Failed To Authenticate !!!");

                    }else {

                        System.out.println("Login Successful");

                        Utilities.showAlertDialog(Alert.AlertType.INFORMATION, "Login Successful !!!");
                    }

                }catch (IOException exception){

                     exception.printStackTrace();
                }
            }
        });

        longinView.getSignupLabel().setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                new RegisterView().show();
            }
        });
    }

    private void  isUserValidated() {

        if (longinView.getUserNameField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Username cannot be empty !!!");

            return;
        }

        if (longinView.getPasswordField().getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Password cannot be empty !!!");

        }
    }
}
