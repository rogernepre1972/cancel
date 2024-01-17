package br.com.desktop.domain.model.oss.common;

public enum PhoneType {
    MOVEL("C", "Movel"),
    CORPORATIVO("E", "Corporativo"),
    RESIDENCIAL("R", "Residencial"); // Assumi que 'R' é o código para Residencial

    private final String code;
    private final String description;

    PhoneType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static PhoneType fromValue(String value) {
        for (PhoneType type : PhoneType.values()) {
            if (type.code.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return RESIDENCIAL; // Valor padrão se nenhum correspondente for encontrado
    }
}
