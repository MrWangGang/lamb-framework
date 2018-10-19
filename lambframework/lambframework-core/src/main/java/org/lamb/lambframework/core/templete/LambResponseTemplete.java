package org.lamb.lambframework.core.templete;


import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.lamb.lambframework.core.config.JsonSymbolicFinalConfig;

/**
 * Created by WangGang on 2017/7/4 0004.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public class LambResponseTemplete {

    private String service_code;

    private String service_message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public String getService_code() {
        if(StringUtils.isBlank(service_code)){
            service_code = JsonSymbolicFinalConfig.DEFAULT_SUCCESS_SERVICE_CODE;
        }
        return service_code;
    }

    public void setService_code(String service_code) {
        this.service_code = service_code;
    }

    public String getService_message() {
        if(StringUtils.isBlank(service_message)){
            service_message = JsonSymbolicFinalConfig.DEFAULT_SUCCESS_SERVICE_MESSAGE;
        }
        return service_message;
    }

    public void setService_message(String service_message) {
        this.service_message = service_message;
    }

    public Object getData() {

        if(data == null){
            return null;
        }
        return data;
    }

    private void setData(Object data) {
        this.data = data;
    }

    public LambResponseTemplete(Object data){
        this.data = data;
    }

    public LambResponseTemplete(){

    }
}
