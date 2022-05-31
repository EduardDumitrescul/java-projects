import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class ProjectInfoPane extends JPanel {
    private String text = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<h3> Scopul proiectului </h3>\n" +
            "<p> Aceasta aplicatie isi propune sa ofere posibilitatea vizualizarii unei animatii ce reda functionarea unui algoritm de calcularea a fluxului maxim (folosind Algoritmul lui Dinic). </p>\n" +
            "<h3> Continut </h3>\n" +
            "<p> Pe pagina 2 se afla informatii legate de ce reprezinta fluxul maxim si cum functioneaza din punct de vedere teoretic Algoritmul lui Dinic. </p>\n" +
            "<p> Pe pagina 3 se gaseste animatia propriu-zisa, ce utilizeaza un graf orientat generat aleator in cadrul programului. La fiecare rulare a animatiei, se genereaza un alt graf.\n" +
            "\n" +
            "<h3> Autor </h3>\n" +
            "<p> Proiect realizat de <br> Eduard-Valentin Dumitrescul <br> Colegiul National \"Stefan cel Mare\" Suceava <br> clasa a 12-a B </p>\n" +
            "\n" +
            "</body>\n" +
            "\n" +
            "</html>\n";
    private JTextPane textPane = new JTextPane();
    private JScrollPane scrollPane = new JScrollPane(textPane);
    ProjectInfoPane() {
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.CENTER);
        textPane.setContentType("text/html");
        textPane.setText(text);
    }
}
