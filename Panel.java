import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Panel extends JPanel {
    private final ArrayList<Point> nodePosition = new ArrayList<>();
    private final boolean[][] adjMatrix;
    private final Color[] nodeColors;
    private final int NODE_RADIUS = 25;
    private int currentNodeIndex = 0;

    public Panel() {
        this.setBackground(Color.WHITE);
        this.setPreferredSize(new Dimension(800, 600));
        nodeColors = new Color[6];
        adjMatrix = new boolean[6][6];
        initializeNodes();
        generateRandomEdges();
    }

    private void initializeNodes() {
        for (int i = 0; i < 3; i++) {
            nodePosition.add(new Point(i * 120 + 25, 160 + 25));
            nodeColors[i] = Color.GRAY;
        }

        for (int i = 0; i < 3; i++) {
            nodePosition.add(new Point(i * 150 + 150 + 25, 300 + 25));
            nodeColors[i + 3] = Color.GRAY;
        }
    }

    private void generateRandomEdges() {
        int totalNodes = nodePosition.size();

        for (int i = 0; i < totalNodes; i++) {
            int connections = new Random().nextInt(2); // 1-2 legături pentru fiecare nod
            for (int j = 0; j < connections; j++) {
                int target = new Random().nextInt(totalNodes);
                if (target != i && !adjMatrix[i][target]) {
                    adjMatrix[i][target] = true;
                    adjMatrix[target][i] = true;
                }
            }
        }
    }

    public void bfs(int startNode) {
        boolean[] visited = new boolean[nodePosition.size()];
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startNode);
        visited[startNode] = true;
        nodeColors[startNode] = Color.GREEN;

        // Setăm un timer pentru actualizarea graficului
        Timer timer = new Timer(500, e -> {
            if (currentNodeIndex < queue.size()) {
                int currentNode = queue.poll();
                repaint();
                currentNodeIndex++;
            } else {
                ((Timer) e.getSource()).stop();
            }
        });

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            visited[currentNode] = true;
            nodeColors[currentNode] = Color.GREEN;

            // Adăugăm nodurile nevisitate
            for (int i = 0; i < adjMatrix[currentNode].length; i++) {
                if (adjMatrix[currentNode][i] && !visited[i]) {
                    visited[i] = true;
                    queue.add(i);
                }
            }
            repaint();
        }

        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2));

        g2d.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < adjMatrix.length; i++) {
            for (int j = i + 1; j < adjMatrix[i].length; j++) {
                if (adjMatrix[i][j]) {
                    Point p1 = nodePosition.get(i);
                    Point p2 = nodePosition.get(j);
                    g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        for (int i = 0; i < nodePosition.size(); i++) {
            Point p = nodePosition.get(i);
            g2d.setColor(nodeColors[i]);
            g2d.fillOval(p.x - NODE_RADIUS, p.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
            g2d.setColor(Color.BLACK);
            g2d.drawOval(p.x - NODE_RADIUS, p.y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);
        }
    }
}
