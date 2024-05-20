package GUIAndMVC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WinGameMenu extends JFrame implements ActionListener {
    private final JButton ok = new JButton("Ok");
    private final JFrame gameWindow;
    public WinGameMenu(JFrame gameWindow) {
        this.gameWindow = gameWindow;

        this.setResizable(false);
        this.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                e.getWindow().dispose();
                gameWindow.dispose();
                new MainMenu();
            }
        });

        this.ok.addActionListener(this);

        JPanel jp = new JPanel(new GridBagLayout());
        jp.setAlignmentX(Component.CENTER_ALIGNMENT);
        jp.setAlignmentY(Component.TOP_ALIGNMENT);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridy = 0;

        JLabel label = new JLabel("Congrats, you won!");
        jp.add(label, gbc);
        gbc.gridy++;

        jp.add(ok, gbc);

        this.add(jp);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == ok) {
            gameWindow.dispose();
            this.dispose();
            new MainMenu();
        }
    }
}
