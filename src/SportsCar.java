public class SportsCar extends Vehicle implements Automobile {

    public SportsCar(String brand) {
        super(brand);
    }

    @Override
    public void accelerate() {
        speed += 40;
        System.out.println("SportsCar accelerating fast! Speed: " + speed);
    }

    @Override
    public void stop() {
        speed = 0;
        System.out.println("SportsCar stopped.");
    }

    @Override
    public void gas() {
        System.out.println("SportsCar refueling premium fuel.");
    }

    @Override
    public void startEngine() {
        System.out.println("SportsCar engine started.");
    }

    @Override
    public void openTrunk() {
        System.out.println("SportsCar trunk opened.");
    }
}