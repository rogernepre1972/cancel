package br.com.desktop.domain.model.oss.common;

import lombok.Data;

@Data
public class Address {
    private String street;
    private String number;
    private String neighborhood;
    private String city;
    private String state;
    private String country;
    private String zipCode;

    public Address(String street, String number, String neighborhood, String city, String state, String country, String zipCode) {
        this.street = street;
        this.number = number;
        this.neighborhood = neighborhood;
        this.city = city;
        this.state = state;
        this.country = country;
        this.zipCode = zipCode;
    }

    public Address(){}
}
