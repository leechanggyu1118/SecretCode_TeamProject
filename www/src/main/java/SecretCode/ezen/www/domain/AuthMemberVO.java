package SecretCode.ezen.www.domain;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthMemberVO {
    private String auth;
    private String email;
}
