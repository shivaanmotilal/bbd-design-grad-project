package za.co.bbd.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends Exception {
    public UnauthorizedException() { super(); }
    public UnauthorizedException(String message) { super(message); }
}
