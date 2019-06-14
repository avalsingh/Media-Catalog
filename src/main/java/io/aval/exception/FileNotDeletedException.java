package io.aval.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_MODIFIED, reason="File Not Deleted")
public class FileNotDeletedException extends Exception{

	private static final long serialVersionUID = 2204205128782957297L;

}
