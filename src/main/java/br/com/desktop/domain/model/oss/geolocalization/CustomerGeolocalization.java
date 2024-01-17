package br.com.desktop.domain.model.oss.geolocalization;

import lombok.Data;

@Data
public class CustomerGeolocalization {

    private Long idGeolocalization;
    private Long idContrato;
    private Double latitude;
    private Double longitude;
}
