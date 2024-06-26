package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.PagingVO;
import SecretCode.ezen.www.domain.ReviewVO;

import java.util.List;

public interface ReviewService {


    int register(ReviewVO rvo);

    int getTotalCount(PagingVO pgvo);

    List<ReviewVO> getList(PagingVO pgvo);

    List<ReviewVO> myreview(String nickName);


    void incrementLikeCount(int bno);

    int getReadCount(int bno);

    void decrementLikeCount(int bno);


    void modify(ReviewVO rvo);

    ReviewVO findById(int bno);

   


    List<ReviewVO> getListByTheme(String themeName, PagingVO pgvo);

    int getTotalCountByTheme(String themeName, PagingVO pgvo);

    String myNickName(String email);

    void delete(int bno);
}
