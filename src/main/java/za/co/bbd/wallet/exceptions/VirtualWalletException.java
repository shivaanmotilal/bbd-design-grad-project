package za.co.bbd.wallet.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR,
        reason = "An internal Virtual Wallet error has occurred")
public class VirtualWalletException {

    private HttpStatus status;
    private String message;
    private List<String> errors;

    public VirtualWalletException(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public VirtualWalletException(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
}
