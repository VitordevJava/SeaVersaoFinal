package br.com.seatecnologia.hub.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a configuração de segurança.
 */
@SpringBootTest(classes = SecurityConfig.class)
public class SecurityConfigTest {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void testUserDetailsService() {
        // Verifica se o serviço de detalhes de usuário foi criado corretamente
        assertNotNull(userDetailsService, "UserDetailsService não deve ser nulo");

        // Verifica se o usuário admin existe e tem as propriedades corretas
        UserDetails adminUser = userDetailsService.loadUserByUsername("admin");
        assertNotNull(adminUser, "Usuário admin não deve ser nulo");
        assertTrue(adminUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")),
                "Usuário admin deve ter a role ADMIN");
        assertTrue(passwordEncoder.matches("123qw@ADMIN", adminUser.getPassword()),
                "Senha do usuário admin deve corresponder a '123qw@ADMIN'");

        // Verifica se o usuário padrão existe e tem as propriedades corretas
        UserDetails standardUser = userDetailsService.loadUserByUsername("padrao");
        assertNotNull(standardUser, "Usuário padrão não deve ser nulo");
        assertTrue(standardUser.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_USER")),
                "Usuário padrão deve ter a role USER");
        assertTrue(passwordEncoder.matches("123qw@USER", standardUser.getPassword()),
                "Senha do usuário padrão deve corresponder a '123qw@USER'");
    }

    @Test
    public void testPasswordEncoder() {
        // Verifica se o codificador de senha foi criado corretamente
        assertNotNull(passwordEncoder, "PasswordEncoder não deve ser nulo");

        // Verifica se o codificador de senha funciona corretamente
        String rawPassword = "testPassword";
        String encodedPassword = passwordEncoder.encode(rawPassword);
        
        assertNotEquals(rawPassword, encodedPassword, "Senha codificada não deve ser igual à senha original");
        assertTrue(passwordEncoder.matches(rawPassword, encodedPassword), 
                "PasswordEncoder deve verificar corretamente a senha");
    }
}