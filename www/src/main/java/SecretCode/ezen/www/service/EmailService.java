package SecretCode.ezen.www.service;

public interface EmailService {
    String sendSimpleMessage(String to)throws Exception;

    void passwordChange(String pwdReturnCheck) throws Exception;

    void reservationEmail(String email, String merchantUid) throws Exception;
}
