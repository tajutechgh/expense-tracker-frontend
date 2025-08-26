package com.tajutechgh.view;

import com.tajutechgh.util.Utilities;
import com.tajutechgh.util.ViewNavigator;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class RegisterView {

    private Label expenseTrackerLabel = new Label("Expense Tracker");
    private TextField nameField = new TextField();
    private TextField userNameField = new TextField();
    private PasswordField passwordField = new PasswordField();
    private PasswordField repeatPasswordField = new PasswordField();
    private Button loginButton = new Button("Register");
    private Label signupLabel = new Label("Already have an account? Login here");

    public void show(){

        Scene scene = creatScene();

        scene.getStylesheets().add(getClass().getResource( "/static/style.css").toExternalForm());

        ViewNavigator.switchViews(scene);
    }

    private Scene creatScene() {

        VBox mainContainerBox = new VBox(44);

        mainContainerBox.getStyleClass().addAll("main-background");
        mainContainerBox.setAlignment(Pos.TOP_CENTER);

        expenseTrackerLabel.getStyleClass().addAll("header", "text-white");

        VBox registrationFormBox = createRegistrationForm();

        mainContainerBox.getChildren().addAll(
                expenseTrackerLabel,
                registrationFormBox
        );

        return new Scene(mainContainerBox, Utilities.APP_WIDTH, Utilities.APP_HEIGHT);
    }

    private VBox createRegistrationForm() {

        VBox registrationForm = new VBox(45);

        registrationForm.setAlignment(Pos.CENTER);

        nameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        nameField.setPromptText("Enter Name");
        nameField.setMaxWidth(473);

        userNameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        userNameField.setPromptText("Enter Username");
        userNameField.setMaxWidth(473);

        passwordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        passwordField.setPromptText("Enter Password");
        passwordField.setMaxWidth(473);

        repeatPasswordField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-lg", "rounded-border");
        repeatPasswordField.setPromptText("Repeat Password");
        repeatPasswordField.setMaxWidth(473);

        loginButton.getStyleClass().addAll("text-size-lg", "bg-light-blue", "text-white", "text-weight-700", "rounded-border");
        loginButton.setMaxWidth(473);

        signupLabel.getStyleClass().addAll("text-size-md", "text-light-gray", "text-underline", "link-text");

        registrationForm.getChildren().addAll(
                nameField,
                userNameField,
                passwordField,
                repeatPasswordField,
                loginButton,
                signupLabel
        );

        return registrationForm;
    }

    public Label getExpenseTrackerLabel() {
        return expenseTrackerLabel;
    }

    public void setExpenseTrackerLabel(Label expenseTrackerLabel) {
        this.expenseTrackerLabel = expenseTrackerLabel;
    }

    public TextField getNameField() {
        return nameField;
    }

    public void setNameField(TextField nameField) {
        this.nameField = nameField;
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

    public PasswordField getRepeatPasswordField() {
        return repeatPasswordField;
    }

    public void setRepeatPasswordField(PasswordField repeatPasswordField) {
        this.repeatPasswordField = repeatPasswordField;
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
