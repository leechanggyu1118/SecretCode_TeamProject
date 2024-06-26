package SecretCode.ezen.www.controller;

import SecretCode.ezen.www.domain.*;
import SecretCode.ezen.www.handler.FileHandler;
import SecretCode.ezen.www.handler.PagingHandler;
import SecretCode.ezen.www.service.adminRegisterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequestMapping("/adminRegister/*")
@Slf4j
@RequiredArgsConstructor
@Controller
public class adminRegisterController {
    private final adminRegisterService arsv;
    private final FileHandler fhd;

    @GetMapping("/adminBoard")
    public String adminBoard(Model m, PagingVO pgvo, @RequestParam(required = false) String type, @RequestParam(required = false) String keyword, @RequestParam(defaultValue = "1") int page) {
        pgvo.setPageNo(page);
        pgvo.setType(type);
        pgvo.setKeyword(keyword);

        List<QnaVO> qnaList = arsv.getBoardList(pgvo);
        int totalCount = arsv.getBoardTotalCount(pgvo);
        PagingHandler ph = new PagingHandler(pgvo, totalCount);

        m.addAttribute("list", qnaList);
        m.addAttribute("ph", ph);
        m.addAttribute("type", type);
        m.addAttribute("keyword", keyword);

        return "/adminRegister/adminBoard";
    }



    @GetMapping("/adminRegister")
    public void register(Model m) {
        List<ThemeVO> getThemeNum = arsv.getThemeNum();

        m.addAttribute("getThemeNum",getThemeNum);
    }

    @GetMapping("/adminThemeList")
    public void adminThemeList(Model m) {
        m.addAttribute("reservationList", arsv.getreservationList());

    }

    @PostMapping("/adminRegister")
    public String insert(adRegisterVO arvo, @RequestParam("files") MultipartFile[] files) {
        log.info("arvo {}", arvo);

        List<FileVO> fileVOList = fhd.uploadFiles(files);


        // 첫 번째 파일의 UUID를 arvo 객체에 설정
        FileVO firstFile = fileVOList.get(0);
        arvo.setUuid(firstFile.getUuid());

        int isOk = arsv.insertWithFiles(arvo, fileVOList);

        if (isOk > 0) {
            return "redirect:/adminRegister/adminThemeList";
        } else {
            return "redirect:/adminRegister/adminRegister";
        }
    }


    //삭제할 테마
    @GetMapping("/admainReservationList")
    public void list(Model m){
        m.addAttribute("reservationList", arsv.getreservationList());
    }




    @GetMapping("/adminUser")
    public String list(Model m, PagingVO pgvo, @RequestParam(required = false) String auth, @RequestParam(required = false) String keyword) {
        log.info(">>>pgvo>>{}", pgvo);

        if ("선택".equals(auth)) {
            pgvo.setAuth(null);
        } else {
            pgvo.setAuth(auth);
        }

        pgvo.setKeyword(keyword);

        if (pgvo.getPageNo() < 1) {
            pgvo.setPageNo(1);
        }

        List<MemberVO> memberList = arsv.getListWithPaging(pgvo);
        int totalCount = arsv.getTotalCountWithAuth(pgvo);

        log.info("totalCount: {}", totalCount);
        PagingHandler ph = new PagingHandler(pgvo, totalCount);

        for (MemberVO member : memberList) {
            List<AuthVO> authList = arsv.getAuthListByEmail(member.getEmail());
            member.setAuthList(authList);
        }

        m.addAttribute("list", memberList);
        m.addAttribute("ph", ph);
        m.addAttribute("auth", auth);
        m.addAttribute("keyword", keyword);

        return "/member/adminUser";
    }









    @DeleteMapping(value = "/delete/{email}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delete(@PathVariable("email") String email){
        log.info("email {}", email);
        int isOk = arsv.deleteAuthUser(email);
        arsv.deleteUser(email);

        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK) :
                new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @DeleteMapping(value = "/deleteTheme/{themeNum}")
    @ResponseBody
    public String deleteTheme(@PathVariable("themeNum") int themeNum){
        log.info("themeNum >>>> {}", themeNum);

        int isOk = arsv.deleteTheme(themeNum);

        log.info("isOk >>>> {}", isOk);


        return isOk>0? "1":"0";
    }


    @PostMapping(value="/auth/grant/{email}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> grantAdmin(@PathVariable("email") String email) {
        log.info("grant: {}", email);
        if (arsv.hasAdminRole(email) > 0) {
            return new ResponseEntity<>("YES_ROLE", HttpStatus.OK);
        }
        int isOk = arsv.grantAdmin(email);
        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK)
                : new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value="/auth/revoke/{email}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> revokeAdmin(@PathVariable("email") String email) {
        log.info("reboke: {}", email);
        if (arsv.hasAdminRole(email) == 0) {
            return new ResponseEntity<>("NO_ROLE", HttpStatus.OK);
        }
        int isOk = arsv.revokeAdmin(email);
        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK)
                : new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
