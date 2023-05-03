module com.project.speedclickinggame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.project.speedclickinggame to javafx.fxml;
    exports com.project.speedclickinggame;
}