package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaCommentVO;
import SecretCode.ezen.www.handler.PagingHandler;
import SecretCode.ezen.www.repository.QnaCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class QnaCommentServiceImpl implements QnaCommentService{


    private final QnaCommentMapper qnacommentMapper;


    @Override
    public int post(QnaCommentVO qcvo) {

        return qnacommentMapper.post(qcvo);
    }

    @Transactional
    @Override
    public PagingHandler getList(long bno, PagingVO pgvo) {
        //totalCount
        int totalCount = qnacommentMapper.bnoToTotalCount(bno);
        //Comment List
        List<QnaCommentVO> qcmtList = qnacommentMapper.getList(bno,pgvo);

        return new PagingHandler(pgvo,totalCount,qcmtList);
    }

    @Override
    public int update(QnaCommentVO cvo) {
        return qnacommentMapper.update(cvo);
    }

    @Override
    public int delete(long cno) {
        return qnacommentMapper.delete(cno);
    }

    @Override
    public String myNickName(String email) {
        return qnacommentMapper.myNickName(email);
    }


}
