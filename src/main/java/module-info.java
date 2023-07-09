module com.example.javfx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javfx to javafx.fxml;
    exports com.example.javfx;
}