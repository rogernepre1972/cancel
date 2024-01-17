package br.com.desktop.domain.model.oss.common;

import lombok.Data;

@Data
public class Phone {
    private String phoneNumber;
    private String phoneType;

    public Phone(String phoneNumber, String phoneType) {
        this.phoneNumber = phoneNumber;
        this.phoneType = phoneType;
    }
}
