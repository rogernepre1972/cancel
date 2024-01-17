package br.com.desktop.domain.model.oss.geolocalization;

import lombok.Data;

@Data
public class CustomerPlanGeo {
    Customer customer;
    ClientsContract clientsContract;
    CustomerGeolocalization customerGeolocalization;
}
