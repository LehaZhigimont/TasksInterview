
public class Numbers {
    private int[] numbers;
    private int number;

    public Numbers() {
        this.numbers = randomArray();
    }

    private int[] randomArray() {
        int[] array = new int[4];
        for (int index = 0; index < array.length; index++) {
            number = (int) (1 + Math.random() * 9);
            array[index] = number;
        }
        return array;
    }

    public int[] getNumbers() {
        return numbers;
    }
}
