package com.dialog;

import com.component.TransactionComponent;
import com.google.gson.JsonObject;
import com.model.Transaction;
import com.model.TransactionCategory;
import com.model.User;
import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.util.Utilities;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CreateOrEditTransactionDialog extends CustomDialog {

    private List<TransactionCategory> listTransactionCategories;

    private TextField transactionNameField;
    private TextField transactionAmountField;
    private DatePicker transactionDatePicker;
    private ComboBox<String> transactionCategoryBox;
    private ToggleGroup transactionTypeToggleGroup;
    private DashboardController dashboardController;
    private TransactionComponent transactionComponent;

    private boolean isEditing;

    public CreateOrEditTransactionDialog(DashboardController dashboardController, TransactionComponent transactionComponent, boolean isEditing) {

        super(dashboardController.getUser());

        this.isEditing = isEditing;
        this.transactionComponent = transactionComponent;
        this.dashboardController = dashboardController;

        setTitle(isEditing ? "Edit Transaction" : "Create Transaction");
//        setWidth(700);
//        setHeight(595);

        listTransactionCategories = SqlUtil.getAllTransactionCategoriesByUser(user);

        VBox mainContainer = createMainContentBox();

        getDialogPane().setContent(mainContainer);
    }

    // for creating transaction
    public CreateOrEditTransactionDialog(DashboardController dashboardController, boolean isEditing){

        // calling the above constructor
        this(dashboardController, null, isEditing);
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

        if (isEditing){

            Transaction transaction = transactionComponent.getTransaction();

            transactionNameField.setText(transaction.getTransactionName());
            transactionAmountField.setText(String.valueOf(transaction.getTransactionAmount()));
            transactionDatePicker.setValue(transaction.getTransactionDate());

            for (TransactionCategory category : listTransactionCategories){

                if (category.getId() == transaction.getTransactionCategoryId()){

                    transactionCategoryBox.setValue(category.getCategoryName());
                }
            }
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

        if (isEditing){

            Transaction transaction = transactionComponent.getTransaction();

            if (transaction.getTransactionTyper().equalsIgnoreCase("income")){

                incomeRadioButton.setSelected(true);

            }else {

                expenseRadioButton.setSelected(true);
            }
        }

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

        saveButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                if (!isInputValidated()) return;

                // extract the data from the nodes
                String transactionName = transactionNameField.getText();
                double transactionAmount = Double.parseDouble(transactionAmountField.getText());
                LocalDate transactionDate = transactionDatePicker.getValue();
                String transactionType = ((RadioButton) transactionTypeToggleGroup.getSelectedToggle()).getText();
                String transactionCategoryName = transactionCategoryBox.getValue();
                TransactionCategory transactionCategory = Utilities.findTransactionCategoryByName(listTransactionCategories, transactionCategoryName);

                JsonObject transactionDataObject = new JsonObject();

                if (isEditing){

                    int transactionId = transactionComponent.getTransaction().getId();

                    transactionDataObject.addProperty("id", transactionId);
                }

                transactionDataObject.addProperty("userId", user.getId());
                transactionDataObject.addProperty("categoryId", transactionCategory.getId());
                transactionDataObject.addProperty("transactionName", transactionName);
                transactionDataObject.addProperty("transactionAmount", transactionAmount);
                transactionDataObject.addProperty("transactionDate", transactionDate.format(DateTimeFormatter.ISO_LOCAL_DATE));
                transactionDataObject.addProperty("transactionType", transactionType);

                if (!isEditing){

                    SqlUtil.createTransaction(transactionDataObject);

                    dashboardController.fetchUserData();

                }else {

                    int transactionId = transactionComponent.getTransaction().getId();

                    SqlUtil.updateTransaction(transactionId, transactionDataObject);

                    dashboardController.fetchUserData();
                }
            }
        });

        Button cancelButton = new Button("Cancel");

        cancelButton.setPrefWidth(200);
        cancelButton.getStyleClass().addAll("bg-light-red", "text-white", "text-size-md", "rounded-border");

        cancelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent mouseEvent) {

                CreateOrEditTransactionDialog.this.close();
            }
        });

        confirmAndCancelButtonBox.getChildren().addAll(
                saveButton,
                cancelButton
        );

        return confirmAndCancelButtonBox;
    }

    private boolean isInputValidated() {

        if (transactionNameField.getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Transaction name cannot be empty !!!");

            return false;
        }

        if (transactionAmountField.getText().isEmpty()){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Transaction amount cannot be empty !!!");

            return false;
        }

        if (transactionDatePicker.getValue() == null){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Transaction date cannot be empty !!!");

            return false;
        }

        if (transactionTypeToggleGroup.getSelectedToggle() == null){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Transaction type cannot be empty !!!");

            return false;
        }


        if (transactionCategoryBox.getValue() == null){

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Transaction category cannot be empty !!!");

            return false;
        }

        return true;
    }
}
