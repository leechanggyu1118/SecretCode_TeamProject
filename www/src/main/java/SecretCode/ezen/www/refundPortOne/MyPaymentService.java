package SecretCode.ezen.www.refundPortOne;

import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.request.CancelData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;

@CrossOrigin(origins = "https://jitpack.io")
@PropertySource("classpath:iamport.properties")
@Service
public class MyPaymentService {


    private final IamportClient api;

    public MyPaymentService(
            @Value("${iamport.api.key}") String apiKey,
            @Value("${iamport.api.secret}") String apiSecret) {

        this.api = new IamportClient(apiKey,apiSecret);
    }


    public void cancelPayment(String merchantUid, int refundAmount) {
        try {
            CancelData cancelData = new CancelData(merchantUid, false, BigDecimal.valueOf(refundAmount));
            api.cancelPaymentByImpUid(cancelData);
        } catch (Exception e) {
            throw new RuntimeException("환불 처리 중 오류가 발생했습니다.", e);
        }
    }



}
