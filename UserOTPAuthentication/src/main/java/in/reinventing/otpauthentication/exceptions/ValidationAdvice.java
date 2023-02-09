package in.reinventing.otpauthentication.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ValidationAdvice {

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String,String> invalidArgument(MethodArgumentNotValidException ex){
		Map<String,String> error =new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(e->{
			String fieldName = ((FieldError) e).getField();
	        String errorMessage = e.getDefaultMessage();
	        error.put(fieldName, errorMessage);
		});
		return error;
	}
}
