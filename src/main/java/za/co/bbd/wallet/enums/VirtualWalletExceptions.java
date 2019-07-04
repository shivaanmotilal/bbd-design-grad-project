package za.co.bbd.wallet.enums;

import org.springframework.http.HttpStatus;

public enum VirtualWalletExceptions {

    USER_EXISTS(HttpStatus.BAD_REQUEST, "The user already exists"),
    CLIENT_NOT_FOUND(HttpStatus.NOT_FOUND, "No client found for Client ID: [%s]"),
    TECHNICAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "A technical error occurred when processing request");

    final HttpStatus httpStatus;
    final String statusDescription;

    VirtualWalletExceptions(final HttpStatus httpStatus, final String statusDescription) {
        this.httpStatus = httpStatus;
        this.statusDescription = statusDescription;
    }

    public String getStatusDescription() {
        return this.statusDescription;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

}