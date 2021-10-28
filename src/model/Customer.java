package model;

public class Customer{

    private int ID;
    private String name;
    private String address;
    private String postalCode;
    private String phone;
    private String country;
    private String division;

    /**
     * Customer constructor used for adding new customers.
     * @param ID Customer ID
     * @param name Customer name
     * @param address Customer address
     * @param postalCode Customer postal code
     * @param phone Customer phone
     * @param country Customer country
     * @param division Customer first level division
     */
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

    /**
     * Empty customer constructor used to update customer objects.
     */
    public Customer() {

    }

    /**
     *
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     *
     * @param ID set ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name set name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     *
     * @param address set address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     *
     * @param postalCode set postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phone set phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     * @return country
     */
    public String getCountry() {
        return country;
    }

    /**
     *
     * @param country set country
     */
    public void setCountry(String country) { this.country = country;}

    /**
     *
     * @return first level division
     */
    public String getDivision() { return division; }

    /**
     *
     * @param division set first level division
     */
    public void setDivision(String division) { this.division = division; }

}