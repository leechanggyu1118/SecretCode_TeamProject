package SecretCode.ezen.www.controller;

import SecretCode.ezen.www.domain.QnaCommentVO;
import SecretCode.ezen.www.domain.QnaVO;
import SecretCode.ezen.www.service.AdminCommentService;
import SecretCode.ezen.www.service.QnaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/adminComment")
@RestController
@Slf4j
@RequiredArgsConstructor
public class AdminCommentController {

    private final AdminCommentService acsv;
    private final QnaService qnaService;

    @PostMapping(value="/post", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> post(@RequestBody QnaCommentVO cvo){
        log.info(">>>> cvo >> {} ", cvo);
        int isOk = acsv.post(cvo);
        if(isOk > 0) {
            qnaService.updateCmtQty(cvo.getBno());
            return new ResponseEntity<>("1", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/{bno}/comments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QnaCommentVO>> list(@PathVariable("bno")int bno) {
        log.info(">>>> bno >> {}", bno);
        List<QnaCommentVO> list = acsv.getList(bno);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping(value="/modify", consumes = "application/json", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> modify(@RequestBody QnaCommentVO cvo){
        log.info(">>>> cvo >> {}", cvo);
        int isOk = acsv.modify(cvo);
        return isOk > 0 ? new ResponseEntity<>("1", HttpStatus.OK) :
                new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(value = "/{cno}/{bno}", produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> remove(@PathVariable("cno") int cno, @PathVariable("bno") int bno){
        int isOk = acsv.delete(cno, bno);
        if(isOk > 0) {
            qnaService.updateCmtQty(bno);
            int updateComments = qnaService.getCommentCount(bno);
            if (updateComments == 0) {
                qnaService.updateCmtStatus(bno);
            }
            return new ResponseEntity<>("1", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("0", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }





}
