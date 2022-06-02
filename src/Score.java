public class Score {
        private final String name;
        private final int userScore;

        public Score(String name, int score){
            this.name = name;
            this.userScore = score;
        }

        @Override
        public String toString() {
            return  this.name + " " + this.userScore;
    }

    public int getUserScore() {
        return userScore;
    }
}
