package br.com.desktop.domain.model.oss.geolocalization;

import lombok.Data;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

@Data
public class Customer {
    public String id;
    public String customerId;
    public String name;
    public String email;
    private String registryCode;
    private String neighborhood;
    public String street;
    public String addressNumber;
    public String zipCode;
    public String city;
    public String state;
    public String country;
    public String phoneType;
    public String phoneNumber;
    public String dueAt;
    private String dataCadastro;
    public CustomerStatusEnum status;

    @Getter
    public enum CustomerStatusEnum {
        INACTIVE("N"),
        ACTIVE("S"),
        CREATED("C"),
        LIVRE_PARA_CADASTRO("I"),
        RESERVADO_PROVISIONAMENTO("R"),
        CADASTRO_ANTIGO("X"),
        MIGRADO_PARA_2("M");


        private final String codigo;

        CustomerStatusEnum(String codigo) {
            this.codigo = codigo;
        }

        public static Optional<CustomerStatusEnum> fromCodigo(String codigo) {
            return Arrays.stream(values())
                    .filter(status ->
                            status.codigo.equals(codigo))
                    .findFirst();
        }
    }
}
