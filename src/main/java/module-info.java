module expense.tracker.frontend {

    requires javafx.controls;
    requires java.desktop;
    requires com.google.gson;
    requires javafx.graphics;

    // make it possible to read data from models and store them in the table
    opens com.model to javafx.base;

    exports com.tajutechgh;
}