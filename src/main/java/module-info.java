module dpp.dpp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens dpp.dpp to javafx.fxml;
    exports dpp.dpp;
}