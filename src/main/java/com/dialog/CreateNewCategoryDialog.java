package com.dialog;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class CreateNewCategoryDialog extends CustomDialog {

    private TextField newCategoryTextField;
    private ColorPicker colorPicker;
    private Button createCategoryButton;

    public CreateNewCategoryDialog() {

        super();

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

        dialogContentBox.getChildren().addAll(
                newCategoryTextField,
                colorPicker,
                createCategoryButton
        );

        return dialogContentBox;
    }
}
