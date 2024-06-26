package SecretCode.ezen.www.controller;


import SecretCode.ezen.www.domain.*;
import SecretCode.ezen.www.handler.PagingHandler;
import SecretCode.ezen.www.service.MemberService;
import SecretCode.ezen.www.service.QnaCommentService;
import SecretCode.ezen.www.service.QnaService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/qna/*")
public class QNAController {
    private final QnaService qsv;
    private final QnaCommentService qcsv;
    private final MemberService msv;




    @GetMapping("/list")
    public String list(Model model, PagingVO pgvo) {
        log.info(">>>pgvo>>{}", pgvo);

        // Q&A 목록을 가져오는 서비스 호출
        List<QnaVO> qnaList = qsv.getList(pgvo);


        // totalCount를 DB에서 가져오기
        int totalCount = qsv.getTotalCount(pgvo); // 검색어도 함께 고려


        // PagingHandler 객체 생성
        PagingHandler ph = new PagingHandler(pgvo, totalCount);

        // 모델에 Q&A 목록과 페이징 정보를 추가하여 뷰로 전달
        model.addAttribute("list", qnaList);
        model.addAttribute("ph", ph);


        return "qna/list"; // 뷰 이름 반환
    }
    @GetMapping("/register")
    public void register(Model model, Principal principal) {
        String NickName = "비회원"; // 인증되지 않은 사용자를 위한 기본 닉네임 설정

        if (principal != null) {
            String email = principal.getName(); // 인증된 사용자의 ID (보통 username 또는 email)
            NickName = qsv.myNickName(email); // 실제 인증된 사용자의 닉네임 가져오기
            log.info(">>>>>   NickName   {}", NickName);
        } else {
            log.info(">>>>>   NickName   {}", NickName);
        }

        model.addAttribute("NickName", NickName);
    }

    @PostMapping("/register")
    public String register(QnaVO qvo) {
        // 비밀글 체크박스 처리
        log.info(">>>qvo>>{}",qvo);

        int isOk = qsv.register(qvo);

        return "redirect:/qna/list";
    }




    @GetMapping("/detail")
    public String detail(@RequestParam("bno") int bno, Model m,Principal principal) {
        log.info(">>bno>>{}",bno);
        QnaVO qvo= qsv.getDetail(bno);
        String NickName = "비회원"; // 인증되지 않은 사용자를 위한 기본 닉네임 설정

        if (principal != null) {
            String email = principal.getName(); // 인증된 사용자의 ID (보통 username 또는 email)
            NickName = qsv.myNickName(email); // 실제 인증된 사용자의 닉네임 가져오기
            log.info(">>>>>   NickName   {}", NickName);
        } else {
            log.info(">>>>>   NickName   {}", NickName);
        }

        m.addAttribute("NickName", NickName);
        int isOk = qsv.readCount(bno);
        log.info(">>qvo>>{}",qvo);
        m.addAttribute("qvo", qvo);


        return "/qna/detail";

    }

    @PostMapping("/modify")
    public String modify(QnaVO qvo){
        qsv.modify(qvo);
        return "redirect:/qna/detail?bno="+qvo.getBno();
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno")long bno){
        qsv.remove(bno);
        return "redirect:/qna/list";
    }
    @PostMapping("/validatePassword")
    @ResponseBody
    public String validatePassword(@RequestParam("bno") long bno, @RequestParam("password") String password) {
        // 입력된 비밀번호와 QnaVO 객체에 저장된 비밀번호를 비교합니다.
        boolean isValid = qsv.validatePassword(bno, password);
        return String.valueOf(isValid);
    }

}
