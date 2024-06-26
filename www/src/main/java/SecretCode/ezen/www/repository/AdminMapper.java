package SecretCode.ezen.www.repository;

import SecretCode.ezen.www.domain.QnaVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminMapper {

    QnaVO getAdminDetail(int bno);

    int deleteQna(int bno);
}
