import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameOverMenu implements ActionListener {
    JFrame frame = new JFrame();
    JLabel scoreLabel = new JLabel();
    JLabel ratLabel = new JLabel();
    JLabel gameOverLabel = new JLabel("Game Over!");
    JLabel highScore = new JLabel();
    StartMenu menu;
    JButton restartButton;
    JButton GameOverExit;
    JButton backButton;
    JLabel s1New = new JLabel();
    JLabel s2New = new JLabel();
    JLabel s3New = new JLabel();
    JLabel s4New = new JLabel();
    JLabel s5New = new JLabel();
    ArrayList<Score> scoreboard = new ArrayList<>();
    UserComparator comparator;

    public GameOverMenu(StartMenu menu){
        comparator = new UserComparator(scoreboard);
        this.menu = menu;
        GameOverExit = new JButton("Quit");
        backButton = new JButton("Home");

        highScore.setBounds(450,50,900,35);

        gameOverLabel.setText("Game Over!");
        gameOverLabel.setBounds(450,30,900,35);
        gameOverLabel.setFont(new Font(null,Font.BOLD,45));
        gameOverLabel.setForeground(new Color(25, 77, 131));

        scoreLabel.setBounds(450,90,900,35);
        scoreLabel.setFont(new Font(null,Font.BOLD,35));
        scoreLabel.setText("SCORES: " + SnakePanel.scores);
        scoreLabel.setForeground(new Color(103, 193, 57));

        ratLabel.setBounds(450,130,900,35);
        ratLabel.setFont(new Font(null,Font.PLAIN,30));
        ratLabel.setText("Rats Eaten: " + SnakePanel.snackEaten);
        ratLabel.setForeground(new Color(208, 68, 68));

        restartButton = new JButton("PLAY");
        restartButton.setBounds(125,170,200,60);
        restartButton.setFont(new Font(null,Font.BOLD,45));
        restartButton.setForeground(new Color(61, 178, 88));
        restartButton.addActionListener(this);

        GameOverExit.setFont(new Font(null,Font.BOLD,45));
        GameOverExit.setForeground(new Color(246, 40, 40));
        GameOverExit.setBounds(125,250,200,60);
        GameOverExit.addActionListener(this);

        backButton.setBounds(10,500,200,60);
        backButton.setFont(new Font(null,Font.BOLD,45));
        backButton.setForeground(new Color(61, 178, 88));
        backButton.addActionListener(this);

        showUpdateScores();

        frame.add(backButton);
        frame.add(s1New);
        frame.add(s2New);
        frame.add(s3New);
        frame.add(s4New);
        frame.add(s5New);
        frame.add(ratLabel);
        frame.add(GameOverExit);
        frame.add(scoreLabel);
        frame.add(gameOverLabel);
        frame.add(restartButton);

        frame.getContentPane().setBackground(Color.ORANGE);
        frame.setBackground(Color.gray);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900,600);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void updateHighScore() {
        int current = SnakePanel.scores;
        int highest = 0;
        int index = 0;

        if (SnakePanel.scores > menu.playerScores.get(0)) {
            for (int i = 0; i < menu.playerScores.size(); i++) {
                if (current <menu.playerScores.get(i)) {
                    highest = menu.playerScores.get(i);
                    current = highest;
                }
                if (SnakePanel.scores > menu.playerScores.get(i) && highest < SnakePanel.scores) {
                    index = i;
                }
            }
                menu.playerScores.set(index,SnakePanel.scores);
                menu.playerNames.set(index, menu.userID);
        }
    }

    public void showUpdateScores(){
        updateHighScore();
        addToScoreList();
        writeToFile();

        s1New.setText(String.valueOf(scoreboard.get(0)));
        scoreUpdateLabel(s1New, 200);
        s2New.setText(String.valueOf(scoreboard.get(1)));
        scoreUpdateLabel(s2New, 250);
        s3New.setText(String.valueOf(scoreboard.get(2)));
        scoreUpdateLabel(s3New, 300);
        s4New.setText(String.valueOf(scoreboard.get(3)));
        scoreUpdateLabel(s4New, 350);
        s5New.setText(String.valueOf(scoreboard.get(4)));
        scoreUpdateLabel(s5New, 400);
    }

    public void scoreUpdateLabel(JLabel jLabel, int y){
        jLabel.setBounds(450,y,900,35);
        jLabel.setFont(new Font(null,Font.PLAIN,25));
    }

    public void addToScoreList(){
        for (int i = 0; i < 5; i++){
            scoreboard.add(new Score(menu.playerNames.get(i),menu.playerScores.get(i)));
        }
    }

    public void writeToFile() {
        scoreboard.sort(comparator);
        try (PrintWriter pw = new PrintWriter("./resources/highScores.txt")) {
            for (int i = 0; i < 5; i++) {
                pw.println(scoreboard.get(i) + " ");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GameOverMenu.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == restartButton){
            try {
                Thread.sleep(1000);
                frame.dispose();
                new SnakeGUI(menu);
            }catch (Exception Ex){
                System.out.println("Oh no, its broken...");
            }
        }
        if(e.getSource() == GameOverExit){
            System.exit(0);
        }
        if(e.getSource() == backButton){
            frame.dispose();
            new StartMenu();
        }
    }
}
