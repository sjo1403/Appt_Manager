package model;

public class Customer{

    private int ID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;

    public Customer(int ID,
                    String name,
                    String address,
                    String postalCode,
                    String phone,
                    String country,
                    String division) {
        this.ID = ID;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.phone = phone;
        this.country = country;
        this.division = division;
    }

    public Customer() {

    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) { this.country = country;}

    public String getDivision() { return division; }

    public void setDivision(String division) { this.division = division; }

}