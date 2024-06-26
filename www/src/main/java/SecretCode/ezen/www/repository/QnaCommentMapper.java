package SecretCode.ezen.www.repository;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface QnaCommentMapper {

    int post(QnaCommentVO qcvo);

    int bnoToTotalCount(long bno);


    List<QnaCommentVO> getList(@Param("bno") long bno, @Param("pgvo") PagingVO pgvo);


    int update(QnaCommentVO cvo);

    int delete(long cno);


    void count(QnaCommentVO qcvo);

    String myNickName(String email);
}
