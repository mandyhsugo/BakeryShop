package cn.tedu.baking.exception;



import cn.tedu.baking.response.JsonResult;
import cn.tedu.baking.response.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



//@ControllerAdvice
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler
    public JsonResult doHandleRuntimeException(RuntimeException ex){
        log.error("error is: " + ex.getMessage());


        return new JsonResult(StatusCode.OPERATION_FAILED, ex.getMessage());
    }


    @ExceptionHandler
    public JsonResult doHandleIllegalArgumentException(IllegalArgumentException ex){
        log.error("IllegalArgumentException error is: " + ex.getMessage());
        return new JsonResult(StatusCode.OPERATION_FAILED, ex.getMessage());
    }


    @ExceptionHandler
    public JsonResult doHandleArgumentNotValidException(MethodArgumentNotValidException ex){
        /*
          ex.getFieldError().getDefaultMessage())：Get exception messages in validation
         */
        return new JsonResult(StatusCode.VALIDATE_ERROR, ex.getFieldError().getDefaultMessage());
    }





    //import org.springframework.security.core.AuthenticationException;
    @ExceptionHandler({InternalAuthenticationServiceException.class,
            BadCredentialsException.class})
    public JsonResult handleAuthenticationException(
            AuthenticationException e){
        if (e instanceof InternalAuthenticationServiceException){
            log.warn("Username does not exist!");
            return new JsonResult(StatusCode.USERNAME_ERROR);
        }
        log.warn("wrong password!");
        return new JsonResult(StatusCode.PASSWORD_ERROR);
    }


    @ExceptionHandler(AccessDeniedException.class)
    public JsonResult handleAccessDeniedException(AccessDeniedException e){
        log.error("No access!");
        return new JsonResult(StatusCode.FORBIDDEN_ERROR);
    }


}







