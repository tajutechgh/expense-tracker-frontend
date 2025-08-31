package com.dialog;

import com.component.TransactionCategoryComponent;
import com.model.TransactionCategory;
import com.model.User;
import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.SqlUtil;
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
//        setHeight(500);

        ScrollPane mainContainer = createMainContainerContent();

        getDialogPane().setContent(mainContainer);
    }

    private ScrollPane createMainContainerContent() {

        VBox dialogVBox = new VBox(20);

        ScrollPane scrollPane = new ScrollPane(dialogVBox);
        scrollPane.setFitToWidth(true);

        List<TransactionCategory> transactionCategories = SqlUtil.getAllTransactionCategoriesByUser(user);

        for (TransactionCategory transactionCategory : transactionCategories){

            TransactionCategoryComponent transactionCategoryComponent = new TransactionCategoryComponent(dashboardController, transactionCategory);

            dialogVBox.getChildren().addAll(transactionCategoryComponent);
        }

        return  scrollPane;
    }
}
