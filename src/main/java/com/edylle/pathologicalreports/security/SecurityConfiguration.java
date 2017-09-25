package com.edylle.pathologicalreports.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.edylle.pathologicalreports.model.enumeration.RoleEnum;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String REALM = "REPORTS";

	@Autowired
	private LoginDetailsService loginDetail;

	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

	@Autowired
	DataSource dataSource;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new StandardPasswordEncoder();
	}

	@Autowired
	public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginDetail).passwordEncoder(getPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf()
			.disable()
			.authorizeRequests()
				.antMatchers("/recover-password/**").permitAll()
		.and()
			.authorizeRequests()
				.antMatchers("/admin/**").hasRole(RoleEnum.ADMIN.name())
				.antMatchers("/professor/**").hasRole(RoleEnum.PROFESSOR.name())
				.antMatchers("/student/**").hasRole(RoleEnum.STUDENT.name())
		.and()
			.authorizeRequests()
			.antMatchers("/login/select-role").hasAnyRole(RoleEnum.ADMIN.name(), RoleEnum.PROFESSOR.name(), RoleEnum.STUDENT.name())
		.and()
			.formLogin()
			.loginPage("/login")
			.successHandler(customAuthenticationSuccessHandler)
			.failureUrl("/login/login-error")
		.and()
			.rememberMe()
		.and()
	        .logout()
	        .logoutSuccessUrl("/login/logout")
	    .and()
			.exceptionHandling().accessDeniedPage("/login/access-denied")
		.and()
			.httpBasic()
			.realmName(REALM)
			.authenticationEntryPoint(getBasicAuthEntryPoint())
		.and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);

		return db;
	}

	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public CustomBasicAuthenticationEntryPoint getBasicAuthEntryPoint() {
		return new CustomBasicAuthenticationEntryPoint();
	}

	// To allow Pre-flight [OPTIONS] request from browser
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
	}

}