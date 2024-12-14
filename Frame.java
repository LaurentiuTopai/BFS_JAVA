import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame implements ActionListener {
    public Frame(){
        Panel panel = new Panel();
        this.setTitle("BFS_VISUAL");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setSize(800, 600);
        this.add(panel);
        Timer timer = new Timer(5000, e -> {
            panel.bfs(0);
        });
        timer.start();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
