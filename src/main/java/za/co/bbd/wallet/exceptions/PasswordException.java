package za.co.bbd.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "Passwords do not match"
)
public class PasswordException extends Exception {
    public PasswordException() { super(); }
    public PasswordException(String message) { super(message); }
}
