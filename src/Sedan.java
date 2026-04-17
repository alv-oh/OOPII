public class Sedan extends Vehicle implements Automobile {

    public Sedan(String brand) {
        super(brand);
    }

    // Method overriding
    @Override
    public void accelerate() {
        speed += 15;
        System.out.println("Sedan accelerating. Speed: " + speed);
    }

    // Method overloading
    public void accelerate(int increase) {
        speed += increase;
        System.out.println("Sedan accelerating faster. Speed: " + speed);
    }

    @Override
    public void stop() {
        speed = 0;
        System.out.println("Sedan stopped.");
    }

    @Override
    public void gas() {
        System.out.println("Sedan refueling.");
    }

    @Override
    public void startEngine() {
        System.out.println("Sedan engine started.");
    }

    @Override
    public void openTrunk() {
        System.out.println("Sedan trunk opened.");
    }
}
