package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.ReservationVO;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    @Autowired
    JavaMailSender emailSender;

    private final MemberService msv;

    private final PaymentService psv;

    public static final String ePw = createKey();

    private MimeMessage createMessage(String to)throws Exception{
        System.out.println("보내는 대상 : "+ to);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();

        message.addRecipients(MimeMessage.RecipientType.TO, to);//보내는 대상
        message.setSubject("이메일 인증 테스트");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 SecretCode방탈출 입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("roh1118a@gmail.com","SecretCode"));//보내는 사람

        return message;
    }

    public static String createKey() {
        StringBuffer key = new StringBuffer();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    //  a~z  (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    //  A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }



    @Override
    public String sendSimpleMessage(String to)throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = createMessage(to);
        try{//예외처리
            emailSender.send(message);
        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return ePw;
    }

    @Override
    public void passwordChange(String pwdReturnCheck) throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = passwordChangeeMessage(pwdReturnCheck);
        try{//예외처리
            emailSender.send(message);
            String email = pwdReturnCheck;

            msv.changePwd(email, ePw);

        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }

    }


    private MimeMessage passwordChangeeMessage(String pwdReturnCheck)throws Exception{
        System.out.println("보내는 대상 : "+ pwdReturnCheck);
        System.out.println("인증 번호 : "+ePw);
        MimeMessage  message = emailSender.createMimeMessage();


        message.addRecipients(MimeMessage.RecipientType.TO, pwdReturnCheck);//보내는 대상
        message.setSubject("SecretCode 비밀번호 변경 이메일입니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 SecretCode방탈출 입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>아래 코드를 복사해 입력해주세요<p>";
        msgg+= "<br>";
        msgg+= "<p>감사합니다.<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>변경된 임시 비밀번호입니다.</h3>";
        msgg+= "<h3 style='color:blue;'>로그인 후 반드시 비밀번호를 변경해주시기 바랍니다.</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "CODE : <strong>";
        msgg+= ePw+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("roh1118a@gmail.com","SecretCode"));//보내는 사람

        return message;
    }


    @Override
    public void reservationEmail(String email, String merchantUid) throws Exception {
        // TODO Auto-generated method stub
        MimeMessage message = reservationEmailMessage(email, merchantUid);
        try{//예외처리
            emailSender.send(message);

        }catch(MailException es){
            es.printStackTrace();
            throw new IllegalArgumentException();
        }


    }

    private MimeMessage reservationEmailMessage(String email, String merchantUid)throws Exception{
        System.out.println("보내는 대상 : "+ email);
        System.out.println("주문번호 : "+merchantUid);
        MimeMessage  message = emailSender.createMimeMessage();
        ReservationVO reser = psv.getImRes(email, merchantUid);

        message.addRecipients(MimeMessage.RecipientType.TO, email);//보내는 대상
        message.setSubject("SecretCode 결제 안내 메일입니다.");//제목

        String msgg="";
        msgg+= "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 SecretCode방탈출 입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p>"+"<span style='color:blue;'> "+reser.getName()+"</span>"+"님!</p>"+"<p>"+"<span style='color:blue;'>"+reser.getThemeName()+"</span>" +" 테마 이용권을 구매해주셔서 감사합니다! </p>";
        msgg+= "<br>";
        msgg+= "<p>예약자 :"+"<span style='color:blue;'> "+reser.getName() +"</span>" +"<p>";
        msgg+= "<br>";
        msgg+= "<p>테마명 :"+"<span style='color:blue;'> "+reser.getThemeName() +"</span>" +"<p>";
        msgg+= "<br>";
        msgg+= "<p>예약날짜 :"+"<span style='color:blue;'> "+reser.getReservationDate() +"</span>" +"<p>";
        msgg+= "<br>";
        msgg+= "<p>예약시간 :"+"<span style='color:blue;'> "+reser.getReservationTime() +"</span>" +"<p>";
        msgg+= "<br>";
        msgg+= "<p>인원수 :"+"<span style='color:blue;'> "+reser.getReservationPeople() +"</span>" +"<p>";
        msgg+= "<br>";
        msgg+= "<p>가격 :"+"<span style='color:blue;'> "+reser.getReservationPrice() +"</span>" +"<p>";
        msgg+= "<br>";
        msgg+= "<p>결제일 :"+"<span style='color:blue;'> "+reser.getReservationPaydate() +"</span>" +"<p>";
        msgg+= "<br>";
        msgg+= "<div align='center' style='border:1px solid black; font-family:verdana';>";
        msgg+= "<h3 style='color:blue;'>주문번호 입니다.</h3>";
        msgg+= "<h3 style='color:blue;'>방문 하실 때 본 이메일을 보여주세요!</h3>";
        msgg+= "<div style='font-size:130%'>";
        msgg+= "주문번호 : <strong>";
        msgg+= merchantUid+"</strong><div><br/> ";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("roh1118a@gmail.com","SecretCode"));//보내는 사람

        return message;
    }

}
