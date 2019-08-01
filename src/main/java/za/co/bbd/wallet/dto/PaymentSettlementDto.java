package za.co.bbd.wallet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@Data
public class PaymentSettlementDto implements Serializable {

    @ApiModelProperty(name = "Payment-id", example = "43fca28f-219e-4f16-9079-ec8e7d18f62a",
            notes = "The Payment id")
    @JsonProperty(value = "Payment-id")
    @NotNull
    private UUID PaymentId;

}
