package SecretCode.ezen.www.controller;


import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaCommentVO;
import SecretCode.ezen.www.handler.PagingHandler;
import SecretCode.ezen.www.service.QnaCommentService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequestMapping("/comment/*")
@RestController
@Slf4j
@RequiredArgsConstructor
public class QnaCommentController {

    private final QnaCommentService qcsv;


    @PostMapping("/post")
    @ResponseBody
    public  String post(@RequestBody QnaCommentVO qcvo){
        int isOk = qcsv.post(qcvo);
        return  isOk > 0 ? "1" : "0";

    }


    @GetMapping("/list/{bno}/{page}")
    @ResponseBody
    public PagingHandler list(@PathVariable("bno")long bno, @PathVariable("page")int page, Principal principal, Model m){
        log.info(">>>bno>>page >>{}"+bno+"/"+page);

        PagingVO pgvo = new PagingVO(page, 5);

        String NickName = "비회원"; // 인증되지 않은 사용자를 위한 기본 닉네임 설정

        if (principal != null) {
            String email = principal.getName(); // 인증된 사용자의 ID (보통 username 또는 email)
            NickName = qcsv.myNickName(email); // 실제 인증된 사용자의 닉네임 가져오기
            log.info(">>>>>   NickName   {}", NickName);
        } else {
            log.info(">>>>>   NickName   {}", NickName);
        }

        m.addAttribute("NickName", NickName);
        //Comment List
        //totalCount
        PagingHandler ph = qcsv.getList(bno,pgvo);
        return ph;
    }

    @PutMapping("/update")
    @ResponseBody
    public String update(@RequestBody QnaCommentVO qcvo){
        log.info(">>>qcvo>>>{}",qcvo);

        int isOk=qcsv.update(qcvo);




        return  isOk > 0 ? "1" : "0";
    }

    @ResponseBody
    @DeleteMapping("/remove/{cno}")
    public  String remove(@PathVariable("cno")long cno){
        int isOk=qcsv.delete(cno);
        return  isOk > 0 ? "1" : "0";
    }







}
