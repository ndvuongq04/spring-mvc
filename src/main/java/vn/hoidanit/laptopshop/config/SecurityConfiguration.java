package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;
import vn.hoidanit.laptopshop.service.CustomUserDetailsService;
import vn.hoidanit.laptopshop.service.UserService;

@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfiguration {

    @Bean
    // dùng để mã hóa mk của người dùng nhập từ form
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // dùng để cung cấp 1 người dùng có kiểu UserDetailsService
    // cung cấp username , password , roles cho AuthenticationManager
    public UserDetailsService userDetailsService(UserService userService) {

        return new CustomUserDetailsService(userService); // tạo ra 1 object có kiểu CustomUserDetailsService
        // CustomUserDetailService implements interface UserDetailsService -> từ class
        // con có thể ép
        // kiểu về class cha ( theo tính đa hình trong OOP )
    }

    @Bean
    // xác thực người dùng
    public DaoAuthenticationProvider authProvider(
            PasswordEncoder passwordEncoder,
            UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder);
        // authProvider.setHideUserNotFoundExceptions(false);
        return authProvider;
    }

    @Bean
    public AuthenticationSuccessHandler customSuccessHandle() {
        return new CustomSuccessHandle();
    }

    @Bean
    public SpringSessionRememberMeServices rememberMeServices() {
        SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
        // optionally customize
        rememberMeServices.setAlwaysRemember(true);
        return rememberMeServices;
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.INCLUDE)
                        .permitAll()

                        .requestMatchers("/", "/login", "/client/**", "/product/**", "/css/**", "/js/**",
                                "/images/**")
                        .permitAll()

                        .requestMatchers("/admin/**").hasRole("ADMIN")

                        .anyRequest().authenticated())

                .sessionManagement((sessionManagement) -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS) // khi người dùng chưa có session -> tạo
                                                                             // mới
                        .invalidSessionUrl("/logout?expired") // hết hạn session -> tự động logout
                        .maximumSessions(1) // tại 1 thời điểm -> chỉ có 1 người đăng nhập đc vào tk đó
                        .maxSessionsPreventsLogin(false)) // nếu có hơn 1 người -> người thứ 2 đăng nhập -> đá người trc
                                                          // ; còn true -> chờ người 1 hết phiên đăng nhập thì người 2
                                                          // mới dc vào
                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true)) // mỗi lần đăng xuất
                                                                                                  // -> xóa cookie này
                                                                                                  // đi , và báo hiệu
                                                                                                  // cho server
                // sử dụng cơ chế remember me
                .rememberMe((rememberMe) -> rememberMe
                        .rememberMeServices(rememberMeServices()))

                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .failureUrl("/login?error")
                        .successHandler(customSuccessHandle())
                        .permitAll())
                .exceptionHandling(ex -> ex
                        .accessDeniedPage("/access-denied"));
        return http.build();
    }

}
