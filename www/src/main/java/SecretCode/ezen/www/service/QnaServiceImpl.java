package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaVO;
import SecretCode.ezen.www.repository.QnaMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService {

    private final QnaMapper qnaMapper;

    @Override
    public List<QnaVO> getList(PagingVO pgvo) {
        List<QnaVO> noticeList = qnaMapper.getNoticeList();
        List<QnaVO> regularList = qnaMapper.getList(pgvo);
        List<QnaVO> combinedList = new ArrayList<>();
        combinedList.addAll(noticeList);
        combinedList.addAll(regularList);
        return combinedList;
    }

    @Override
    public int getTotalCount(PagingVO pgvo) {
        return qnaMapper.getTotalCount(pgvo);
    }

    @Override
    public int register(QnaVO qvo) {
        return qnaMapper.register(qvo);
    }

    @Override
    public String getSecretByBno(long bno) {
        return qnaMapper.getSecretByBno(bno);
    }

    @Override
    public boolean validatePassword(long bno, String password) {
        QnaVO qnaVO = qnaMapper.getQnaByBno(bno);
        return qnaVO != null && qnaVO.getScpwd() != null && qnaVO.getScpwd().equals(password);
    }

    @Override
    public void updateCmtQty(int bno) {
        qnaMapper.updateCmtQty(bno);
    }

    @Override
    public int getCommentCount(int bno) {
        return qnaMapper.getCommentCount(bno);
    }

    @Override
    public void updateCmtStatus(int bno) {
        qnaMapper.updateCmtStatus(bno);
    }

    @Override
    public List<QnaVO> myqna(String nickName) {
        return qnaMapper.myqna(nickName);
    }

    @Override
    public List<QnaVO> memberQna() {
        return qnaMapper.memberQna();
    }

    @Override
    public String myNickName(String email) {
        return qnaMapper.myNickName(email);
    }


    @Override
    public QnaVO getDetail(int bno) {
        return qnaMapper.getDetail(bno);
    }

    @Override
    public void remove(long bno) {
        qnaMapper.remove(bno);
    }

    @Override
    public int readCount(int bno) {
        return qnaMapper.readCount(bno);
    }

    @Override
    public void modify(QnaVO qvo) {
        qnaMapper.modify(qvo);
    }






}
