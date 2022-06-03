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
    JLabel popUpMessage;
    StartMenu startMenu;
    Random random;
    static Timer timer;

    static final int WINDOW_WIDTH = 900;
    static final int WINDOW_HEIGHT = 600;
    static final int SIZE_OF_BLOCK = 30;
    static final int DELAY = 150;
    static final int FIELD_SIZE = ((WINDOW_WIDTH * WINDOW_HEIGHT)/(SIZE_OF_BLOCK * SIZE_OF_BLOCK));
    static final int[] snakeX = new int[FIELD_SIZE];
    static final int[] snakeY = new int[FIELD_SIZE];

    static int bodyParts = 3;
    static int snackEaten;
    static int scores;
    static int ratX;
    static int ratY;

    static int bombX;
    static int bombY;
    static int bombX1 = -1;
    static int bombY1 = -1;
    static int bombX2 = -1;
    static int bombY2 = -1;
    static int bombX3 = -1;
    static int bombY3 = -1;

    static boolean running = false;
    static boolean isDead;
    static boolean popUp;
    static int snakeLive = 3;
    static boolean isHit;
    char direction = 'R';

    //int xVelocity = 1;
    //int yVelocity = 1;
    int heartY = 15;

    ImageIcon rat;
    ImageIcon bomb;
    ImageIcon bg;
    ImageIcon head;
    ImageIcon leaf1;
    ImageIcon heart;
    Image ratImg;
    Image bombImg;
    Image bgImg;
    Image headImg;
    Image heartImg;
    //Testing Idea
    Image leaf1Img;

    SnakePanel(SnakeGUI frame, StartMenu startMenu){
        top = new JPanel();
        bot = new JPanel();
        scoreLabel = new JLabel("Score: ");
        popUpMessage = new JLabel();
        this.frame = frame;
        this.startMenu = startMenu;

        rat = new ImageIcon("./resources/rat.png");
        bomb = new ImageIcon("./resources/bomb.png");
        bg = new ImageIcon("./resources/bg.png");
        head = new ImageIcon("./resources/head.png");
        leaf1 = new ImageIcon("./resources/leaf1.png");
        heart = new ImageIcon("./resources/heart.png");
        random = new Random();

        ratImg = rat.getImage();
        bombImg = bomb.getImage();
        headImg = head.getImage();
        bgImg = bg.getImage();
        leaf1Img = leaf1.getImage();
        heartImg = heart.getImage();

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
            if(popUp){
                g.setColor(new Color(236, 200, 69, 255));
                g.setFont(new Font(null,Font.ITALIC, 30));
                g.drawString("+200", snakeX[0], snakeY[0]);
                popUp = false;
            }
            scores += 100;
            UI(g);
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
        g.drawImage(bombImg, bombX, bombY, this);
        g.drawImage(ratImg, ratX, ratY, this);

        if(snackEaten >= 3){
            g.drawImage(bombImg, bombX1, bombY1, this);
        }
        if(snackEaten >= 5){
            g.drawImage(bombImg, bombX2, bombY2, this);
        }
        if(snackEaten >= 10){
            g.drawImage(bombImg, bombX3, bombY3, this);
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
            if(isHit){
                try {
                    g.setColor(Color.red);
                    g.fillRect(snakeX[i], snakeY[i], SIZE_OF_BLOCK, SIZE_OF_BLOCK);
                }catch (Exception Ex){
                    System.out.println("Oh no....");
                }
                isHit = false;
            }
        }
    }

    public void BombIsHit(Graphics g){
        if(snakeLive == 3){
            g.drawImage(heartImg, 370, heartY, this);
            g.drawImage(heartImg, 420, heartY, this);
            g.drawImage(heartImg, 470, heartY, this);
        } else if(snakeLive == 2) {
            g.drawImage(heartImg, 400, heartY, this);
            g.drawImage(heartImg, 450, heartY, this);
        }else if(snakeLive == 1){
            g.drawImage(heartImg, 400, heartY, this);
        }
        theBomb(bombX, bombY);
        theBomb(bombX1, bombY1);
        theBomb(bombX2, bombY2);
        theBomb(bombX3, bombY3);

        if(snakeLive == 0){
            running = false;
            g.setColor(Color.black);
            isDead = true;
            g.dispose();
        }
    }

    public void theBomb(int bX, int bY){
        if ((snakeX[0] == bX) && (snakeY[0] == bY)){
            snakeLive--;
            isHit = true;
            CheckingCollision.positionOfItems();
        }
    }

    public void UI(Graphics g){
        g.setColor(new Color(204, 6, 6, 74));
        g.setFont( new Font(null,Font.BOLD, 37));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("RATS: "+ snackEaten, (WINDOW_WIDTH - metrics.stringWidth("RATS: "+ snackEaten)), g.getFont().getSize());

        g.setColor(new Color(1, 141, 166, 87));
        g.setFont( new Font(null,Font.BOLD, 37));
        g.drawString("SCORES: "+ scores, (WINDOW_WIDTH - metrics.stringWidth("SCORES: "+ scores))/80, g.getFont().getSize());
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
        snakeLive = 3;
        bombX1 = -1;
        bombY1 = -1;
        bombX2 = -1;
        bombY2 = -1;
        bombX3 = -1;
        bombY3 = -1;
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
        }
        if(isDead) {
            try{
                Thread.sleep(500);
                frame.dispose();
                snakeX[0] = 0;
                snakeY[0] = 0;
                isHit = false;
                new GameOverMenu(startMenu);
                resetGame();

            }catch (Exception Ex){
                System.out.println("Something is wrong...");
            }
        }
        repaint();
    }
}