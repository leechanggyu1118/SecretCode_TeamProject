package SecretCode.ezen.www.config;


import SecretCode.ezen.www.handler.authenticationFailureHandler;
import SecretCode.ezen.www.security.CustomUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@Configurable
@RequiredArgsConstructor
public class SecurityConfig {

    private final DefaultOAuth2UserService oAuth2UserService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

        // SecurityFilterChain 객체로 설정
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers
                                ("/index", "/", "/js/**", "/dist/**", "/board/list", "/member/login", "/member/register","review/delete"
                                        ,"/member/login_register","/member/emailCheck","/member/findEmailPwd","/member/phoneCheck","/member/pwdReturnCheck","/member/**", "/upload/**", "/comment/**"
                                        ,"/theme/theme", "/theme/**", "/qna/list", "/qna/**", "/qna/checkSecret", "/oauth2/**","/portOnePay/**","/adminRegister/**" )
                        .permitAll().requestMatchers("/member/list","/review/delete").hasAnyRole("ADMIN")
                        .anyRequest().permitAll()
                )
                .oauth2Login(oauth2 -> oauth2
                        .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/**"))
                        .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
                        .defaultSuccessUrl("/").permitAll()
                )

                .formLogin(login -> login
                        .usernameParameter("email")
                        .passwordParameter("pwd")
                        .loginPage("/member/login")
                        .failureHandler(authenticationFailureHandler()) /* 로그인 실패 핸들러 */
                        .defaultSuccessUrl("/").permitAll()
//                        .defaultSuccessUrl("/theme/mainHome?success=true").permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl(("/member/logout"))
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/")
                );
        return http.build();


    }
//        userDetailsService : spring에서 만든 클래스와 같은 객체

    @Bean
    UserDetailsService userDetailsService() {
        return new CustomUserService();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    //로그인 실패할 때
    @Bean
    AuthenticationFailureHandler authenticationFailureHandler(){
        return new authenticationFailureHandler();
    }
}
