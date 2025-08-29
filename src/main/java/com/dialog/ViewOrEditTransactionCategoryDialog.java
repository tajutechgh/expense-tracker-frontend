package com.dialog;

import com.model.User;
import com.tajutechgh.controller.DashboardController;

public class ViewOrEditTransactionCategoryDialog extends CustomDialog {

    private DashboardController dashboardController;

    public ViewOrEditTransactionCategoryDialog(User user, DashboardController dashboardController) {

        super(user);

        this.dashboardController = dashboardController;

        //configure the dialog
        setTitle("View Categories");
        setWidth(815);
        setHeight(500);
    }
}
