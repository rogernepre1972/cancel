package br.com.desktop.domain.model.oss.availability;

import br.com.desktop.domain.model.oss.availability.AvailabilityItem;
import lombok.Data;

import java.util.List;

@Data
public class AvailabilityResponse {
    private boolean success;
    private List<AvailabilityItem> data;

    public AvailabilityResponse(boolean success, List<AvailabilityItem> data) {
        this.success = success;
        this.data = data;
    }

    public AvailabilityResponse(){}
}
