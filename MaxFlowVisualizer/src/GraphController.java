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

    public GraphController(GraphModel graphModel, GraphView graphView) {
        this.graphModel = graphModel;
        this.graphView = graphView;

        /*graphView.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                System.out.println(graphView.getHeight() + " " + graphView.getWidth());
            }
        });*/


    }

    private boolean isReady = true, isRunning = false;

    public void setInfoView(InfoView infoView) {
        this.infoView = infoView;
        infoView.setCurrentStep(infoView.IDLE);
        infoView.getStartButton().addActionListener(e -> {
            if(isRunning == false) {
                DinicMaxFlow();
                isRunning = true;
            }
        });

        infoView.getStopButton().addActionListener(e -> {
            algorithm.cancel();
            timer.cancel();
            timer.purge();
            isRunning = false;
        });
    }

    private Timer timer = new Timer();
    private TimerTask algorithm = new TimerTask() {
        @Override
        public void run() {

        }
    };

    public void DinicMaxFlow() {
        graphModel.randomGraph(10);
        algorithm = new TimerTask() {
            @Override
            public void run() {
                int maxFlow = 0;
                while(graphModel.computeLayers()) {
                    graphView.setVertexList(graphModel.getVertexList());
                    graphView.setEdgeList(graphModel.getEdgeList());
                    graphView.setPath(new ArrayList<>());
                    graphView.refresh();

                    infoView.setCurrentStep(infoView.LEVEL_GRAPH);

                    try {
                        Thread.sleep(1200);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }

                    ArrayList<Edge> path = new ArrayList<>();

                    int flow = graphModel.findNewFlow(path);
                    while(flow > 0) {
                        infoView.setCurrentStep(infoView.FLOW_SEARCH);
                        maxFlow += flow;
                        infoView.setCurrentFlow(maxFlow);

                        graphView.setPath(path);
                        graphView.refresh();

                        try {
                            Thread.sleep(500);
                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                        }

                        flow = graphModel.findNewFlow(path);
                    }

                    try {
                        Thread.sleep(1200);
                    }
                    catch (Exception ex){
                        ex.printStackTrace();
                    }
                }

                infoView.setCurrentStep(infoView.ALGORITHM_DONE);
            }
        };
        timer = new Timer();
        timer.scheduleAtFixedRate(algorithm, 0, 100000);
    }

}
