package com.example.javfx;

public class Customer {
    private String FirstName;
    private String LastName;
    private int RequiredBurgers;

    public Customer(String FirstName, String LastName, int RequiredBurgers) {
        this.FirstName = FirstName;
        this.LastName = LastName;
        this.RequiredBurgers = RequiredBurgers;
    }

    public String getName () {
        return FirstName+" "+LastName;
    }

    public int getRequiredBurgers () { return RequiredBurgers; }
}

