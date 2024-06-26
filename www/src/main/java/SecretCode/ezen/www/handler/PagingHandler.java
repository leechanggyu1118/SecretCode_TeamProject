package SecretCode.ezen.www.handler;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaCommentVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter
@Setter
public class PagingHandler {

    private int startPage;
    private int endPage;
    private boolean prev, next;
    private int totalCount;
    private PagingVO pgvo;
    private int realEndPage;

    private List<QnaCommentVO> qcmtList;

    public PagingHandler(PagingVO pgvo, int totalCount) {
        this.pgvo = pgvo;
        this.totalCount = totalCount;

        if (totalCount == 0) {
            this.realEndPage = 1;
            this.endPage = 1;
            this.startPage = 1;
            this.prev = false;
            this.next = false;
        } else {
            this.realEndPage = (int) Math.ceil(totalCount / (double) pgvo.getQty());

            this.endPage = (int) Math.ceil(pgvo.getPageNo() / (double) 10) * 10;

            if (realEndPage < endPage) {
                this.endPage = realEndPage;
            }

            this.startPage = endPage - 9;

            if (this.startPage < 1) {
                this.startPage = 1;
            }

            this.prev = this.startPage > 1;
            this.next = this.endPage < realEndPage;
        }
    }

    // 댓글 리스트를 포함한 생성자
    public PagingHandler(PagingVO pgvo, int totalCount, List<QnaCommentVO> qcmtList) {
        this(pgvo, totalCount);
        this.qcmtList = qcmtList;
    }
}
