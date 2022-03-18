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
        graphController = new GraphController(graphModel, graphView);

        visualizerView.setMainPane(graphView);

        infoView = new InfoView();

        graphController.setInfoView(infoView);

        visualizerView.setEastPane(infoView);

        System.out.println("OK");
    }

}
