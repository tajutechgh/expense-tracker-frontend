package com.component;

import com.model.TransactionCategory;
import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.util.Utilities;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class TransactionCategoryComponent extends HBox {

    private DashboardController dashboardController;
    private TransactionCategory transactionCategory;

    private TextField categoryNameTextField;
    private ColorPicker categoryColorPicker;
    private Button editButton;
    private Button saveButton;
    private Button deleteButton;

    private boolean isEditing;

    public TransactionCategoryComponent(DashboardController dashboardController, TransactionCategory transactionCategory) {

        this.dashboardController = dashboardController;
        this.transactionCategory = transactionCategory;

        getStyleClass().addAll("rounded-border", "field-background", "padding-10px");
        setSpacing(10);
        setAlignment(Pos.CENTER);

        categoryNameTextField = new TextField();
        categoryNameTextField.setText(transactionCategory.getCategoryName());
        categoryNameTextField.setMaxWidth(Double.MAX_VALUE);
        categoryNameTextField.setEditable(false);
        categoryNameTextField.getStyleClass().addAll("field-background", "text-size-md", "text-light-gray");

        categoryColorPicker = new ColorPicker();
        categoryColorPicker.setValue(Color.valueOf(transactionCategory.getCategoryColor()));
        categoryColorPicker.setDisable(true);
        categoryColorPicker.getStyleClass().addAll("text-size-sm");

        editButton = new Button("Edit");
        editButton.setMinWidth(50);
        editButton.getStyleClass().addAll("text-size-sm", "bg-light-green", "text-white");

        editButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                handleToggle();
            }
        });

        saveButton = new Button("Save");
        saveButton.setMinWidth(50);
        saveButton.getStyleClass().addAll("text-size-sm", "bg-light-blue", "text-white");
        saveButton.setVisible(false);
        saveButton.setManaged(false);

        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                handleToggle();;

                //extract values
                String categoryName = categoryNameTextField.getText();
                String categoryColor = Utilities.getHaxColorValue(categoryColorPicker);

                //update transaction category
                SqlUtil.updateTransactionCategory(transactionCategory.getId(), categoryName, categoryColor);
            }
        });

        deleteButton = new Button("Delete");
        deleteButton.setMinWidth(50);
        deleteButton.getStyleClass().addAll("text-size-sm", "bg-light-red", "text-white");

        deleteButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                if (!SqlUtil.deleteTransactionCategory(transactionCategory.getId())) return;;

                //remove this component from the dialog
                setVisible(false);
                setManaged(false);

                if (getParent() instanceof VBox){

                    ((VBox) getParent()).getChildren().remove(TransactionCategoryComponent.this);
                }
            }
        });

        getChildren().addAll(
                categoryNameTextField,
                categoryColorPicker,
                editButton,
                saveButton,
                deleteButton
        );
    }

    private void handleToggle(){

        if (!isEditing){

            isEditing = true;

            // enable category name text field
            categoryNameTextField.setEditable(true);
            categoryNameTextField.setStyle("-fx-background-color: #fff; -fx-text-fill: #000");

            //enable color picker
            categoryColorPicker.setDisable(false);

            //hide edit button
            editButton.setVisible(false);
            editButton.setManaged(false);

            //display save button
            saveButton.setVisible(true);
            saveButton.setManaged(true);

        }else {

            isEditing = false;

            // disable category name text field
            categoryNameTextField.setEditable(false);
            categoryNameTextField.setStyle("-fx-background-color: #515050; -fx-text-fill: #BEB9B9");

            //disable color picker
            categoryColorPicker.setDisable(true);

            //display edit button
            editButton.setVisible(true);
            editButton.setManaged(true);

            //hide save button
            saveButton.setVisible(false);
            saveButton.setManaged(false);
        }
    }
}
