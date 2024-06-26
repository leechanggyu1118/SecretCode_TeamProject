package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaVO;

import java.util.List;

public interface QnaService {

    List<QnaVO> getList(PagingVO pgvo);

    int getTotalCount(PagingVO pgvo);






    QnaVO getDetail(int bno);


    void modify(QnaVO qvo);

    void remove(long bno);

    int readCount(int bno);


    int register(QnaVO qvo);

    /* boolean checkSecret(long bno, String password);*/


    String getSecretByBno(long bno);

    boolean validatePassword(long bno, String password);

    void updateCmtQty(int bno);

    int getCommentCount(int bno);

    void updateCmtStatus(int bno);

    List<QnaVO> myqna(String nickName);


    List<QnaVO> memberQna();

    String myNickName(String email);
}
