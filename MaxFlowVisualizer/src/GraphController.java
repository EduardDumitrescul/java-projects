import GraphVisual.Edge;
import GraphVisual.Vertex;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GraphController {
    private GraphView graphView;
    private GraphModel graphModel;
    private InfoView infoView;
    private DinicTimer dinicTimer;
    public GraphController(GraphModel graphModel, GraphView graphView, InfoView infoView) {
        this.graphModel = graphModel;
        this.graphView = graphView;
        this.infoView = infoView;

        infoView.setCurrentStep(infoView.IDLE);
        infoView.getStartButton().addActionListener(e -> {
            dinicTimer = new DinicTimer(graphModel, graphView, infoView);
            dinicTimer.start();
        });

        infoView.getStopButton().addActionListener(e -> {
            dinicTimer.stop();
        });

        /*graphView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                System.out.println(graphView.getHeight() + " " + graphView.getWidth());
            }
        });*/
    }
}
