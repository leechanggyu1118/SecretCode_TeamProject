package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.QnaCommentVO;

import java.util.List;

public interface AdminCommentService {
    int post(QnaCommentVO cvo);
    List<QnaCommentVO> getList(int bno);
    int modify(QnaCommentVO cvo);
    int delete(int cno, int bno);
}
