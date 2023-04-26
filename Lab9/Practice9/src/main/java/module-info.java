module zihan.assignment.practice9 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens zihan.assignment.practice9 to javafx.fxml;
    exports zihan.assignment.practice9;
}