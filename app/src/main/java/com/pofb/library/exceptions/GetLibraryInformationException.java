package com.pofb.library.exceptions;

import java.io.IOException;

public class GetLibraryInformationException extends IOException {
    public GetLibraryInformationException(String msg) {
        super(msg);
    }

    // contrói um objeto NumeroNegativoException com mensagem e a causa dessa exceção, utilizado para encadear exceptions
    public GetLibraryInformationException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
