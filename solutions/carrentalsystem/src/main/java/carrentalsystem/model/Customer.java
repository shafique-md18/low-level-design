package carrentalsystem.model;

public class Customer {
    private String id;
    private String name;
    private String email;
    private String drivingLicenseNumber;

    public Customer(String drivingLicenseNumber, String email, String id, String name) {
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.email = email;
        this.id = id;
        this.name = name;
    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
