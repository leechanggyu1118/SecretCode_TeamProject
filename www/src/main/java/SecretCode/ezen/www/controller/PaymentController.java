package SecretCode.ezen.www.controller;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaVO;
import SecretCode.ezen.www.domain.ReservationVO;
import SecretCode.ezen.www.service.EmailService;
import SecretCode.ezen.www.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/portOnePay/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class PaymentController {

    private final PaymentService psv;
    private final EmailService emailService;


//    @GetMapping("/portOnePay")
//    public void pay() {
//    }

    //결제 후 예약 DB 작업
    @ResponseBody
    @GetMapping(value = "/reservation/{merchantUid}/{date}/{time}/{theme}/{name}/{phone}/{email}/{participants}/{price}")
    public String reservation(ReservationVO rvo, @PathVariable("merchantUid")String merchantUid, @PathVariable("date")String date, @PathVariable("time")String time, @PathVariable("theme")String theme
            , @PathVariable("name")String name, @PathVariable("phone")String phone, @PathVariable("email")String email
            , @PathVariable("participants")int participants, @PathVariable("price")int price) {

        log.info(">>> merchant_uid >> {}",merchantUid);
        log.info(">>> date >> {}",date);
        log.info(">>> time >> {}",time);
        log.info(">>> theme >> {}",theme);
        log.info(">>> name >> {}",name);
        log.info(">>> phone >> {}",phone);
        log.info(">>> email >> {}",email);
        log.info(">>> participants >> {}",participants);
        log.info(">>> price >> {}",price);

        rvo.setMerchantUid(merchantUid);
        rvo.setReservationDate(date);
        rvo.setReservationTime(time);
        rvo.setThemeName(theme);
        rvo.setName(name);
        rvo.setPhone(phone);
        rvo.setEmail(email);
        rvo.setReservationPeople(participants);
        rvo.setReservationPrice(price);
        log.info(">>> rvo >> {}",rvo);

        int isOk = psv.reservationInsert(rvo);

        log.info(">>> reservationVO ================ >> {}",isOk);

        return isOk>0? "1":"0";

    }

    //예약후 이메일 전송
    @ResponseBody
    @GetMapping(value = "/reservationEmail/{email}/{merchantUid}")
    public String pwdReturnCheck(@PathVariable("email")String email, @PathVariable("merchantUid")String merchantUid ) throws Exception {

        log.info(">>> reservationEmail >> {}",email);
        log.info(">>> merchantUid >> {}",merchantUid);

        emailService.reservationEmail(email, merchantUid);


        return "isOk";

    }

    @GetMapping("adminPayList")
    public String list(Model m, PagingVO pgvo){
        List<ReservationVO> payList = psv.getPayList(pgvo);
        m.addAttribute("payList", payList);
        return "/adminRegister/adminPayList";
    }

    @DeleteMapping(value = "/{reservationNum}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("reservationNum") int reservationNum) {
        int isOk = psv.deleteReservation(reservationNum);
        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK)
                : new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }




    @GetMapping("/reservationCheck")
    public void reservationCheck() {

    }

    @PostMapping("/reservationCheck")
    public void getReservation(@RequestParam String merchantUid, Model m) {

        ReservationVO reservation = psv.getReservation(merchantUid);
        log.info(">>> reservation >> {}", reservation);
        m.addAttribute("reservation", reservation);

    }

    @GetMapping("/portOnePay")
    public void showReservationPage(@RequestParam("startDay") String startDay,
                                    @RequestParam("themeName") String themeName,
                                    @RequestParam("selectedTime") String selectedTime,
                                    Model model) {

        log.info(startDay);
        log.info(themeName);
        log.info(selectedTime);
        // 예약 페이지에서 할 일
        model.addAttribute("startDay", startDay);
        model.addAttribute("themeName", themeName);
        model.addAttribute("selectedTime", selectedTime);




    }
    @GetMapping("/themeReserv")
    public String getAllReservations(Model model) {
        List<ReservationVO> reservations = psv.getAllReservations(); // 모든 예약 정보를 가져옴
        log.info(">>>>>>>>>>>>>{}",reservations);
        model.addAttribute("reservations", reservations); // 모델에 예약 정보를 추가하여 뷰로 전달
        return "/theme/themeReserv"; // 뷰 이름 반환 (예: themeReserv.html)
    }






}
