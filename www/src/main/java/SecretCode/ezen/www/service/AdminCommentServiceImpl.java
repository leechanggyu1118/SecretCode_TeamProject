package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.QnaCommentVO;
import SecretCode.ezen.www.repository.AdminCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AdminCommentServiceImpl implements AdminCommentService {
    private final AdminCommentMapper acMapper;

    @Override
    public int post(QnaCommentVO cvo) {
        return acMapper.insertComment(cvo);
    }

    @Override
    public List<QnaCommentVO> getList(int bno) {
        return acMapper.selectCommentsByBno(bno);
    }

    @Override
    public int modify(QnaCommentVO cvo) {
        return acMapper.updateComment(cvo);
    }

    @Override
    public int delete(int cno, int bno) {
        return acMapper.deleteComment(cno, bno);
    }
}
