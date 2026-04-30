public class Main {

    public static void main(String[] args) {

        Sedan mySedan = new Sedan("Toyota");

        mySedan.startEngine();

        // Overriding
        mySedan.accelerate();

        // Overloading
        mySedan.accelerate(20);

        mySedan.stop();

        mySedan.gas();

        mySedan.openTrunk();

        Bus myBus = new Bus("Isuzu");
        myBus.accelerate();
        myBus.accelerate(50);
    }
}