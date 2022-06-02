import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StartMenu extends JPanel implements ActionListener {
    JFrame startFrame = new JFrame();
    JLabel welcomeLabel = new JLabel("Hello to Snake!");
    JLabel messageLabel = new JLabel();
    JButton exit;
    JLabel s1 = new JLabel();
    JLabel s2 = new JLabel();
    JLabel s3 = new JLabel();
    JLabel s4 = new JLabel();
    JLabel s5 = new JLabel();
    JButton userNameEntered;
    JTextField userNameTextField;
    JLabel enterName = new JLabel("Enter Name:");
    JLabel scoreBoard = new JLabel();
    ArrayList<String> playerNames = readNames();
    ArrayList<Integer> playerScores = readScores();
    String userID;
    SnakeGUI snake;

    public StartMenu(){

        userNameTextField = new JTextField();
        userNameEntered = new JButton("PLAY");
        userNameTextField.setBounds(125,200,200,35);

        enterName.setBounds(125,160,200,25);
        enterName.setFont(new Font(null,Font.PLAIN,25));

        userNameEntered.setBounds(125,250,200,60);
        userNameEntered.setFont(new Font(null,Font.BOLD,45));
        userNameEntered.setForeground(new Color(61, 178, 88));
        userNameEntered.addActionListener(this);

        messageLabel.setBounds(450,450,900,35);
        messageLabel.setFont(new Font(null,Font.PLAIN,35));

        welcomeLabel.setBounds(50,50,900,35);
        welcomeLabel.setFont(new Font(null,Font.BOLD,42));
        welcomeLabel.setForeground(new Color(41, 138, 189));
        welcomeLabel.setText("Welcome to Snake");

        scoreBoard.setText("HIGH SCORES:");
        scoreBoard.setBounds(450,100,900,35);
        scoreBoard.setFont(new Font(null,Font.BOLD,45));
        scoreBoard.setForeground(new Color(41, 178, 189));

        exit = new JButton("Quit");
        exit.setBounds(125,310,200,60);
        exit.setFont(new Font(null,Font.BOLD,45));
        exit.setForeground(new Color(246, 40, 40));
        exit.addActionListener(this);

        startFrame.add(welcomeLabel);
        startFrame.add(scoreBoard);
        startFrame.add(userNameEntered);
        startFrame.add(enterName);
        startFrame.add(userNameTextField);
        startFrame.add(messageLabel);
        startFrame.add(s1);
        startFrame.add(s2);
        startFrame.add(s3);
        startFrame.add(s4);
        startFrame.add(s5);
        startFrame.add(exit);

        showScores();

        startFrame.getContentPane().setBackground(Color.ORANGE);
        startFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        startFrame.setSize(900,600);
        startFrame.setLayout(null);
        startFrame.setLocationRelativeTo(null);
        startFrame.setVisible(true);
    }

    public void showScores(){
        s1.setText(playerNames.get(0) + " " + playerScores.get(0));
        scoreLabel(s1, 150);
        s2.setText(playerNames.get(1) + " " + playerScores.get(1));
        scoreLabel(s2, 200);
        s3.setText(playerNames.get(2) + " " + playerScores.get(2));
        scoreLabel(s3, 250);
        s4.setText(playerNames.get(3) + " " + playerScores.get(3));
        scoreLabel(s4, 300);
        s5.setText(playerNames.get(4) + " " + playerScores.get(4));
        scoreLabel(s5, 350);
    }

    public void scoreLabel(JLabel jLabel, int y){
        jLabel.setBounds(450,y,900,35);
        jLabel.setFont(new Font(null,Font.PLAIN,25));
    }

    public ArrayList<String>  readNames() {
        ArrayList<String> names = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("./resources/highScores.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split(" ");
                names.add(str[0]);
            }
        } catch (IOException ex) {
            Logger.getLogger(SnakeGame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(SnakeGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return names;
    }

    public ArrayList<Integer>  readScores() {
        ArrayList<Integer> playScores = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("./resources/highScores.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split(" ");
                playScores.add(Integer.parseInt(str[1]));
            }
        } catch (IOException ex) {
            Logger.getLogger(SnakeGame.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException ex) {
                    Logger.getLogger(SnakeGame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return playScores;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == exit){
            System.exit(0);
        }
        if(e.getSource() == userNameEntered){
            if(userNameTextField.getText().equals("")){
                userID = "New_Player";
            }else {
                userID = userNameTextField.getText();
                userNameTextField.setText("");
            }
            try{
                Thread.sleep(700);
                startFrame.dispose();
                snake= new SnakeGUI(this);
            }catch (Exception EX){
                System.out.println("Something went wrong");
            }
        }
    }
}
