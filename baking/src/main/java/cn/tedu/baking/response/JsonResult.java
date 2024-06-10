package cn.tedu.baking.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {

    private Integer code;

    private String msg;

    private Object data;


    public JsonResult(int code, String msg){
        this.code = code;
        this.msg = msg;
    }


    public JsonResult(StatusCode statusCode){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
    }


    public JsonResult(StatusCode statusCode, Object data){
        this.code = statusCode.getCode();
        this.msg = statusCode.getMsg();
        this.data = data;
    }


    public JsonResult(Object data){
        this.code = StatusCode.SUCCESS.getCode();
        this.msg = StatusCode.SUCCESS.getMsg();
        this.data = data;
    }


    public static JsonResult ok(Object data){
        return new JsonResult(data);
    }

    public static JsonResult ok(){
        return ok(null);
    }

}










