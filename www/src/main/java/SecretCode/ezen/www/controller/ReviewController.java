package SecretCode.ezen.www.controller;


import SecretCode.ezen.www.domain.*;
import SecretCode.ezen.www.handler.PagingHandler;
import SecretCode.ezen.www.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.context.Theme;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/review/*")
public class ReviewController {

    private final ReviewService rsv;

    private final ThemeService themeService; // ThemeService 주입


    @GetMapping("/list")
    public String list(@RequestParam(value = "themeName", required = false) String themeName,
                       Model model, PagingVO pgvo, Principal principal) {
        log.info(">>>pgvo>>{}", pgvo);

        List<ReviewVO> reviewList;
        int totalCount;

        String NickName = "비회원"; // 기본값 설정

        if (principal != null) {
            String email = principal.getName(); //id
            NickName = rsv.myNickName(email);
            log.info(">>>>> NickName123>>>{}",NickName);
        }

        model.addAttribute("NickName", NickName);

        if (themeName != null && !themeName.isEmpty()) {
            // 선택된 테마의 리뷰 목록 가져오기
            reviewList = rsv.getListByTheme(themeName, pgvo);
            totalCount = rsv.getTotalCountByTheme(themeName, pgvo);
        } else {
            // 전체 리뷰 목록 가져오기
            reviewList = rsv.getList(pgvo);
            totalCount = rsv.getTotalCount(pgvo);
        }

        // PagingHandler 객체 생성
        PagingHandler ph = new PagingHandler(pgvo, totalCount);

        // 모델에 리뷰 목록, 페이징 정보, 선택된 테마 이름 추가하여 뷰로 전달
        model.addAttribute("list", reviewList);
        model.addAttribute("ph", ph);
        model.addAttribute("selectedTheme", themeName); // 선택된 테마 이름 추가

        return "review/list"; // 뷰 이름 반환
    }

















    // 테마 이름 목록을 모델에 추가하는 메서드
    @ModelAttribute("themeList")
    public List<String> themeList() {
        List<ThemeVO> themes = themeService.getAllThemes();
        return themes.stream().map(ThemeVO::getThemeName).collect(Collectors.toList());
    }


    @GetMapping("/register")
    public String registerForm(Model model,Principal principal) {
        List<ThemeVO> themeList = themeService.getAllThemes();
        model.addAttribute("themeList", themeList);
        model.addAttribute("theme", "");
        model.addAttribute("themeText", "");

        String email = principal.getName(); //id
        String NickName = rsv.myNickName(email);
        model.addAttribute("NickName", NickName);
        log.info(">>>>>   NickName   {}",NickName);

        return "review/register";
    }

    @PostMapping("/register")
    public String register(ReviewVO rvo, @RequestParam("themeUuid") String themeUuid, Model model) {
        log.info(">>>reviewVO>>{}", rvo);

        // 선택된 테마의 UUID로 테마 정보를 조회
        ThemeVO theme = themeService.getThemeDetailsByUuid(themeUuid);

        // 테마가 null인 경우 처리



        rvo.setThemeName(theme.getThemeName());

        // ReviewService를 사용하여 ReviewVO를 저장
        rvo.setUuid(themeUuid); // 선택된 테마의 UUID를 리뷰 객체에 설정

        rsv.register(rvo);

        return "redirect:/review/list"; // 등록 후 Review 목록 페이지로 리다이렉트
    }


    @GetMapping("/getThemeDetails")
    public ResponseEntity<ThemeVO> getThemeDetails(@RequestParam("themeNum") Long themeNum) {
        ThemeVO theme = themeService.getThemeDetails(themeNum);
        if (theme != null) {
            return ResponseEntity.ok(theme);
        } else {
            return ResponseEntity.notFound().build();
        }
    }












    // 리뷰 수정 화면
    @GetMapping("/modify/{bno}")
    public String modifyForm(@PathVariable int bno, Model model) {
        // bno를 이용해 수정할 리뷰 정보를 조회하여 모델에 담습니다.
        ReviewVO rvo = rsv.findById(bno); // 예시 메서드, 실제로는 데이터베이스 조회 등으로 대체해야 합니다.
        model.addAttribute("rvo", rvo);
        return "modifyForm"; // 수정 폼의 Thymeleaf 템플릿 이름
    }

    // 리뷰 수정 처리
    @PostMapping("/modify")
    public String modify(@ModelAttribute ReviewVO rvo) {
        rsv.modify(rvo);
        return "redirect:/review/list";
    }

    @PostMapping("/delete")
    public String delete(ReviewVO rvo) {
log.info(">>>>11111>{}",rvo);
        rsv.delete(rvo.getBno());
        return "redirect:/review/list";
    }
















}










