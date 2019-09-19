package com.siemens.ctc.config;

import com.siemens.ctc.filter.JwtAuthenticationTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    protected UserDetailsService myUserService() {
        return new MyUserDetailService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder.userDetailsService(myUserService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.
                userDetailsService(myUserService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/v1/ctc/auth/**").permitAll()
//                .antMatchers("/v1/ctc/media/media_num").permitAll()
                .antMatchers("/v1/ctc/media/resources").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/ctc/media").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/ctc/media").permitAll()
                .antMatchers("/v1/ctc/media/upload").permitAll()
                .antMatchers(HttpMethod.POST, "/v1/ctc/media/upload").permitAll()
                .antMatchers("/v1/ctc/media/thumbnail/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/ctc/media/thumbnail/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/ctc/media/waterfall").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/ctc/media/download/**").permitAll()
                .antMatchers("/v1/ctc/dir").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/ctc/dir").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/ctc/label/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/ctc/auth/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/ctc/media/resources").permitAll()
                .anyRequest().authenticated();

        http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**");

//        web.ignoring().antMatchers("swagger-ui.html",
//                "/swagger-ui.html/**",
//                "/favicon.ico",
//                "/**/*.css",
//                "/**/*.js",
//                "/**/*.png",
//                "/**/*.gif",
//                "/swagger-resources/**",
//                "/v2/**",
//                "/**/*.ttf");
    }

}
