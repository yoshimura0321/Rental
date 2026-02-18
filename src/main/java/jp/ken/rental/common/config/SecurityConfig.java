package jp.ken.rental.common.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
	
	private DataSource dataSource;
	
	public SecurityConfig(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	@Bean
	protected SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		http.authorizeHttpRequests(authorize -> authorize
				.requestMatchers("/home").permitAll()
				.requestMatchers("/regist").permitAll()
				.requestMatchers("/cart").authenticated()
				.requestMatchers("/cart/add").authenticated()
				.requestMatchers("/mypage").authenticated()
				.requestMatchers("/mypage/**").authenticated()
				.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.requestMatchers("/admin").hasAuthority("ROLE_ADMIN")
				.requestMatchers("/css/**").permitAll()
				.anyRequest().authenticated());
		
		http.formLogin(login -> login
				.defaultSuccessUrl("/home")
				.loginPage("/login")
				.failureUrl("/login?error")
				.permitAll());
		
		http.exceptionHandling(e ->e
				.accessDeniedPage("/accessdenied"));
		
		
		return http.build();
	}
	
	@Bean
	protected UserDetailsManager userDetailsManager() {
		JdbcUserDetailsManager users = new JdbcUserDetailsManager(this.dataSource);
		return users;
	}
}
