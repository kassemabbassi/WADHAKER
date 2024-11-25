module com.example.prayerproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.media;
    requires java.desktop;
    requires jlayer;

    opens com.example.prayerproject to javafx.fxml;
    exports com.example.prayerproject;
}