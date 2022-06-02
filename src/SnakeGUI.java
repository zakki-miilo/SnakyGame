import javax.swing.*;

public class SnakeGUI extends JFrame {
    StartMenu menu;

    public SnakeGUI(StartMenu menu){
        this.menu = menu;
        this.add(new SnakePanel(this, menu));
        this.setTitle("90's Snake");

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }

}
