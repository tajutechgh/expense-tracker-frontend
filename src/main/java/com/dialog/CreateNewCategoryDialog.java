package com.dialog;

import com.google.gson.JsonObject;
import com.model.User;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.util.Utilities;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class CreateNewCategoryDialog extends CustomDialog {

    private TextField newCategoryTextField;
    private ColorPicker colorPicker;
    private Button createCategoryButton;

    public CreateNewCategoryDialog(User user) {

        super(user);

        setTitle("New Category");

        getDialogPane().setContent(createDialogContentBox());
    }

    private Node createDialogContentBox() {

        VBox dialogContentBox = new VBox(20);

        dialogContentBox.setPrefWidth(400);
        dialogContentBox.setPrefHeight(250);

        newCategoryTextField = new TextField();
        newCategoryTextField.setPromptText("Enter Category Name");
        newCategoryTextField.getStyleClass().addAll("text-size-md", "field-background", "text-light-gray");

        colorPicker = new ColorPicker();
        colorPicker.getStyleClass().addAll("text-size-md");
        colorPicker.setMaxWidth(Double.MAX_VALUE);

        createCategoryButton = new Button("Create Category");
        createCategoryButton.getStyleClass().addAll("bg-light-blue", "text-size-md", "text-white", "rounded-border");
        createCategoryButton.setMaxWidth(Double.MAX_VALUE);

        createCategoryButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if (!isInputValidated()) return;

                String categoryName = newCategoryTextField.getText();
                String color = Utilities.getHaxColorValue(colorPicker);

//                JsonObject userData = new JsonObject();
//
//                userData.addProperty("id", user.getId());

                JsonObject transactionCategoryData = new JsonObject();

                transactionCategoryData.addProperty("userId", user.getId());
                transactionCategoryData.addProperty("categoryName", categoryName);
                transactionCategoryData.addProperty("categoryColor", color);

                SqlUtil.createCategory(transactionCategoryData);
            }
        });

        dialogContentBox.getChildren().addAll(
                newCategoryTextField,
                colorPicker,
                createCategoryButton
        );

        return dialogContentBox;
    }

    private boolean isInputValidated() {

        if (newCategoryTextField.getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Category name cannot be empty !!!");

            return false;
        }

        return true;
    }
}
