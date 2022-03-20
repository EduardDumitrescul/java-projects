public class VisualizerController {
    private VisualizerView visualizerView;
    private GraphController graphController;
    private GraphView graphView;
    private GraphModel graphModel;

    private InfoView infoView;

    public VisualizerController(VisualizerView visualizerView) {
        this.visualizerView = visualizerView;

        graphModel = new GraphModel();
        graphView = new GraphView();
        infoView = new InfoView();
        graphController = new GraphController(graphModel, graphView, infoView);

        visualizerView.setMainPane(graphView);

        visualizerView.setEastPane(infoView);

        System.out.println("OK");
    }

}
