public class Main {

    public static void main(String[] args) {

        Sedan mySedan = new Sedan("Toyota");

        mySedan.startEngine();

        // Overriding
        mySedan.accelerate();

        // Overloading
        mySedan.accelerate(30);

        mySedan.stop();
    }
}