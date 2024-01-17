package br.com.desktop.domain.model.oss.availability;

import lombok.Data;

@Data
public class ScheduleParameters {

    String initialDateForCheck;
    Double longitude;
    Double latitude;
    String document;
    String number;
    String street;
    String neighborhood;
    String city;
    String state;
    String country;
    String zipCode;
    String token;
}
