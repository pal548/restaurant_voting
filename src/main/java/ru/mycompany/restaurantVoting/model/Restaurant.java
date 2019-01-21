package ru.mycompany.restaurantVoting.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "RESTAURANTS")
public class Restaurant extends AbstractBaseEntity {

    @Column(nullable = false, unique = true)
    @NotBlank
    @Size(max = 255)
    private String name;

    @Column(nullable = false)
    @NotBlank
    @Size(max = 255)
    private String address;

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
}
