import GraphVisual.Edge;
import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DinicTimer extends Timer {
    private GraphModel graphModel;
    private GraphView graphView;
    private InfoView infoView;
    private boolean isRunning = false;

    private DinicTimerTask algorithm = new DinicTimerTask();

    public DinicTimer(GraphModel graphModel, GraphView graphView, InfoView infoView) {
        this.graphModel = graphModel;
        this.graphView = graphView;
        this.infoView = infoView;
    }

    public void stop() {
        if(isRunning == false)
                return;
        algorithm.stop();
        isRunning = false;
    }

    public void start() {
        if(isRunning == true)
            return;
        schedule(algorithm, 0);
        isRunning = true;
    }

    private class DinicTimerTask extends TimerTask {
        private boolean flag = false;
        DinicTimerTask() {};
        public void stop() {
            flag = true;
            this.cancel();
        }
        @Override
        public void run() {
            int maxFlow = 0;
            flag = false;
            while(graphModel.computeLayers()) {
                graphView.setVertexList(graphModel.getVertexList());
                graphView.setEdgeList(graphModel.getEdgeList());
                graphView.setPath(new ArrayList<>());
                graphView.refresh();

                infoView.setCurrentStep(infoView.LEVEL_GRAPH);

                try {
                    if(flag) break;
                    Thread.sleep(1000);
                    if(flag) break;
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
                        if(flag) break;
                        Thread.sleep(500);
                        if(flag) break;
                    }
                    catch (Exception ex) {
                        ex.printStackTrace();
                    }

                    flow = graphModel.findNewFlow(path);
                }

                try {
                    if(flag) break;
                    Thread.sleep(100);
                    if(flag) break;
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            infoView.setCurrentStep(infoView.ALGORITHM_DONE);
            System.out.println(maxFlow);
        }

    }
}
