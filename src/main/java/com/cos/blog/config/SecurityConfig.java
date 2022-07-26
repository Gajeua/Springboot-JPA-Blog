package com.cos.blog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.cos.blog.config.auth.PrincipalDetailService;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것.
@Configuration // 빈 등록 (IoC 관리)
@EnableWebSecurity  // 시큐리티 필터가 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 주소로 접근을 하면 권한 및 인증을 미리 체크
public class SecurityConfig {  //extends WebSecurityConfigurerAdapter 
														// 스프링 시큐리티 5.7.1 이상 또는 스프링부트 2.7.0 이상에서는 지원을 안한다. 
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Bean  // IoC가 돼서 리턴하는 객체를 스프링이 관리한다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
	// 시큐리티가 대신 로그인 할때 password를 가로채기를 하는데
	// 해당 password가 어떻게 해쉬가 되어 회원가입이 되었는지 알아야
	// 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교할 수 있다.
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		 return authenticationConfiguration.getAuthenticationManager();
    }
	
//	@Bean
//    AuthenticationManager authenticationManager(AuthenticationManagerBuilder builder) throws Exception {
//        return builder.userDetailsService(principalDetailService).passwordEncoder(encodePWD()).and().build();
//    }
	
//	@Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//
//        AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
//        authenticationManagerBuilder.userDetailsService(userDetailsService);
//        authenticationManager = authenticationManagerBuilder.build();
//
//        http.csrf().disable().cors().disable().authorizeHttpRequests().antMatchers("/api/v1/account/register", "/api/v1/account/auth").permitAll()
//            .anyRequest().authenticated()
//            .and()
//            .authenticationManager(authenticationManager)
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        return http.build();
//    }

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable() // csrf 토큰 비활성화 (테스트시 걸어두는게 좋음)
			.authorizeRequests()	  // 어떤 요청에 대한 인가가 들어오면
				.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")  // /auth이 붙은 모든것에 요청이 들어오면
				.permitAll()  // 모두 permit을 해준다.
				.anyRequest() // 다른 요청들은
				.authenticated()  // 인증이 되어야 한다.
			.and()
				.formLogin()
				.loginPage("/auth/loginForm")  // auth로 갈때 빼고는 전부 인증이 필요함으로 login 폼으로 이동.
				.loginProcessingUrl("/auth/loginProc")  // 스프링 시큐리티가 해당 주소로 요청하는 로그인을 가로채서 대신 로그인.
				.defaultSuccessUrl("/");
//				.failureUrl("/fail"); // 실패했을 경우  
		
		return http.build();
}
	
	
	// 스프링 시큐리티 5.7.1 이상 또는 스프링부트 2.7.0 이상에서는 지원을 안한다. 
//	@Override
//	protected void configure(HttpSecurity http) throws Exception {
//		http
//			.authorizeRequests()	  // 어떤 요청에 대한 인가가 들어오면
//				.antMatchers("/auth/**")  // /auth이 붙은 모든것에 요청이 들어오면
//				.permitAll()  // 모두 permit을 해준다.
//				.anyRequest() // 다른 요청들은
//				.authenticated();  // 인증이 되어야 한다.
//	}
	
}
