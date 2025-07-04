package br.com.seatecnologia.hub.exception;

// Exceção lançada quando o CPF já está cadastrado
public class CPFUniqueException extends RuntimeException {

    // Construtor com mensagem padrão
    public CPFUniqueException() {
        super("CPF já cadastrado no sistema.");
    }

    // Construtor com mensagem personalizada
    public CPFUniqueException(String message) {
        super(message);
    }

    // Construtor com mensagem e causa
    public CPFUniqueException(String message, Throwable cause) {
        super(message, cause);
    }
}