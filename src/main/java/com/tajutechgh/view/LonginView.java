package com.tajutechgh.view;

import com.tajutechgh.controller.LoginController;
import com.tajutechgh.util.Utilities;
import com.tajutechgh.util.ViewNavigator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class LonginView {

    private Label expenseTrackerLabel = new Label("Expense Tracker");
    private TextField userNameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private Button loginButton = new Button("Login");
    private Label signupLabel = new Label("Don't have an account? Register here");

    public void show(){

        Scene scene = creatScene();

        scene.getStylesheets().add(getClass().getResource( "/static/style.css").toExternalForm());

        new LoginController(this);

        ViewNavigator.switchViews(scene);
    }

    private Scene creatScene() {

        VBox mainContainerBox = new VBox();

        mainContainerBox.getStyleClass().addAll("main-background");
        mainContainerBox.setAlignment(Pos.TOP_CENTER);

        expenseTrackerLabel.getStyleClass().addAll("header", "text-white");

        VBox loginFormBox = createLoginFormBox();

        mainContainerBox.getChildren().addAll(
                expenseTrackerLabel,
                loginFormBox
        );

        return new Scene(mainContainerBox, Utilities.APP_WIDTH, Utilities.APP_HEIGHT);
    }

    private VBox createLoginFormBox(){

        VBox loginFormBox = new VBox(51);

        loginFormBox.setAlignment(Pos.CENTER);

        userNameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        userNameField.setPromptText("Enter Username");
        userNameField.setMaxWidth(473);

        passwordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(473);

        loginButton.getStyleClass().addAll("text-size-lg", "bg-light-blue", "text-white", "text-weight-700", "rounded-border");
        loginButton.setMaxWidth(473);

        signupLabel.getStyleClass().addAll("text-size-md", "text-light-gray", "text-underline", "link-text");

        loginFormBox.getChildren().addAll(
                userNameField,
                passwordField,
                loginButton,
                signupLabel
        );

        return loginFormBox;
    }

    public Label getExpenseTrackerLabel() {
        return expenseTrackerLabel;
    }

    public void setExpenseTrackerLabel(Label expenseTrackerLabel) {
        this.expenseTrackerLabel = expenseTrackerLabel;
    }

    public TextField getUserNameField() {
        return userNameField;
    }

    public void setUserNameField(TextField userNameField) {
        this.userNameField = userNameField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(Button loginButton) {
        this.loginButton = loginButton;
    }

    public Label getSignupLabel() {
        return signupLabel;
    }

    public void setSignupLabel(Label signupLabel) {
        this.signupLabel = signupLabel;
    }
}
