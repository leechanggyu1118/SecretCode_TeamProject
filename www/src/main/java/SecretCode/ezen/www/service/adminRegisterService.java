package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.*;

import java.util.List;

public interface adminRegisterService {
    int insert(adRegisterVO advo);

    List<MemberVO> getList();

    int deleteUser(String email);

    int deleteAuthUser(String email);

    int getTotalCount();

    List<MemberVO> getListWithPaging(PagingVO pagingVO);

    List<QnaVO> getBoardList(PagingVO pgvo);

    int getBoardTotalCount(PagingVO pgvo);

    int insertFile(FileVO fileVO);

    int insertWithFiles(adRegisterVO advo, List<FileVO> fileVOList);

    List<adRegisterVO> getreservationList();

    List<ThemeVO> getThemeNum();

    int deleteTheme(int themeNum);

    int getTotalCountWithAuth(PagingVO pgvo);

    int grantAdmin(String email);

    int revokeAdmin(String email);

    int hasAdminRole(String email);

    List<AuthVO> getAuthListByEmail(String email);
}
