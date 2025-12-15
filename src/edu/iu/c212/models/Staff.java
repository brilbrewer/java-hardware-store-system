package edu.iu.c212.models;

public class Staff {

    /**
     * initializes instance variables
     */
    private String fullName;
    private int age;
    private String role;
    private String availability;

    /**
     * the constructor to build staff members
     * @param name String
     * @param age int
     * @param role String
     * @param av String
     */
    public Staff(String name, int age, String role, String av) {
        this.fullName = name;
        this.age = age;
        this.role = role;
        this.availability = av;
    }

    /**
     * gets fullName
     * @return fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * gets age
     * @return age
     */
    public int getAge() {
        return age;
    }

    /**
     * gets role
     * @return role
     */
    public String getRole() {
        return role;
    }

    /**
     * gets availability
     * @return availability
     */
    public String getAvailability() {
        return availability;
    }

    /**
     * sets a new fullName
     * @param fullName String
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * sets a new age
     * @param age int
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * sets a new role
     * @param role String
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * sets a new availability
     * @param availability String
     */
    public void setAvailability(String availability) {
        this.availability = availability;
    }

    /**
     * this returns the staff members stats separated by spaces
     * @return String
     */
    public String toString() {
        String str = this.fullName + " " + this.age + " " + this.role + " " + this.availability;
        return str;
    }
}
