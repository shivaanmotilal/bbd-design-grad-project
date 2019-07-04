package za.co.bbd.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "CustomerEntity not found")
public class NotFoundException extends Exception {
    public NotFoundException() { super(); }
    public NotFoundException(String message) { super(message); }
}
