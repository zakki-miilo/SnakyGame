import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class SnakePanel extends JPanel implements ActionListener{
    SnakeGUI frame;
    JLabel scoreLabel;
    JPanel top;
    JPanel bot;
    StartMenu startMenu;
    Random random;
    static Timer timer;

    static final int WINDOW_WIDTH = 900;
    static final int WINDOW_HEIGHT = 600;
    static final int SIZE_OF_BLOCK = 30;
    static final int DELAY = 160;
    static final int FIELD_SIZE = ((WINDOW_WIDTH * WINDOW_HEIGHT)/(SIZE_OF_BLOCK * SIZE_OF_BLOCK));
    static final int[] snakeX = new int[FIELD_SIZE];
    static final int[] snakeY = new int[FIELD_SIZE];

    static int bodyParts = 3;
    static int snackEaten;
    static int scores;
    static int ratX;
    static int ratY;
    static int poisonX;
    static int poisonY;
    static int poisonX1 = -1;
    static int poisonY1 = -1;
    static int poisonX2 = -1;
    static int poisonY2 = -1;
    static int poisonX3 = -1;
    static int poisonY3 = -1;
    static boolean running = false;
    static boolean isDead;
    char direction = 'R';

    ImageIcon rat;
    ImageIcon bomb;
    ImageIcon bg;
    ImageIcon head;
    ImageIcon leaf1;
    Image ratImg;
    Image bombImg;
    Image bgImg;
    Image headImg;
    Image leaf1Img;

    SnakePanel(SnakeGUI frame, StartMenu startMenu){
        top = new JPanel();
        bot = new JPanel();
        scoreLabel = new JLabel("Score: ");
        this.frame = frame;
        this.startMenu = startMenu;

        rat = new ImageIcon("./resources/rat.png");
        bomb = new ImageIcon("./resources/bomb.png");
        bg = new ImageIcon("./resources/bg.png");
        head = new ImageIcon("./resources/head.png");
        leaf1 = new ImageIcon("./resources/leaf1.png");
        random = new Random();

        ratImg = rat.getImage();
        bombImg = bomb.getImage();
        headImg = head.getImage();
        bgImg = bg.getImage();
        leaf1Img = leaf1.getImage();

        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setBackground(new Color(144, 212, 114));
        this.setFocusable(true);
        this.addKeyListener(new KeyPad(this));
        startGame();
    }

    public SnakePanel() {}

    public void startGame() {
        CheckingCollision.positionOfItems();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bgImg, 0, 0, this);
        draw(g);
    }
    public void draw(Graphics g) {
        if(running) {
            scores += 100;
            drawingRatAndBomb(g);
            snakeBodyColor(g);
        }
        else {
            g.setColor(Color.black);
            isDead = true;
            g.dispose();
        }
        BombIsHit(g);
    }

    public void drawingRatAndBomb(Graphics g){
        UI(g);
        g.drawImage(bombImg, poisonX, poisonY, this);
        g.drawImage(ratImg, ratX, ratY, this);

        if(snackEaten >= 3){
            g.drawImage(bombImg, poisonX1, poisonY1, this);
        }
        if(snackEaten >= 5){
            g.drawImage(bombImg, poisonX2, poisonY2, this);
        }
        if(snackEaten >= 10){
            g.drawImage(bombImg, poisonX3, poisonY3, this);
        }
    }

    public void snakeBodyColor(Graphics g){
        boolean pattern = true;

        for(int i = 0; i< bodyParts;i++) {
            if(i == 0) {
                g.setColor(new Color(239, 206, 87));
            }
            else{
                if(pattern){
                    g.setColor(new Color(75, 100, 196, 97));
                    pattern = false;
                }else {
                    g.setColor(new Color(32, 61, 161, 210));
                    pattern = true;
                }
            }
            g.fillRect(snakeX[i], snakeY[i], SIZE_OF_BLOCK, SIZE_OF_BLOCK);
            g.setColor(Color.black);
            g.drawRect(snakeX[i], snakeY[i], SIZE_OF_BLOCK, SIZE_OF_BLOCK);
        }
    }

    public void BombIsHit(Graphics g){
        if ((snakeX[0] == poisonX) && (snakeY[0] == poisonY) || (snakeX[0] == poisonX1) && (snakeY[0] == poisonY1) || (snakeX[0] == poisonX2) && (snakeY[0] == poisonY2)
                || (snakeX[0] == poisonX3) && (snakeY[0] == poisonY3)) {
            running = false;
            g.setColor(Color.black);
            isDead = true;
            g.dispose();
        }
    }

    public void UI(Graphics g){
        g.setColor(new Color(204, 6, 6, 74));
        g.setFont( new Font("Ink Free",Font.BOLD, 37));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("RATS: "+ snackEaten, (WINDOW_WIDTH - metrics.stringWidth("RATS: "+ snackEaten)), g.getFont().getSize());

        g.setColor(new Color(1, 141, 166, 87));
        g.setFont( new Font("Ink Free",Font.BOLD, 37));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("SCORES: "+ scores, (WINDOW_WIDTH - metrics1.stringWidth("SCORES: "+ scores))/80, g.getFont().getSize());
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
                snakeX[i] = snakeX[i - 1];
                snakeY[i] = snakeY[i - 1];
        }

        switch (direction) {
            case 'U':
                snakeY[0] = snakeY[0] - SIZE_OF_BLOCK;
                break;
            case 'D':
                snakeY[0] = snakeY[0] + SIZE_OF_BLOCK;
                break;
            case 'L':
                snakeX[0] = snakeX[0] - SIZE_OF_BLOCK;
                break;
            case 'R':
                snakeX[0] = snakeX[0] + SIZE_OF_BLOCK;
                break;
        }
    }

    public void resetGame(){
        isDead = false;
        running = false;
        poisonX1 = -1;
        poisonY1 = -1;
        poisonX2 = -1;
        poisonY2 = -1;
        poisonX3 = -1;
        poisonY3 = -1;
        bodyParts = 3;
        snackEaten = 0;
        direction = 'R';
        scores = 0;
        timer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running) {
            move();
            CheckingCollision.checkTheRat();
            CheckingCollision.checkCollisions();
            repaint();
        }
        if(isDead) {
            try{
                Thread.sleep(500);
                frame.dispose();
                snakeX[0] = 0;
                snakeY[0] = 0;
                new GameOverMenu(startMenu);
                resetGame();

            }catch (Exception Ex){
                System.out.println("Something is wrong...");
            }
        }
    }
}