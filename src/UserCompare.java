import java.util.ArrayList;
import java.util.Comparator;

class UserComparator implements Comparator<Score> {

    ArrayList<Score> scoreList;

    public UserComparator(ArrayList<Score> scoreList) {
        this.scoreList = scoreList;
    }

    public int compare(Score score1, Score score2) {
        return Integer.compare(score1.getUserScore(), score2.getUserScore());
    }
}