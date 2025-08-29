package com.tajutechgh.view;

import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.Utilities;
import com.tajutechgh.util.ViewNavigator;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DashboardView {

    private MenuItem createCategoryMenuItem;
    private MenuItem viewCategoriesMenuItem;

    private String email;

    private Label currentBalanceLabel;
    private Label currentBalance;
    private Label totalIncomeLabel;
    private Label totalIncome;
    private Label totalExpenseLabel;
    private Label totalExpense;

    public DashboardView(String email) {

        this.email = email;

        currentBalanceLabel = new Label("Current Balance");
        totalIncomeLabel = new Label("Total Income");
        totalExpenseLabel = new Label("Total Expense");

        currentBalance = new Label("$0.00");
        totalIncome = new Label("$0.00");
        totalExpense = new Label("$0.00");
    }

    public void show(){

        Scene scene = createScene();

        scene.getStylesheets().add(getClass().getResource( "/static/style.css").toExternalForm());

        new DashboardController(this);

        ViewNavigator.switchViews(scene);
    }

    private Scene createScene() {

        MenuBar menuBar = createMenuBar();

        VBox mainContainer = new VBox();

        mainContainer.getStyleClass().addAll("main-background");

        HBox balanceSummaryBox = createBalanceSummaryBox();

        mainContainer.getChildren().addAll(
                menuBar,
                balanceSummaryBox
        );

        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGHT);
    }

    private MenuBar createMenuBar() {

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        createCategoryMenuItem = new MenuItem("Create Category");
        viewCategoriesMenuItem = new MenuItem("View Categories");

        fileMenu.getItems().addAll(
                createCategoryMenuItem,
                viewCategoriesMenuItem
        );

        menuBar.getMenus().addAll(fileMenu);

        return menuBar;
    }

    private HBox createBalanceSummaryBox() {

        HBox balanceSummaryBox = new HBox();

        VBox currentBalanceBox = new VBox();

        currentBalanceLabel.getStyleClass().addAll("text-size-lg", "text-light-gray");
        currentBalance.getStyleClass().addAll("text-size-lg", "text-white");

        currentBalanceBox.getChildren().addAll(
                currentBalanceLabel,
                currentBalance
        );

        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);

        VBox totalIncomeBox = new VBox();

        totalIncomeLabel.getStyleClass().addAll("text-size-lg", "text-light-gray");
        totalIncome.getStyleClass().addAll("text-size-lg", "text-white");

        totalIncomeBox.getChildren().addAll(
               totalIncomeLabel,
                totalIncome
        );

        Region region2 = new Region();
        HBox.setHgrow(region2, Priority.ALWAYS);

        VBox totalExpenseBox = new VBox();

        totalExpenseLabel.getStyleClass().addAll("text-size-lg", "text-light-gray");
        totalExpense.getStyleClass().addAll("text-size-lg", "text-white");

        totalExpenseBox.getChildren().addAll(
                totalExpenseLabel,
                totalExpense
        );

        balanceSummaryBox.getChildren().addAll(
                currentBalanceBox,
                region1,
                totalIncomeBox,
                region2,
                totalExpenseBox
        );

        return balanceSummaryBox;
    }

    public MenuItem getCreateteCategoryMenuItem() {
        return createCategoryMenuItem;
    }

    public void setCreateCategoryMenuItem(MenuItem createCategoryMenuItem) {
        this.createCategoryMenuItem = createCategoryMenuItem;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
