package carrentalsystem.model;

public class Car {
    private String id;
    private String licensePlateNumber;
    private String model;
    private String year;
    private double pricePerDay;
    private boolean available;

    public Car(boolean available, String id, String licensePlateNumber, String model, double pricePerDay, String year) {
        this.available = available;
        this.id = id;
        this.licensePlateNumber = licensePlateNumber;
        this.model = model;
        this.pricePerDay = pricePerDay;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public String getLicensePlateNumber() {
        return licensePlateNumber;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getModel() {
        return model;
    }

    public String getYear() {
        return year;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Car{" +
                "available=" + available +
                ", id='" + id + '\'' +
                ", licensePlateNumber='" + licensePlateNumber + '\'' +
                ", model='" + model + '\'' +
                ", year='" + year + '\'' +
                ", pricePerDay=" + pricePerDay +
                '}';
    }
}
