package vn.hoidanit.laptopshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

}
