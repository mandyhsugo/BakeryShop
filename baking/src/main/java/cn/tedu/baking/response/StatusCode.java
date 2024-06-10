package cn.tedu.baking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum StatusCode {

    SUCCESS(1, "OK"),
    NOT_LOGIN(1000,"Not logged in"),
    LOGIN_SUCCESS(1001,"login successful"),
    PASSWORD_ERROR(1002, "wrong password"),
    USERNAME_ERROR(1003, "username error"),
    USERNAME_ALREADY_EXISTS(1004, "Username exists"),
    FORBIDDEN_ERROR(1005,"No access"),
    OPERATION_SUCCESS(2001, "Successful operation"),
    OPERATION_FAILED(2002, "operation failed"),
    VALIDATE_ERROR(3002, "Parameter verification failed");

    private Integer code;
    private String msg;
}
