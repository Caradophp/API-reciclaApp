package br.com.api.reciclapp.reciclapp.utils;

import br.com.api.reciclapp.reciclapp.entity.Session;
import br.com.api.reciclapp.reciclapp.repository.UsuarioRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.util.*;

@Service
public class CustomBasicAuthFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager em;

    private UUID sessionid = null;

    public CustomBasicAuthFilter(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var headerAuthorization = request.getHeader("Authorization");
        Cookie[] cookies = request.getCookies();

        boolean sessionCookieExists = false;

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("useridsession")) {
                    sessionid = UUID.fromString(cookie.getValue());
                    sessionCookieExists = true;
                }
            }
        }

        if (request.getRequestURI().equals("/usuarios/login")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!sessionCookieExists) {
            throw new AccessDeniedException("Usuário não logado");
        }

        if (sessionid != null) {
            Session query = em.createQuery("from Session s where s.id = :id and s.expired_at > current_timestamp()", Session.class)
                    .setParameter("id", sessionid)
                    .getSingleResult();

            if (query == null) {
                throw new AccessDeniedException("Usuário não logado");
            }

            if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ") || !sessionCookieExists) {
                filterChain.doFilter(request, response);
                return;
            }

            String base64Credentials = headerAuthorization.substring(6);
            byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
            String decodedString = new String(decodedBytes);
            String[] credentials = decodedString.split(":", 2);

            String useremail = credentials[0];
            String password = credentials[1];

            usuarioRepository.getUserInfoByEmail(useremail).ifPresent(usuario -> {
                if (passwordEncoder.matches(password, usuario.getSenha())) {
                    var authToken = new UsernamePasswordAuthenticationToken(
                            usuario.getEmail(),
                            null,
                            List.of()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            });

            filterChain.doFilter(request, response);
        }
    }
}

