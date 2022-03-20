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
    private DinicTimer dinicTimer = null;
    public GraphController(GraphModel graphModel, GraphView graphView, InfoView infoView) {
        this.graphModel = graphModel;
        this.graphView = graphView;
        this.infoView = infoView;

        infoView.setCurrentStep(infoView.IDLE);
        infoView.getStartButton().addActionListener(e -> {
            infoView.getStartButton().setEnabled(false);
            if(dinicTimer != null)
                dinicTimer.stop();
            graphModel.randomGraph(10);
            dinicTimer = new DinicTimer(graphModel, graphView, infoView);
            dinicTimer.start();
            try{
                Thread.sleep(200);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            infoView.getStartButton().setEnabled(true);
        });

        infoView.getStopButton().addActionListener(e -> {
            infoView.getStopButton().setEnabled(false);
            dinicTimer.stop();
            try{
                Thread.sleep(200);
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
            infoView.getStopButton().setEnabled(true);
        });
    }
}
