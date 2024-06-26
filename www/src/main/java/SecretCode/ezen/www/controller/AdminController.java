package SecretCode.ezen.www.controller;

import SecretCode.ezen.www.domain.QnaVO;
import SecretCode.ezen.www.service.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/*")
public class AdminController {
    private final AdminService asv;

    @GetMapping("/adminDetail")
    public String adminDetail(@RequestParam("bno") int bno, Model m) {
        QnaVO list = asv.getAdminDetail(bno);
        m.addAttribute("list", list);
        return "/adminRegister/adminDetail";
    }

    @DeleteMapping(value="/admin/{bno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> delete(@PathVariable("bno") int bno) {
        log.info("bno {}", bno);
        int isOk = asv.deleteQna(bno);

        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK) :
                new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/adminReservation")
    public String adminReservation(){
        return "/member/adminReservation";
    }

}
