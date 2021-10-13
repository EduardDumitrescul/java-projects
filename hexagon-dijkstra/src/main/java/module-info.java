module com.example.hexagondijkstra {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hexagondijkstra to javafx.fxml;
    exports com.example.hexagondijkstra;
}