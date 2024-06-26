package SecretCode.ezen.www.repository;

import SecretCode.ezen.www.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface adminRegisterMapper {
    int insert(adRegisterVO advo);

    List<MemberVO> getList();

    int deleteUser(String email);

    int deleteAuthUser(String email);

    int getTotalCount();

    List<MemberVO> getListWithPaging(PagingVO pagingVO);

    List<QnaVO> getNoticeList();

    List<QnaVO> getBoardList(PagingVO pgvo);

    int getBoardTotalCount(PagingVO pgvo);

    int insertFile(FileVO fileVO);

    List<adRegisterVO> getreservationList();

    List<ThemeVO> getThemeNum();

    int deleteTheme(int themeNum);

    int getTotalCountWithAuth(PagingVO pgvo);

    int grantAdmin(@Param("email") String email);

    int revokeAdmin(@Param("email") String email);

    int hasAdminRole(@Param("email") String email);

    List<AuthVO> getAuthList(@Param("email") String email);

    List<AuthVO> getAuthListByEmail(String email);
}
