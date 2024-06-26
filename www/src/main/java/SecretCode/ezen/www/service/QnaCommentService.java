package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaCommentVO;
import SecretCode.ezen.www.handler.PagingHandler;

public interface  QnaCommentService {

    int post(QnaCommentVO qcvo);

    PagingHandler getList(long bno, PagingVO pgvo);

    int update(QnaCommentVO qcvo);

    int delete(long cno);


    String myNickName(String email);
}
