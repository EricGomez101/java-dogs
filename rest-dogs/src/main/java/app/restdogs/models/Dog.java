package app.restdogs.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data // creates getters, setters, toString
@Entity // object ready jpa storage
public class Dog
{
    private @Id @GeneratedValue Long id; // primary key and auto incremented.
    private String breed;
    private int avgWeight;
    private boolean appropriate;

    // needed for setter dependency injection
    public Dog() //default constructor
    {
    }

    public Dog(String breed, int avgWeight, boolean appropriate)
    {
        this.breed = breed;
        this.avgWeight = avgWeight;
        this.appropriate = appropriate;
    }
}
