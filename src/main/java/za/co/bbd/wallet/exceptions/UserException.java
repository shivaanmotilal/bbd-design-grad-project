package za.co.bbd.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.FOUND,
        reason = "CustomerEntity already exists"
)
public class UserException extends Exception {
    public UserException() { super(); }
    public UserException(String message) { super(message); }
}
