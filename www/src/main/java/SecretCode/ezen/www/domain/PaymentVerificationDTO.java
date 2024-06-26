package SecretCode.ezen.www.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class PaymentVerificationDTO {

    private String imp_uid;
    private String merchant_uid;
    private String amount;

    @Builder
    public PaymentVerificationDTO(String imp_uid) {
        this.imp_uid = imp_uid;
    }

}

