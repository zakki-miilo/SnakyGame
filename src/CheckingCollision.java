import java.util.Random;

public class CheckingCollision extends SnakePanel{
    static Random random = new Random();

    public CheckingCollision() {
        super();
    }

    public static void positionOfItems() {
        poisonX = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        poisonY = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;

        ratX = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        ratY = random.nextInt(WINDOW_HEIGHT / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;

        if(snackEaten >= 3){
            poisonX1 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
            poisonY1 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        }
        if(snackEaten >= 5){
            poisonY2 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
            poisonX3 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        }
        if(snackEaten >= 10){
            poisonX2 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
            poisonY3 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        }
    }

    public static void checkTheRat() {
        if ((snakeX[0] == ratX) && (snakeY[0] == ratY)) {
            bodyParts++;
            snackEaten++;
            scores += 200;
            positionOfItems();
        }
    }

    public static void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                running = false;
                isDead = true;
                break;
            }
        }
        if (snakeX[0] < 0) {
            running = false;
            isDead = true;
        }
        if (snakeX[0] > WINDOW_WIDTH) {
            running = false;
            isDead = true;
        }
        if (snakeY[0] < 0) {
            running = false;
            isDead = true;
        }
        if (snakeY[0] > WINDOW_HEIGHT) {
            running = false;
            isDead = true;
        }

        if (!running) {
            timer.stop();
        }
    }
}