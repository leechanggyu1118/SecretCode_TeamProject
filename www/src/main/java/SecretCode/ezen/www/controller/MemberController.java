package SecretCode.ezen.www.controller;

import SecretCode.ezen.www.domain.MemberVO;
import SecretCode.ezen.www.domain.QnaVO;
import SecretCode.ezen.www.domain.ReservationVO;
import SecretCode.ezen.www.domain.ReviewVO;
import SecretCode.ezen.www.service.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RequestMapping("/member/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class MemberController {
    private final MemberService msv;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final PaymentService psv;
    private final QnaService qsv;
    private final ReviewService rsv;

    @GetMapping("/login_register")
    public void join() {
    }

    //중복아이디 체크
    @ResponseBody
    @GetMapping(value = "/emailCheck/{email}")
    public String emailCheck(@PathVariable("email")String email) {

        log.info(">>> email >> {}",email);
        int emailCheck = msv.checkEmail(email);
        log.info(">>> emailCheck >> {}",emailCheck);
        String mailCheck = String.valueOf(emailCheck);

        return mailCheck;

    }

    //아이디 찾기 휴대폰번호 체크
    @ResponseBody
    @GetMapping(value = "/phoneCheck/{phone}/{nickName}")
    public List<MemberVO> phoneCheck(@PathVariable("phone")String phone, @PathVariable("nickName")String nickName ) {

        log.info(">>> phone >> {}",phone);
        log.info(">>> nickName >> {}",nickName);

        List<MemberVO> phoneCheck = msv.phoneCheck(phone, nickName);

        log.info(">>> phoneCheck >> {}",phoneCheck);


        return phoneCheck;

    }



    @GetMapping("/adminBoard")
    public void adminBoard(){}

    @PostMapping("/register")
    public String register(MemberVO mvo, Model m){
        mvo.setPwd(passwordEncoder.encode(mvo.getPwd()));
        int isOk = msv.insert(mvo);
        if(isOk > 0){
            m.addAttribute("msg_register", 1);
            return "/index";
        }
        m.addAttribute("msg_register", 2);

        return "/index";

    }

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {

        /* 에러와 예외를 모델에 담아 view resolve */
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);
        return "/member/login_register";
    }

    /* 로그인 */
/*    @GetMapping("/auth/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "exception", required = false) String exception,
                        Model model) {

        *//* 에러와 예외를 모델에 담아 view resolve *//*
        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

    }*/

    @GetMapping("/list")
    public void list(Model m){
        m.addAttribute("list", msv.getList());
    }

    @GetMapping("/modify")
    public void modify(){}

    private void logout(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        new SecurityContextLogoutHandler()
                .logout(request, response, authentication);
    }


    @PostMapping("/modify")
    public String modify(MemberVO mvo, Principal principal,HttpServletRequest request, HttpServletResponse response ){
        String email = principal.getName();
        mvo.setEmail(email);

        if(mvo.getPwd().isEmpty() || mvo.getPwd().length() == 0){
            msv.memberModify(mvo);
        }else {

            String pwdModify = passwordEncoder.encode(mvo.getPwd());
            mvo.setPwd(pwdModify);
            msv.memberPwdModify(mvo);

        }
        logout(request, response);
        return "redirect:/";
    }

    @GetMapping("/deleteMember")
    public String deleteMember(Principal principal, HttpServletRequest request, HttpServletResponse response) {
        String email = principal.getName(); //id

        msv.memberAuthDelete(email);
        msv.memberDelete(email);
        logout(request, response);
        return "redirect:/";
    }

    @GetMapping("/myPage")
    public void myReservation(Principal principal, Model m){

        String email = principal.getName(); //id
        log.info(">>>>>>>!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@ email {}", email);


        //현재 로그인한 아이디의 닉네임 뽑아오기
        String nickName = msv.myNickName(email);
        log.info(">>>>>>>!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@ !!!!!!!!!!!!!!!!nickName {}", nickName);


        //회원일때
        List<ReservationVO> myReservation = psv.getmyReservation(email);

        m.addAttribute("myReservation", myReservation);

        //관리자일때
        List<ReservationVO> memberReservation = psv.getMemberReservation();
        m.addAttribute("memberReservation", memberReservation);



        List<QnaVO> myqna = qsv.myqna(nickName);
        m.addAttribute("myqna", myqna);
        log.info(">>>>>>>!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@ myqna {}", myqna);

        List<QnaVO> memberQna = qsv.memberQna();
        m.addAttribute("memberQna", memberQna);



        List<ReviewVO> myreview = rsv.myreview(nickName);
        m.addAttribute("myreview", myreview);
        log.info(">>>>>>>!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@ myreview {}", myreview);

        String myNickName = msv.myNickName(email);
        m.addAttribute("myNickName", myNickName);

        //role이 안 먹을 떄
        String type = msv.getType(email);
        m.addAttribute("type", type);
        log.info(">>>>>>>!!!!!!!@@@@@@@@@@@@@@@@@@@@@@@@@ type {}", type);





    }




    //구글 이메일 인증
    @PostMapping("/emailConfirm")
    @ResponseBody
    public String emailConfirm(@RequestBody String email) throws Exception {
        log.info(email);

        String confirm = emailService.sendSimpleMessage(email);

        return confirm;
    }

    //비밀번호 재발급 체크
    @ResponseBody
    @GetMapping(value = "/pwdReturnCheck/{phone}/{nickName}/{email}")
    public String pwdReturnCheck(@PathVariable("phone")String phone, @PathVariable("nickName")String nickName, @PathVariable("email")String email ) throws Exception {

        log.info(">>> phone >> {}",phone);
        log.info(">>> nickName >> {}",nickName);
        log.info(">>> email >> {}",email);

        String pwdReturnCheck = msv.pwdReturnCheck(phone, nickName, email);
        log.info(">>> pwdReturnCheck >> {}",pwdReturnCheck);

        emailService.passwordChange(pwdReturnCheck);


        return "isOk";

    }





//    @GetMapping("/myPage")
//    public void myPage(){
//
//    }

    @GetMapping("/findEmailPwd")
    public void findEmail() {
    }









}
