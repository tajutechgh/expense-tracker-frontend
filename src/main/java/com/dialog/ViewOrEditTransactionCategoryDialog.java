package com.dialog;

import com.component.TransactionCategoryComponent;
import com.model.TransactionCategory;
import com.model.User;
import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.SqlUtil;
import com.tajutechgh.util.Utilities;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ViewOrEditTransactionCategoryDialog extends CustomDialog {

    private DashboardController dashboardController;

    public ViewOrEditTransactionCategoryDialog(User user, DashboardController dashboardController) {

        super(user);

        this.dashboardController = dashboardController;

        //configure the dialog
        setTitle("View Categories");
//        setWidth(815);
           setHeight(600);

        ScrollPane mainContainer = createMainContainerContent();

        getDialogPane().setContent(mainContainer);
    }

    private ScrollPane createMainContainerContent() {

        VBox dialogVBox = new VBox(20);

        ScrollPane scrollPane = new ScrollPane(dialogVBox);
        scrollPane.setFitToWidth(true);

        List<TransactionCategory> transactionCategories = SqlUtil.getAllTransactionCategoriesByUser(user);

        if (transactionCategories != null && !transactionCategories.isEmpty()){

            for (TransactionCategory transactionCategory : transactionCategories){

                TransactionCategoryComponent transactionCategoryComponent = new TransactionCategoryComponent(dashboardController, transactionCategory);

                dialogVBox.getChildren().addAll(transactionCategoryComponent);
            }

            return  scrollPane;

        }else {

            Utilities.showAlertDialog(Alert.AlertType.WARNING, "Transaction category list is empty, add new category !!!");
        }

        return  null;
    }
}
