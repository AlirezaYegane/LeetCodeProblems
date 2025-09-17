package algorithm.reverseInteger;

public class ReverseService {
    private ReverseStrategy strategy;

    public ReverseService(ReverseStrategy strategy) {
        this.strategy = strategy;
    }

    public int execute(int x) {
        return strategy.reverse(x);
    }

    public void setStrategy(ReverseStrategy strategy) {
        this.strategy = strategy;
    }
}