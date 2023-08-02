package randomNum;

public class RandomNum {
    int min;
    int max;

    public RandomNum(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getRandom() {
        int rand = (int) Math.floor(Math.random() * (this.max - this.min) + this.min);

        return rand;
    }
}
