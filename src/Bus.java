public class Bus extends Vehicle {

    public Bus(String brand) {
        super(brand);
    }

    @Override
    public void accelerate() {
        speed += 10;
        System.out.println("Bus accelerating. Speed: " + speed);
    }

    public void accelerate(int increase) {
        speed += increase;
        System.out.println("Bus accelerating faster. Speed: " + speed);
    }

    @Override
    public void stop() {
        speed = 0;
        System.out.println("Bus stopped.");
    }

    @Override
    public void gas() {
        System.out.println("Bus refueling.");
    }
}