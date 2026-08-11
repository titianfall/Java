package access.ex;

public class MaxCounter {
    private int count;
    private int max;
    public MaxCounter(int max) {
        this.count = 0;
        this.max = max;
    }

    public void increment() {
        if(isLowerMax()) {
            count++;
        } else {
            System.out.println("max: " + max + " count: " + count);
        }
    }

    private boolean isLowerMax() {
        return count < max;
    }

    public int getCount() {
        return count;
    }
}
