package SecretCode.ezen.www.service;

import SecretCode.ezen.www.domain.MemberVO;
import SecretCode.ezen.www.repository.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
//    private final ad

    @Override
    public int insert(MemberVO mvo) {
        // 아이디가 중복되는 경우 회원가입 실패
        // 아이디만 주고, DB에서 일치하는 mvo 객체를 리턴 => 일치하는 객체가 있따면 가입실패 / 없으면 가능.
        MemberVO temMapper = memberMapper.getUser(mvo.getEmail());
        if(temMapper != null) {
            // 기존 아이디가 있는 경우
            return 0;
        }
        //아이디가 중복되지 않는다면... 회원가입 진행.
        //password가 null이거나 값이 없다면... 가입 불가
        if(mvo.getEmail() == null || mvo.getEmail().length() == 0) {
            return 0;
        }
        if(mvo.getPwd() == null || mvo.getPwd().length() == 0) {
            return 0;
        }
        mvo.setType("SecretCode");

        int isOk = memberMapper.insert(mvo);
        return (isOk > 0 ? memberMapper.insertAuth(mvo.getEmail()) : 0);
    }



    @Override
    public List<MemberVO> getList() {
        List<MemberVO> list = memberMapper.getList();
        for(MemberVO mvo : list){
            mvo.setAuthList((memberMapper.selectAuths(mvo.getEmail())));
        }
        return list;
    }

    @Override
    public void memberModify(MemberVO mvo) {
        memberMapper.memberModify(mvo);

    }

    @Override
    public void memberPwdModify(MemberVO mvo) {
        memberMapper.memberPwdModify(mvo);
    }

    @Override
    public void memberAuthDelete(String email) {
        memberMapper.memberAuthDelete(email);
    }

    @Override
    public void memberDelete(String email) {
        memberMapper.memberDelete(email);
    }

    @Override
    public MemberVO isUser(MemberVO mvo) {
        // 로그인 유저 확인
        MemberVO tempMapper = memberMapper.getUser((mvo.getEmail())); //회원가입 했을 때 썼던 메서드 활용

        //해당 아이디가 없으면...
        if(tempMapper == null) {
            return null;
        }

        //matches(원래비번, 암호화된 비번) 비교
        if(passwordEncoder.matches(mvo.getPwd(), tempMapper.getPwd())) {
            return tempMapper;
        }

        return null;
    }

    @Override
    public int checkEmail(String email) {
        return memberMapper.checkEmail(email);
    }

    @Override
    public List<MemberVO> phoneCheck(String phone, String nickName) {
        return memberMapper.phoneCheck(phone, nickName);
    }

    @Override
    public String pwdReturnCheck(String phone, String nickName, String email) {
        return memberMapper.pwdReturnCheck(phone, nickName, email);
    }

    @Override
    public void changePwd(String email, String ePw) {

        String pwd = passwordEncoder.encode(ePw);




        memberMapper.changePwd(email, pwd);
    }

    @Override
    public List<MemberVO> getNickName(String name) {
        return memberMapper.getNickName(name);
    }

    @Override
    public String getType(String name) {
        return memberMapper.getType(name);
    }

    @Override
    public String myNickName(String email) {
        return memberMapper.myNickName(email);
    }


}
