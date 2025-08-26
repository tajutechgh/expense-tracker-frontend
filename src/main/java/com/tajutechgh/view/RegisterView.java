package com.tajutechgh.view;

import com.tajutechgh.util.ViewNavigator;
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
    private Button loginButton = new Button("Login");
    private Label signupLabel = new Label("Already have an account? Login here");

    public void show(){

        Scene scene = creatScene();

        scene.getStylesheets().add(getClass().getResource( "/static/style.css").toExternalForm());

        ViewNavigator.switchViews(scene);
    }

    private Scene creatScene() {

        VBox mainContainer = new VBox(44);
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
