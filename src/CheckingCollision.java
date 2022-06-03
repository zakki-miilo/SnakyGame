import java.util.Random;

public class CheckingCollision extends SnakePanel{
    static Random random = new Random();

    public CheckingCollision() {
        super();
    }

    public static void positionOfItems() {
        bombX = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        bombY = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;

        if (snackEaten >= 3) {
            bombX1 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
            bombY1 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        }
        if (snackEaten >= 5) {
            bombY2 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
            bombX3 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        }
        if (snackEaten >= 10) {
            bombX2 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
            bombY3 = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        }

    ratX = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
    ratY = random.nextInt(WINDOW_HEIGHT / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;

    checkRatAndBombPosition(bombX, bombY);
    checkRatAndBombPosition(bombX1, bombY1);
    checkRatAndBombPosition(bombX2, bombY2);
    checkRatAndBombPosition(bombX3, bombY3);

    }

    public static void checkRatAndBombPosition(int bombPositionX, int bombPositionY){
        if(ratX == bombPositionX && ratY == bombPositionY){
            ratX = random.nextInt(WINDOW_WIDTH / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
            ratY = random.nextInt(WINDOW_HEIGHT / SIZE_OF_BLOCK) * SIZE_OF_BLOCK;
        }

    }

    public static void checkTheRat() {
        if ((snakeX[0] == ratX) && (snakeY[0] == ratY)) {
            bodyParts++;
            snackEaten++;
            scores += 200;
            popUp = true;
            positionOfItems();
        }
    }

    public static void dying(){
        running = false;
        isDead = true;
    }

    public static void checkCollisions() {
        for (int i = bodyParts; i > 0; i--) {
            if ((snakeX[0] == snakeX[i]) && (snakeY[0] == snakeY[i])) {
                dying();
                break;
            }
        }
        if (snakeX[0] < 0) {
            dying();
        }
        if (snakeX[0] > WINDOW_WIDTH) {
            dying();
        }
        if (snakeY[0] < 0) {
            dying();
        }
        if (snakeY[0] > WINDOW_HEIGHT) {
            dying();
        }
        if (!running) {
            timer.stop();
        }
    }
}