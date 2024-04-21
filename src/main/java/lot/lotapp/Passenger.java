package lot.lotapp;

public class Passenger {
    private int id;
    private String firstName;
    private String lastName;
    private String mobileNumber;
    //Constructor
    public Passenger(int id, String firstName, String lastName, String mobileNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
    }
    //Getters
    public int getId() {
        return id;
    }
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
