package SecretCode.ezen.www.service;


import SecretCode.ezen.www.domain.PagingVO;

import SecretCode.ezen.www.domain.ReviewVO;
import SecretCode.ezen.www.repository.ReviewMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewMapper reviewMapper;
    private PagingVO pgvo;


    public class ReviewNotFoundException extends RuntimeException {

        public ReviewNotFoundException(String message) {
            super(message);
        }
    }

    @Override
    public int register(ReviewVO rvo) {
        return reviewMapper.register(rvo);
    }

    @Override
    public int getTotalCount(PagingVO pgvo) {
        return reviewMapper.getTotalCount(pgvo);
    }

    @Override
    public List<ReviewVO> getList(PagingVO pgvo) {
        return reviewMapper.getList(pgvo);


    }

    @Override
    public void incrementLikeCount(int bno) {
        reviewMapper.incrementLikeCount(bno);
    }

    @Override
    public int getReadCount(int bno) {
        return reviewMapper.getReadCount(bno);
    }

    @Override
    public void decrementLikeCount(int bno) {
        reviewMapper.decrementLikeCount(bno);
    }

    @Override
    public List<ReviewVO> myreview(String nickName) {
        return reviewMapper.myreview(nickName);
    }

    @Override
    public void modify(ReviewVO rvo) {
        reviewMapper.modify(rvo); // ReviewMapper의 modify 메소드를 호출하여 데이터베이스에서 해당 리뷰 정보를 수정합니다.
    }

    @Override
    public ReviewVO findById(int bno) {
        return reviewMapper.findById(bno); // ReviewMapper의 findById 메소드를 호출하여 데이터베이스에서 해당 bno에 해당하는 리뷰 정보를 조회합니다.
    }

    @Override
    public void delete(int bno) {
        reviewMapper.delete(bno); // ReviewMapper의 delete 메소드를 호출하여 데이터베이스에서 해당 bno에 해당하는 리뷰 정보를 삭제합니다.
    }

    @Override
    public List<ReviewVO> getListByTheme(String themeName, PagingVO pgvo) {
        return reviewMapper.getListByTheme(themeName, pgvo);
    }

    @Override
    public int getTotalCountByTheme(String themeName, PagingVO pgvo) {
        return reviewMapper.getTotalCountByTheme(themeName, pgvo);
    }

    @Override
    public String myNickName(String email) {
        return reviewMapper.myNickName(email);
    }


}
