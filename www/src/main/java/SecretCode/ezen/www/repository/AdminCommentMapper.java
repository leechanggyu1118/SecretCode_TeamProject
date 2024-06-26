package SecretCode.ezen.www.repository;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.QnaCommentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminCommentMapper {

    int insertComment(QnaCommentVO cvo);

    List<QnaCommentVO> selectCommentsByBno(int bno);

    int updateComment(QnaCommentVO cvo);

    int deleteComment(int cno, int bno);
}
