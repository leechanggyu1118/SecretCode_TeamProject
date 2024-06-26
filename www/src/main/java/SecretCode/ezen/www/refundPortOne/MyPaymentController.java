package SecretCode.ezen.www.refundPortOne;

import com.siot.IamportRestClient.exception.IamportResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "https://jitpack.io")
@RestController
@RequestMapping("/api/payments")
public class MyPaymentController {

    @Autowired
    private MyPaymentService myPaymentService;

    @DeleteMapping("/{merchantUid}")
    public ResponseEntity<String> cancelPayment(@PathVariable String merchantUid, @RequestParam int refundAmount) throws IamportResponseException, IOException {
        try {
            myPaymentService.cancelPayment(merchantUid, refundAmount);
            return ResponseEntity.ok("환불 처리가 성공적으로 완료되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("환불 처리 중 오류가 발생했습니다.");
        }
    }



}
