package br.com.api.reciclapp.reciclapp.exceptions;

public class UsuarioDesconhecidoException extends RuntimeException {
    public UsuarioDesconhecidoException(String message) {
        super(message);
    }
}
