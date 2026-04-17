public abstract class Vehicle {

    String brand;
    int speed;

    public Vehicle(String brand) {
        this.brand = brand;
        this.speed = 0;
    }

    public abstract void accelerate();
    public abstract void stop();
    public abstract void gas();
}