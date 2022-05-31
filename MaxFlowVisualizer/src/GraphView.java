import GraphVisual.Edge;
import GraphVisual.Vertex;

import javax.swing.*;
import javax.swing.text.Segment;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GraphView extends JPanel{
    private Vertex[] vertexList = new Vertex[0];
    private ArrayList<Edge> edgeList = new ArrayList<>();
    private ArrayList<Edge> path = new ArrayList<>();
    private double margin = 20, diameter;

    public GraphView() {

    }

    public void setVertexList(Vertex[] vertexList) {
        this.vertexList = vertexList;
    }

    public void setEdgeList(ArrayList<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public void setPath(ArrayList<Edge> path) {
        this.path = path;
    }

    public void refresh() {
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        super.paint(g);

        FontMetrics fm = g.getFontMetrics();
        Graphics2D graphics2D = (Graphics2D) g;

        graphics2D.translate(margin, margin);

        computeVerticesPosition();
        graphics2D.setStroke(new BasicStroke((int)(Math.sqrt(diameter) / 2)));
        g.setFont(new Font("TimesRoman", Font.BOLD, (int)(Math.sqrt(diameter) * 1.9)));
        drawVertices(graphics2D);
        drawEdges(graphics2D);
    }

    void computeVerticesPosition() {
        int layerCount = 0;
        for(int i = 0; i < vertexList.length; i ++) {
            layerCount = Math.max(layerCount, vertexList[i].getLayer());
        }

        int[] layerSize = new int[layerCount + 1];
        int maxSize = 0;
        for(int i = 0; i < vertexList.length; i ++) {
            if(vertexList[i].getLayer() < 0)
                continue;
            layerSize[vertexList[i].getLayer()] ++;
            maxSize = Math.max(layerSize[vertexList[i].getLayer()], maxSize);
        }

        double height = getHeight() - 2 * margin;
        double width = getWidth() - 2 * margin;
        double verticalDistance = .7 * height / maxSize;
        double horizontalDistance = .7 * width / layerCount;
        diameter = Math.min(0.3 * height / maxSize, 0.3 * width / layerCount);

        double[] layerPosX = new double[layerCount + 4];
        double[] layerPosY = new double[layerCount + 4];
        for(int i = 0; i <= layerCount; i ++) {
            layerPosX[i] = width / 2 + (i - 1.0 * layerCount / 2) * horizontalDistance;
            layerPosY[i] = height / 2 - (layerSize[i] - 1) * .5 * verticalDistance;
        }

        for(int i = 0; i < vertexList.length; i ++) {
            int layer = vertexList[i].getLayer();
            if(layer < 0)
                continue;
            vertexList[i].setX(layerPosX[layer]);
            vertexList[i].setY(layerPosY[layer]);
            layerPosY[layer] += verticalDistance;
        }
    }

    void drawCenteredString(Graphics2D graphics2D, String string, double x, double y) {
        FontMetrics fm = graphics2D.getFontMetrics();
        double X = (double)x - fm.stringWidth(string) / 2.0;
        double Y = (double)y - fm.getHeight() / 2.0  + fm.getAscent();

        graphics2D.drawString(string, (float)X, (float)Y);
    }

    void drawVertices(Graphics2D graphics2D) {
        for(int i = 0; i < vertexList.length; i ++) {
            if(vertexList[i].getLayer() < 0)
                continue;
            double x = vertexList[i].getX() - diameter / 2;
            double y = vertexList[i].getY() - diameter / 2;

            Shape circle = new Ellipse2D.Double(x, y, diameter, diameter);

            graphics2D.draw(circle);

            drawCenteredString(graphics2D, vertexList[i].getLabel(), vertexList[i].getX(), vertexList[i].getY());
        }
    }

    void drawEdges(Graphics2D graphics2D) {
        Map<Integer, Boolean> highlighted = new HashMap<>();
        for(Edge edge: path)
            highlighted.put(edge.getSrc() * 1000000 + edge.getDest(), true);
        for(Edge edge: edgeList){
            Vertex src = vertexList[edge.getSrc()];
            double x1 = src.getX();
            double y1 = src.getY();
            Vertex dest = vertexList[edge.getDest()];
            double x2 = dest.getX();
            double y2 = dest.getY();
            double radius = diameter / 2;

            double length = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
            double sx = x1 + (x2 - x1) * radius / length;
            double sy = y1 + (y2 - y1) * radius / length;

            double fx = x2 - (x2 - x1) * radius / length;
            double fy = y2 - (y2 - y1) * radius / length;

            double angle = Math.atan2(y2 - y1, x2 - x1);
            double size = 20;

            if(highlighted.get(edge.getSrc() * 1000000 + edge.getDest()) != null)
                graphics2D.setColor(Color.red);
            else
                graphics2D.setColor(Color.blue);

            if(dest.getLayer() - src.getLayer() == 1) {
                BasicStroke solid = new BasicStroke(2.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER);
                graphics2D.setStroke(solid);

                double f = 0.3;
                double xMid = (f * sx + (1 - f) * fx);
                double yMid = (f * sy + (1 - f) * fy) ;
                String string = Integer.toString(edge.getFlow()) + "\\" + Integer.toString(edge.getCap());
                drawCenteredString(graphics2D, string, xMid + Math.sin(angle) * 16, yMid - Math.cos(angle) * 16);
                graphics2D.drawLine((int)fx, (int)fy, (int)(fx + Math.cos(angle + Math.PI * 150 / 180) * size), (int)(fy + Math.sin(angle + Math.PI * 150 / 180) * size));
                graphics2D.drawLine((int)fx, (int)fy, (int)(fx + Math.cos(angle - Math.PI * 150 / 180) * size), (int)(fy + Math.sin(angle - Math.PI * 150 / 180) * size));
            }
            else {
                BasicStroke dashed = new BasicStroke(0.4f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, new float[]{10.0f}, 0.0f);
                graphics2D.setStroke(dashed);
            }

            graphics2D.drawLine((int) sx, (int)sy, (int)fx, (int)fy);
        }
    }

}
