public class Player {
    public int tries;
    public int lives;

    public Player(int lives) {
        this.tries = 0;
        this.lives = lives;
    }

    public int getTries() {
        return this.tries;
    }

    public int getLives() {
        return this.lives;
    }
}
