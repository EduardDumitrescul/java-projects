module com.example.hexagondijkstra {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.desktop;


    opens com.example.hexagondijkstra to javafx.fxml;
    exports com.example.hexagondijkstra;
}