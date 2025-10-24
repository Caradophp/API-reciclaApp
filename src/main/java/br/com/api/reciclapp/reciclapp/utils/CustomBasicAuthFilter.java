package br.com.api.reciclapp.reciclapp.utils;

import br.com.api.reciclapp.reciclapp.entity.Usuario;
import br.com.api.reciclapp.reciclapp.repository.UsuarioRepository;
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
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class CustomBasicAuthFilter extends OncePerRequestFilter {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public CustomBasicAuthFilter(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var headerAuthorization = request.getHeader("Authorization");

        Cookie[] cookies = request.getCookies();

        if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
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
                        usuario.getNome(),
                        null,
                        List.of()
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        });
        filterChain.doFilter(request, response);
    }

//        if (cookies == null) {
//            throw new AccessDeniedException("Usuário não logado");
//        }

//        if (Arrays.stream(cookies).anyMatch(c -> c.getName().equals("useridsession")) || true) {
//            Optional<Usuario> user = usuarioRepository
//                    .findById(Long.valueOf(Arrays.stream(cookies).filter(c -> c.getName().equals("useridsession")).toString()));
//
////            if (user.isEmpty()) {
////                throw new AccessDeniedException("Usuário não encontrado");
////            }
//
//            user.ifPresent(u -> {
//                if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
//                    try {
//                        filterChain.doFilter(request, response);
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    } catch (ServletException e) {
//                        throw new RuntimeException(e);
//                    }
//                    return;
//                }
//
//                String base64Credentials = headerAuthorization.substring(6);
//                byte[] decodedBytes = Base64.getDecoder().decode(base64Credentials);
//                String decodedString = new String(decodedBytes);
//                String[] credentials = decodedString.split(":", 2);
//
//                String useremail = credentials[0];
//                String password = credentials[1];
//
//                usuarioRepository.getUserInfoByEmail(useremail).ifPresent(usuario -> {
//                    if (passwordEncoder.matches(password, usuario.getSenha())) {
//                        var authToken = new UsernamePasswordAuthenticationToken(
//                                usuario.getNome(),
//                                null,
//                                List.of()
//                        );
//                        SecurityContextHolder.getContext().setAuthentication(authToken);
//                    }
//                });
//                try {
//                    filterChain.doFilter(request, response);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                } catch (ServletException e) {
//                    throw new RuntimeException(e);
//                }
//            });

    }

