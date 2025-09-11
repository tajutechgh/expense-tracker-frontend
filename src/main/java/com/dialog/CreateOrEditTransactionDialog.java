package com.dialog;

import com.model.TransactionCategory;
import com.model.User;
import com.tajutechgh.util.SqlUtil;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CreateOrEditTransactionDialog extends CustomDialog {

    private List<TransactionCategory> listTransactionCategories;

    private TextField transactionNameField;
    private TextField transactionAmountField;
    private DatePicker transactionDatePicker;
    private ComboBox<String> transactionCategoryBox;
    private ToggleGroup transactionTypeToggleGroup;

    private boolean isEditing;

    public CreateOrEditTransactionDialog(User user, boolean isEditing) {

        super(user);

        this.isEditing = isEditing;

        setTitle(isEditing ? "Edit Transaction" : "Create Transaction");
//        setWidth(700);
//        setHeight(595);

        listTransactionCategories = SqlUtil.getAllTransactionCategoriesByUser(user);

        VBox mainContainer = createMainContentBox();

        getDialogPane().setContent(mainContainer);
    }

    private VBox createMainContentBox() {

        VBox mainContentBox = new VBox(20);

        mainContentBox.setAlignment(Pos.CENTER);

        transactionNameField = new TextField();
        transactionNameField.setPromptText("Enter Transaction Name");
        transactionNameField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");

        transactionAmountField = new TextField();
        transactionAmountField.setPromptText("Enter Transaction Amount");
        transactionAmountField.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");

        transactionDatePicker = new DatePicker();
        transactionDatePicker.setPromptText("Enter Transaction Date");
        transactionDatePicker.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");
        transactionDatePicker.setMaxWidth(Double.MAX_VALUE);

        transactionCategoryBox = new ComboBox<>();
        transactionCategoryBox.setPromptText("Choose Category");
        transactionCategoryBox.getStyleClass().addAll("field-background", "text-light-gray", "text-size-md", "rounded-border");
        transactionCategoryBox.setMaxWidth(Double.MAX_VALUE);

        for (TransactionCategory category : listTransactionCategories){

            transactionCategoryBox.getItems().add(category.getCategoryName());
        }

        mainContentBox.getChildren().addAll(
                transactionNameField,
                transactionAmountField,
                transactionDatePicker,
                transactionCategoryBox,
                createTransactionTypeRadioButtonGroup(),
                createConfirmAndCancelButtonBox()
        );

        return mainContentBox;
    }

    public HBox createTransactionTypeRadioButtonGroup(){

        HBox radioButtonBox = new HBox(50);

        radioButtonBox.setAlignment(Pos.CENTER);

        transactionTypeToggleGroup = new ToggleGroup();

        RadioButton incomeRadioButton = new RadioButton("Income");

        incomeRadioButton.setToggleGroup(transactionTypeToggleGroup);
        incomeRadioButton.getStyleClass().addAll("text-size-md", "text-light-gray");

        RadioButton expenseRadioButton = new RadioButton("Expense");

        expenseRadioButton.setToggleGroup(transactionTypeToggleGroup);
        expenseRadioButton.getStyleClass().addAll("text-size-md", "text-light-gray");

        radioButtonBox.getChildren().addAll(
                incomeRadioButton,
                expenseRadioButton
        );

        return radioButtonBox;
    }

    public HBox createConfirmAndCancelButtonBox(){

        HBox confirmAndCancelButtonBox = new HBox(50);

        confirmAndCancelButtonBox.setAlignment(Pos.CENTER);

        Button saveButton = new Button("Save");

        saveButton.setPrefWidth(200);
        saveButton.getStyleClass().addAll("bg-light-green", "text-white", "text-size-md", "rounded-border");

        Button cancelButton = new Button("Cancel");

        cancelButton.setPrefWidth(200);
        cancelButton.getStyleClass().addAll("bg-light-red", "text-white", "text-size-md", "rounded-border");

        confirmAndCancelButtonBox.getChildren().addAll(
                saveButton,
                cancelButton
        );

        return confirmAndCancelButtonBox;
    }
}
