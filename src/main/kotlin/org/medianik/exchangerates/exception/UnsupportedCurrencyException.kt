package org.medianik.exchangerates.exception

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class UnsupportedCurrencyException(vararg currencies: String) :
    RuntimeException("All or few currencies of ${currencies.contentToString()} are not supported")
