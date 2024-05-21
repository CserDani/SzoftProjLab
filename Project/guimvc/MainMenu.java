package guimvc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements ActionListener {
    GradientPanel mainPanel = new GradientPanel();
    JPanel buttonPanel = new JPanel();
    JPanel titlePanel = new JPanel(new GridLayout(2, 1, 10, 10));
    JLabel titleTop = new JLabel("Rektori", SwingConstants.CENTER);
    JLabel titleBottom = new JLabel("Rejtvények", SwingConstants.CENTER);
    JButton singlePlayer = new JButton("Egyjátékos mód");
    JButton multiPlayer = new JButton("Többjátékos mód");
    JButton exit = new JButton("Kilépés");

    public MainMenu(){
        this.setTitle("Rektori Rejtvények");
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setSize(700, 600);
        this.setLocationRelativeTo(null);

        mainPanel.setLayout(new GridLayout(1, 2));
        this.setContentPane(mainPanel);

        titleTop.setForeground(new Color(245, 245, 245));
        titleTop.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 45));
        titleTop.setVerticalAlignment(SwingConstants.BOTTOM);
        titleTop.setOpaque(false);

        titleBottom.setForeground(new Color(245, 245, 245));
        titleBottom.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 45));
        titleBottom.setVerticalAlignment(SwingConstants.TOP);
        titleBottom.setOpaque(false);

        titlePanel.add(titleTop);
        titlePanel.add(titleBottom);
        titlePanel.setOpaque(false);

        setButton(singlePlayer);
        setButton(multiPlayer);
        setButton(exit);

        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(Box.createVerticalStrut(200));
        buttonPanel.add(singlePlayer);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(multiPlayer);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exit);
        buttonPanel.setOpaque(false);

        mainPanel.add(titlePanel);
        mainPanel.add(buttonPanel);

        this.setVisible(true);
    }

    public void setButton(JButton button) {
        button.setForeground(new Color(245, 245, 245));
        button.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 20));
        button.setOpaque(false);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == singlePlayer) {
            this.dispose();
            Game g = new Game();
            g.gameLoad("onePlayerMap.txt");
            GameView gw = new GameView(g);

            new Controller(g, gw);
        } else if(e.getSource() == multiPlayer) {
            this.dispose();
            Game g = new Game();
            g.gameLoad("twoPlayerMap.txt");
            GameView gw = new GameView(g);

            new Controller(g, gw);
        } else if(e.getSource() == exit) {
            System.exit(0);
        }
    }

    static class GradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int w = getWidth();
            int h = getHeight();
            Color color1 = new Color(0, 160, 0);
            Color color2 = new Color(0, 50, 0);
            GradientPaint gp = new GradientPaint(w / 2, 0, color1, w / 2, h, color2);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);
        }
    }
}
