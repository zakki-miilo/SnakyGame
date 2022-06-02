import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class KeyPad extends KeyAdapter {
    SnakePanel gamePanel;

    public KeyPad(SnakePanel gamePanel){
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        switch(e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                if(gamePanel.direction != 'R') {
                    gamePanel.direction = 'L';
                }
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                if(gamePanel.direction != 'L') {
                    gamePanel.direction = 'R';
                }
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                if(gamePanel.direction != 'D') {
                    gamePanel.direction = 'U';
                }
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                if(gamePanel.direction != 'U') {
                    gamePanel.direction = 'D';
                }
                break;
        }
    }
}

