package SecretCode.ezen.www.repository;


import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.ReviewVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {

    int register(ReviewVO rvo);

    int getTotalCount(PagingVO pgvo);

    List<ReviewVO> getList(PagingVO pgvo);

    List<ReviewVO> myreview(String email);


    void incrementLikeCount(int bno);

    int getReadCount(int bno);

    void decrementLikeCount(int bno);


    void modify(ReviewVO rvo);

    ReviewVO findById(int bno);

    void delete(int bno);


    List<ReviewVO> getListByTheme(String themeName, PagingVO pgvo);

    int getTotalCountByTheme(String themeName, PagingVO pgvo);


    String myNickName(String email);
}
