package SecretCode.ezen.www.controller;

import SecretCode.ezen.www.service.NoticeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/notice/*")
public class NoticeController {
    private final NoticeService nsv;

    @GetMapping("/notice")
    public String notice(){

        return "/notice";



    }


}
