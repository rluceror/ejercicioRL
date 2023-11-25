package cl.ejercicio.utilities.jwt;

import cl.ejercicio.data.EjercicioRepository;
import cl.ejercicio.utilities.jwt.exceptions.AuthenticationEntryPointHandler;
import cl.ejercicio.utilities.jwt.exceptions.CustomAccessDeniedHandler;

import cl.ejercicio.utilities.jwt.exceptions.CustomLogoutHandler;
import cl.ejercicio.utilities.jwt.exceptions.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMethod;

import static cl.ejercicio.utilities.AppConstant.API_REST_PROTECTED_DOSIGNOUT;
import static cl.ejercicio.utilities.AppConstant.API_REST_PROTECTED_GETUSERS;
import static cl.ejercicio.utilities.AppConstant.API_REST_UNPROTECTED_DOSIGNIN;
import static cl.ejercicio.utilities.AppConstant.HAS_ROL_ADMIN;
import static cl.ejercicio.utilities.AppConstant.HAS_ROL_USER;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired JwtTokenProvider jwtTokenProvider;
  @Autowired
  EjercicioRepository ejercicioRepository;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.httpBasic()
        .disable()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(API_REST_UNPROTECTED_DOSIGNIN)
        .permitAll()
        .antMatchers(API_REST_PROTECTED_GETUSERS)
        .hasAnyRole(HAS_ROL_USER, HAS_ROL_ADMIN)
        .anyRequest()
        .authenticated()
        .and()
        .apply(new JwtConfigurer(jwtTokenProvider))
        .and()
        .logout()
        .logoutRequestMatcher(
            new AntPathRequestMatcher(
                API_REST_PROTECTED_DOSIGNOUT, RequestMethod.POST.toString(), true))
        .invalidateHttpSession(true)
        .clearAuthentication(true)
        .deleteCookies("SESSION")
        .deleteCookies("JSESSIONID")
        .addLogoutHandler(new CustomLogoutHandler(jwtTokenProvider, ejercicioRepository))
        .logoutSuccessHandler(new CustomLogoutSuccessHandler())
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(new AuthenticationEntryPointHandler())
        .accessDeniedHandler(new CustomAccessDeniedHandler());
  }
}
