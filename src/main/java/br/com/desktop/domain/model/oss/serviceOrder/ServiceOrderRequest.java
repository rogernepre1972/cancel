package br.com.desktop.domain.model.oss.serviceOrder;

import br.com.desktop.domain.model.oss.common.Address;
import br.com.desktop.domain.model.oss.common.Coordinates;
import br.com.desktop.domain.model.oss.common.Phone;
import lombok.Data;

import java.util.List;

@Data
public class ServiceOrderRequest {

    private Requester requester;
    private Coordinates coordinates;
    private String serviceOrderTypeId;
    private Address address;
    private List<Phone> phones;
    private String estimatedStart;
    private String clientDocument;
    private String clientEmail;
    private String clientName;
    private String foreignIdentification;
    private String originApplication;
    private int eScheduleAvailabilityType;

    private ServiceOrderRequest(Builder builder) {
        this.requester = builder.requester;
        this.coordinates = builder.coordinates;
        this.serviceOrderTypeId = builder.serviceOrderTypeId;
        this.address = builder.address;
        this.phones = builder.phones;
        this.estimatedStart = builder.estimatedStart;
        this.clientDocument = builder.clientDocument;
        this.clientEmail = builder.clientEmail;
        this.clientName = builder.clientName;
        this.foreignIdentification = builder.foreignIdentification;
        this.originApplication = builder.originApplication;
        this.eScheduleAvailabilityType = builder.eScheduleAvailabilityType;
    }

    public static class Builder {
        private Requester requester;
        private Coordinates coordinates;
        private String serviceOrderTypeId;
        private Address address;
        private List<Phone> phones;
        private String estimatedStart;
        private String clientDocument;
        private String clientEmail;
        private String clientName;
        private String foreignIdentification;
        private String originApplication;
        private int eScheduleAvailabilityType;

        public Builder withRequester(Requester requester) {
            this.requester = requester;
            return this;
        }

        public Builder withCoordinates(Coordinates coordinates) {
            this.coordinates = coordinates;
            return this;
        }

        public Builder withServiceOrderTypeId(String serviceOrderTypeId) {
            this.serviceOrderTypeId = serviceOrderTypeId;
            return this;
        }

        public Builder withAddress(Address address) {
            this.address = address;
            return this;
        }

        public Builder withPhones(List<Phone> phones) {
            this.phones = phones;
            return this;
        }

        public Builder withEstimatedStart(String estimatedStart) {
            this.estimatedStart = estimatedStart;
            return this;
        }

        public Builder withClientDocument(String clientDocument) {
            this.clientDocument = clientDocument;
            return this;
        }

        public Builder withClientEmail(String clientEmail) {
            this.clientEmail = clientEmail;
            return this;
        }

        public Builder withClientName(String clientName) {
            this.clientName = clientName;
            return this;
        }

        public Builder withForeignIdentification(String foreignIdentification) {
            this.foreignIdentification = foreignIdentification;
            return this;
        }

        public Builder withOriginApplication(String originApplication) {
            this.originApplication = originApplication;
            return this;
        }

        public Builder withEScheduleAvailabilityType(int eScheduleAvailabilityType) {
            this.eScheduleAvailabilityType = eScheduleAvailabilityType;
            return this;
        }

        public ServiceOrderRequest build() {
            return new ServiceOrderRequest(this);
        }
    }
}

