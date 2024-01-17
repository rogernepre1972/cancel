package br.com.desktop.domain.model.oss.availability;

import lombok.Data;

@Data
public class AvailabilityItem {

    private String description;
    private String value;

    public AvailabilityItem(String description, String value) {
        this.description = description;
        this.value = value;
    }

    public AvailabilityItem(){}
}
