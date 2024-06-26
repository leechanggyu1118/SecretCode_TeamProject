package SecretCode.ezen.www.security;

import SecretCode.ezen.www.domain.MemberVO;
import SecretCode.ezen.www.repository.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService {

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MemberVO loginMvo = memberMapper.selectEmail(username);
        loginMvo.setAuthList(memberMapper.selectAuths(username));
        //userDetails return
        return new AuthMember(loginMvo);
    }
}
