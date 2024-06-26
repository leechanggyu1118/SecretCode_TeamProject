package SecretCode.ezen.www.repository;

import SecretCode.ezen.www.domain.AuthVO;
import SecretCode.ezen.www.domain.MemberVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MemberMapper {


    int insert(MemberVO mvo);

    int insertAuth(String email);

    MemberVO selectEmail(String username);

    List<AuthVO> selectAuths(String username);

    List<MemberVO> getList();

    void memberPwdModify(MemberVO mvo);

    void memberAuthDelete(String email);

    void memberDelete(String email);

    void memberModify(MemberVO mvo);

    MemberVO getUser(String email);

    int checkEmail(String email);

    void deleteUser(String email);

    void deleteAuth(String email);

    MemberVO checkSocialLogin(String userId);

    List<MemberVO> phoneCheck(@Param("phone")String phone, @Param("nickName")String nickName);

    String pwdReturnCheck(@Param("phone")String phone, @Param("nickName")String nickName, @Param("email")String email);

    void changePwd(@Param("email")String email, @Param("pwd")String pwd);

    List<MemberVO> getNickName(String name);

    String getType(String name);

    void insertSocialAuth(String userId);

    String myNickName(String email);
}
