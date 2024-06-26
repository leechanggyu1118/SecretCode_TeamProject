package SecretCode.ezen.www.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/map")
@Controller
@Slf4j
@RequiredArgsConstructor
public class mapController {

    @GetMapping("/comePage")
    public void come(){
    }
}
