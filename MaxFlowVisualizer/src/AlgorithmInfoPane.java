import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;
import java.io.IOException;
import java.net.URI;


public class AlgorithmInfoPane extends JPanel {
    private String text =
            "<html>\n" +
            "<head>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "<h3> Ce ne propunem sa rezolvam? </h3>\n" +
            "<p>Presupunem ca avem o retea de conducte, fiecare dintre acestea avand o capacitate reprezentand cantitatea maxima de apa ce poate curge prin ea intr-o unitate de timp. Consideran ca avem o sursa ce poate pompa o cantitate infinita de apa si o destinatie, vrem sa aflam debitul maxim ce poate fi transportat intre sursa si destinatie. </p>\n" +
            "\n" +
            "<p>Aceasta problema poate fi rezolvata ca o problema de flux maxim, unde reteaua de conducte este modelata sub forma unui graf orientat (conductele sunt muchii, iar punctele de intersectie noduri)</p>\n" +
            "\n" +
            "<p>Asadar, reteaua de flux este graful orientat G=(V, E) cu 2 noduri speciale (sursa si destinatie), iar fiecare muchie (u, v) are atasata o valoarea cap(u, v), reprezentand capacitatea acesteia.</p>\n" +
            "\n" +
            "<p>Unui dintre algoritmii care calculeaza valoarea fluzului maxim este Algoritmul lui Dinic, avand complexitate O(V^2 * E)</p>\n" +
            "\n" +
            "<h3> O scurta explicatie a algoritmului </h3>\n" +
            "\n" +
            "<p>Algoritmul lui Dinic pentru calcularea fluxului maxim consta intr-o serie de pasi ce vor fi repetati cat timp fluxul total poate fi imbunatatit.</p>\n" +
            "<ol>\n" +
            "<li> Vom construi reteaua stratificata a grafului folosind doar muchiile nesaturate (debitul de apa nu este maxim), utilizand o parcurgere BFS, pentru a garanta drumul cel mai scurt intre nodul sursa si celelalte noduri.</li>\n" +
            "<li> Cautam printr-o parcurgere DFS drumuri in reteaua stratificata ce contribuie la fluxum total, actualizand si debitul fiecare muchii </li>\n" +
            "</ol>\n" +
            "\n" +
            "<h3>Pentru o explicatie amanuntita a algoritmului:</h3> \n" +
            "<a href=\"https://cp-algorithms.com/graph/dinic.html\">Algoritmul lui Dinic</a>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "\n" +
            "</html>\n";
    private JTextPane textPane = new JTextPane();
    private JScrollPane scrollPane = new JScrollPane(textPane);
    AlgorithmInfoPane() {
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        textPane.setContentType("text/html");
        textPane.setEditable(false);
        textPane.setText(text);
        textPane.addHyperlinkListener(e -> {
            Desktop desktop = Desktop.getDesktop();
            if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED)
                try {
                    desktop.browse(URI.create(e.getURL().toString()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
        });
    }
}
