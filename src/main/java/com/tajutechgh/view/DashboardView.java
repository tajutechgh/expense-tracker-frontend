package com.tajutechgh.view;

import com.animation.LoadAnimationPane;
import com.model.MonthlyFinance;
import com.tajutechgh.controller.DashboardController;
import com.tajutechgh.util.Utilities;
import com.tajutechgh.util.ViewNavigator;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.math.BigDecimal;
import java.time.Year;

public class DashboardView {

    private MenuItem createCategoryMenuItem;
    private MenuItem viewCategoriesMenuItem;
    private MenuItem logoutMenuItem;

    private LoadAnimationPane loadAnimationPane;

    private String email;

    private Label currentBalanceLabel;
    private Label currentBalance;
    private Label totalIncomeLabel;
    private Label totalIncome;
    private Label totalExpenseLabel;
    private Label totalExpense;

    private ComboBox<Integer> yearComboBox;
    private Button viewChartButton;
    private Button addTransactionButton;
    private VBox recentTransactionBox;
    private ScrollPane recentTransactionsScrollPane;

    // table
    private TableView<MonthlyFinance> transactionTable;
    private TableColumn<MonthlyFinance, String> monthlyColumn;
    private TableColumn<MonthlyFinance, BigDecimal> incomeColumn;
    private TableColumn<MonthlyFinance, BigDecimal> expenseColumn;

    public DashboardView(String email) {

        this.email = email;

        loadAnimationPane = new LoadAnimationPane(Utilities.APP_WIDTH, Utilities.APP_HEIGHT);

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

        scene.widthProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                loadAnimationPane.resizeWidth(t1.doubleValue());

                resizeTableWidthColumns();
            }
        });

        scene.heightProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {

                loadAnimationPane.resizeHeight(t1.doubleValue());
            }
        });

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
                mainContainerWrapper,
                loadAnimationPane
        );

        return new Scene(mainContainer, Utilities.APP_WIDTH, Utilities.APP_HEIGHT);
    }

    private GridPane createContentGridPane() {

        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);

        // set constraints to te cells in the grid pane
        ColumnConstraints columnConstraint = new ColumnConstraints();
        columnConstraint.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(columnConstraint, columnConstraint);

        //transaction table at the left
        VBox transactionsTableSummaryVBox = new VBox(20);

        transactionsTableSummaryVBox.getStyleClass().addAll( "rounded-border", "padding-10px");

        HBox yearComboBoxAndChartButtonBox = createYearComboBoxAndChartButtonBox();

        VBox transactionTableContentBox = createTransactionTableContentBox();

        VBox.setVgrow(transactionTableContentBox, Priority.ALWAYS);

        transactionsTableSummaryVBox.getChildren().addAll(
                yearComboBoxAndChartButtonBox,
                transactionTableContentBox
        );

        // recent transaction at the right
        VBox recentTransactionsVBox = createRecentTransactionsVBox();

        recentTransactionsVBox.getStyleClass().addAll("field-background", "rounded-border", "padding-10px");

        GridPane.setVgrow(recentTransactionsVBox, Priority.ALWAYS);

        gridPane.add(transactionsTableSummaryVBox, 0, 0);
        gridPane.add(recentTransactionsVBox, 1, 0);

        return gridPane;
    }

    private HBox createYearComboBoxAndChartButtonBox() {

        HBox yearComboBoxAndChartButtonBox = new HBox(15);

        yearComboBox = new ComboBox<>();

        yearComboBox.getStyleClass().addAll("text-size-md");
        yearComboBox.setValue(Year.now().getValue());

        viewChartButton = new Button("View Chart");

        viewChartButton.getStyleClass().addAll("field-background", "text-white", "text-size-md");

        yearComboBoxAndChartButtonBox.getChildren().addAll(yearComboBox, viewChartButton);

        return yearComboBoxAndChartButtonBox;
    }

    private VBox createTransactionTableContentBox() {

        VBox transactionTableContentBox = new VBox();

        transactionTable = new TableView<>();

        VBox.setVgrow(transactionTable, Priority.ALWAYS);

        monthlyColumn = new TableColumn<>("Month");

        monthlyColumn.setCellValueFactory(new PropertyValueFactory<>("month"));
        monthlyColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        incomeColumn = new TableColumn<>("Income");

        incomeColumn.setCellValueFactory(new PropertyValueFactory<>("income"));
        incomeColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        expenseColumn = new TableColumn<>("Expense");

        expenseColumn.setCellValueFactory(new PropertyValueFactory<>("expense"));
        expenseColumn.getStyleClass().addAll("main-background", "text-size-md", "text-light-gray");

        transactionTable.getColumns().addAll(
                monthlyColumn,
                incomeColumn,
                expenseColumn
        );

        transactionTableContentBox.getChildren().addAll(transactionTable);

        resizeTableWidthColumns();

        return transactionTableContentBox;
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

    private void resizeTableWidthColumns(){

        Platform.runLater(new Runnable() {

            @Override
            public void run() {

                double colsWidth = transactionTable.getWidth() * (0.335);

                monthlyColumn.setPrefWidth(colsWidth);

                incomeColumn.setPrefWidth(colsWidth);

                expenseColumn.setPrefWidth(colsWidth);
            }
        });
    }

    private MenuBar createMenuBar() {

        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");

        fileMenu.getStyleClass().addAll("text-size-lg");

        createCategoryMenuItem = new MenuItem("Create Category");
        viewCategoriesMenuItem = new MenuItem("View Categories");
        logoutMenuItem = new MenuItem("Logout");

        fileMenu.getItems().addAll(
                createCategoryMenuItem,
                viewCategoriesMenuItem,
                logoutMenuItem
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

    public LoadAnimationPane getLoadAnimationPane() {
        return loadAnimationPane;
    }

    public void setLoadAnimationPane(LoadAnimationPane loadAnimationPane) {
        this.loadAnimationPane = loadAnimationPane;
    }

    public MenuItem getLogoutMenuItem() {
        return logoutMenuItem;
    }

    public void setLogoutMenuItem(MenuItem logoutMenuItem) {
        this.logoutMenuItem = logoutMenuItem;
    }

    public TableView<MonthlyFinance> getTransactionTable() {
        return transactionTable;
    }

    public void setTransactionTable(TableView<MonthlyFinance> transactionTable) {
        this.transactionTable = transactionTable;
    }

    public TableColumn<MonthlyFinance, String> getMonthlyColumn() {
        return monthlyColumn;
    }

    public void setMonthlyColumn(TableColumn<MonthlyFinance, String> monthlyColumn) {
        this.monthlyColumn = monthlyColumn;
    }

    public TableColumn<MonthlyFinance, BigDecimal> getIncomeColumn() {
        return incomeColumn;
    }

    public void setIncomeColumn(TableColumn<MonthlyFinance, BigDecimal> incomeColumn) {
        this.incomeColumn = incomeColumn;
    }

    public TableColumn<MonthlyFinance, BigDecimal> getExpenseColumn() {
        return expenseColumn;
    }

    public void setExpenseColumn(TableColumn<MonthlyFinance, BigDecimal> expenseColumn) {
        this.expenseColumn = expenseColumn;
    }

    public ComboBox<Integer> getYearComboBox() {
        return yearComboBox;
    }

    public void setYearComboBox(ComboBox<Integer> yearComboBox) {
        this.yearComboBox = yearComboBox;
    }

    public Label getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Label totalExpense) {
        this.totalExpense = totalExpense;
    }

    public Label getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Label totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Label getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(Label currentBalance) {
        this.currentBalance = currentBalance;
    }
}
