public class Motorcycle extends Vehicle {

    public Motorcycle(String brand) {
        super(brand);
    }

    @Override
    public void accelerate() {
        speed += 20;
        System.out.println("Motorcycle accelerating. Speed: " + speed);
    }

    @Override
    public void stop() {
        speed = 0;
        System.out.println("Motorcycle stopped.");
    }

    @Override
    public void gas() {
        System.out.println("Motorcycle refueling.");
    }
}