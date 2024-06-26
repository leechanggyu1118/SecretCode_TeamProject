package SecretCode.ezen.www.security;

import SecretCode.ezen.www.domain.MemberVO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Getter
public class AuthMember extends User {
    private MemberVO mvo;

    public AuthMember(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
    public AuthMember(MemberVO mvo){
        super(mvo.getEmail(), mvo.getPwd(),
                mvo.getAuthList().stream().map(
                        authVO -> new SimpleGrantedAuthority(authVO.getAuth())
                ).collect(Collectors.toList()) );
        this.mvo = mvo;
    }


}
