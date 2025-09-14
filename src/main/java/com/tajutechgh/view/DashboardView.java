package com.tajutechgh.view;

import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.Utilities;
import com.tajutechgh.util.ViewNavigator;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;

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

    private Button addTransactionButton;
    private VBox recentTransactionBox;
    private ScrollPane recentTransactionsScrollPane;

    public DashboardView(String email) {

        this.email = email;

        currentBalanceLabel = new Label("Current Balance");
        totalIncomeLabel = new Label("Total Income");
        totalExpenseLabel = new Label("Total Expense");

        addTransactionButton = new Button("+");

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

        VBox mainContainerWrapper = new VBox();

        mainContainerWrapper.getStyleClass().addAll("dashboard-padding");

        VBox.setVgrow(mainContainerWrapper, Priority.ALWAYS);

        HBox balanceSummaryBox = createBalanceSummaryBox();

        GridPane contentGridPane = createContentGridPane();

        VBox.setVgrow(contentGridPane, Priority.ALWAYS);

        mainContainerWrapper.getChildren().addAll(
                balanceSummaryBox,
                contentGridPane
        );

        mainContainer.getChildren().addAll(
                menuBar,
                mainContainerWrapper
        );

        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGHT);
    }

    private GridPane createContentGridPane() {

        GridPane gridPane = new GridPane();

        // set constraints to te cells in the grid pane
        ColumnConstraints columnConstraint = new ColumnConstraints();
        columnConstraint.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(columnConstraint, columnConstraint);

        // recent transaction
        VBox recentTransactionsVBox = createRecentTransactionsVBox();

        recentTransactionsVBox.getStyleClass().addAll("field-background", "rounded-border", "padding-10px");

        GridPane.setVgrow(recentTransactionsVBox, Priority.ALWAYS);

        gridPane.add(recentTransactionsVBox, 1, 0);

        return gridPane;
    }

    private VBox createRecentTransactionsVBox() {

        VBox rescentTransationsVBox = new VBox(20);

        //label and add button
        HBox rescentTransactionsLabelAndAddButtonHBox = new HBox();

        Label recentTransactionsLabel = new Label("Recent Transactions");

        recentTransactionsLabel.setStyle(" -fx-font-size: 20px; -fx-text-fill: #fff;");

        Region labelAndButtonSpaceRegion = new Region();

        HBox.setHgrow(labelAndButtonSpaceRegion, Priority.ALWAYS);

        addTransactionButton.setStyle(" -fx-background-color: #377BE1; -fx-text-fill: #fff;  -fx-font-size: 20px;");

        rescentTransactionsLabelAndAddButtonHBox.getChildren().addAll(
                recentTransactionsLabel,
                labelAndButtonSpaceRegion,
                addTransactionButton
        );

        //recent transactions box
        recentTransactionBox = new VBox(10);
        recentTransactionsScrollPane = new ScrollPane(recentTransactionBox);
        recentTransactionsScrollPane.setFitToWidth(true);
        recentTransactionsScrollPane.setFitToHeight(true);

        rescentTransationsVBox.getChildren().addAll(
                rescentTransactionsLabelAndAddButtonHBox,
                recentTransactionsScrollPane
        );

        return rescentTransationsVBox;
    }

    private MenuBar createMenuBar() {

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        fileMenu.getStyleClass().addAll("text-size-lg");

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

        balanceSummaryBox.getStyleClass().addAll("padding-top");

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

    public MenuItem getViewCategoriesMenuItem() {
        return viewCategoriesMenuItem;
    }

    public void setViewCategoriesMenuItem(MenuItem viewCategoriesMenuItem) {
        this.viewCategoriesMenuItem = viewCategoriesMenuItem;
    }

    public Button getAddTransactionButton() {
        return addTransactionButton;
    }

    public void setAddTransactionButton(Button addTransactionButton) {
        this.addTransactionButton = addTransactionButton;
    }

    public VBox getRecentTransactionBox() {
        return recentTransactionBox;
    }

    public void setRecentTransactionBox(VBox recentTransactionBox) {
        this.recentTransactionBox = recentTransactionBox;
    }
}
