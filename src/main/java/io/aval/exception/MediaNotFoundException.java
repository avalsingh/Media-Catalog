package io.aval.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason = "File Not Found")
public class MediaNotFoundException extends Exception {

	private static final long serialVersionUID = -5787559562731613597L;

}
