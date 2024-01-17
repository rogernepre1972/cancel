package br.com.desktop.domain.model.oss.serviceOrder;

import lombok.Data;

@Data
public class Requester {
    private String name;
    private String type;

    public Requester(String name, String type) {
        this.name = name;
        this.type = type;
    }
    public Requester(){}
}
