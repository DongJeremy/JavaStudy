package org.cloud.ssm.common.config;

import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.cloud.ssm.common.security.api.ApiJWTAuthenticationFilter;
import org.cloud.ssm.common.security.api.ApiJWTAuthorizationFilter;
import org.cloud.ssm.common.security.form.CustomAuthenticationFailureHandler;
import org.cloud.ssm.common.security.form.CustomAuthenticationSuccessHandler;
import org.cloud.ssm.common.security.form.CustomLogoutSuccessHandler;
import org.cloud.ssm.common.security.form.FormBasedJWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class MultiHttpSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private UserDetailsService userDetailsService;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        }

        // @formatter:off
        protected void configure(HttpSecurity http) throws Exception {
            http.csrf().disable().antMatcher("/api/**").authorizeRequests().antMatchers("/api/v1/user/signup")
                    .permitAll().anyRequest().authenticated().and().exceptionHandling()
                    .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED)).and()
                    .addFilter(new ApiJWTAuthenticationFilter(authenticationManager()))
                    .addFilter(new ApiJWTAuthorizationFilter(authenticationManager())).sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
        // @formatter:on
    }

    @Order(2)
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private UserDetailsService userDetailsService;

        @Autowired
        private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

        @Autowired
        private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        }

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Bean
        public UsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
            /*
             * All the configuration you do in http.formLogin().x().y().z() is applied to
             * the standard UsernamePasswordAuthenticationFilter not the custom filter you
             * build. You will need to configure it manually yourself. My auth filter
             * initialization looks like this:
             */
            FormBasedJWTAuthenticationFilter authFilter = new FormBasedJWTAuthenticationFilter();
            authFilter.setAuthenticationManager(authenticationManager);
            authFilter.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/login", "POST"));
            authFilter.setAuthenticationSuccessHandler(customAuthenticationSuccessHandler);
            authFilter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
            authFilter.setUsernameParameter("username");
            authFilter.setPasswordParameter("password");
            return authFilter;
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.addFilterAfter(customAuthenticationFilter(), LogoutFilter.class);
            http.csrf().disable()
                .authorizeRequests(authorize -> authorize
                    .mvcMatchers("/webjars/**", "/static/**", "/login").permitAll()
                    .mvcMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('USER')")
                    .anyRequest().denyAll()
                );
            http.formLogin().loginPage("/login").loginProcessingUrl("/login").successForwardUrl("/admin/main")
                    .and().csrf().requireCsrfProtectionMatcher(new RestRequestMatcher()).and()
                    .logout().permitAll().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                    .logoutSuccessHandler(new CustomLogoutSuccessHandler()).deleteCookies("JSESSIONID")
                    .logoutSuccessUrl("/login").and().exceptionHandling();
        }
        
        private class RestRequestMatcher implements RequestMatcher {
            private Pattern allowedMethods = Pattern.compile("^(GET|POST|PUT|PATCH|DELETE)$");
            private RegexRequestMatcher apiMatcher = new RegexRequestMatcher("/v[0-9]*/.*", null);

            @Override
            public boolean matches(HttpServletRequest request) {
                // No CSRF due to allowedMethod
                if (allowedMethods.matcher(request.getMethod()).matches())
                    return false;
                // No CSRF due to api call
                if (apiMatcher.matches(request))
                    return false;
                // CSRF for everything else that is not an API call or an
                // allowedMethod
                return true;
            }
        }

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers("/webjars/**", "/static/**");
        }
    }
}
