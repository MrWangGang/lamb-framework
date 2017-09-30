package com.lamb.lambframework.core.enumeration;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public enum ExceptionEnum {

    ANALYSIS_ASPECT_ERROR("1000444","无法加载切点对象"),
    ANALYSIS_ASPECT_OBJECT_NULL("1000445","链接点参数为空"),

    SERVICE_RETURN_VALUE_IS_NOT_STRING("100011","此业务切点返回值必须为String类型"),

    ILLEGAL_ACCESS("1000","类或对象访问权限限制"),
    INSTANTIATION_EXCEPTION("100223","实例化异常"),
    IO_ERRO("1000","I/O异常"),

    ZMXY_API_ERROR_METHOD_IS_NULL("10001","method为空"),
    ZMXY_API_ERROR_PARAMS_IS_NULL("12001","params为空"),
    ZMXY_API_INVOKE_ERROR("120021","调用芝麻信用API失败"),
    ZMXY_API_RETURN_ERROR("120022","芝麻信用API接口返回了一个未知错误"),
    ZMXY_API_VERIFY_SIGN_ERROR("120023","芝麻信用验签失败"),
    JXL_API_INVOKE_ERROR("120024","调用聚信立API失败"),
    JSON_CONVERT_DATAPOOR_NULL_POINT("10003","数据报为空"),
    JSON_CONVERT_DATAPOOR_MAP_NULL_POINT("10004","转换数据报返回为空"),
    JSON_CONVERT_TARGET_NAME_NULL("10005","目标名称为空"),
    JSON_CONVERT_OBJ_ERROR("10007","OBJ to json 失败"),
    //=================================================================
    ALGORITHM_UNSUPPORTED_ENCODING_EXCEPTION("4000","不支持字符编码"),
    ALGORITHM_NOSUCH_ALGORITHM_EXCEPTION("4001","没有此算法"),
    ALGORITHM_NO_SUCH_PADDING_EXCEPTION("4003","缺少算法配置参数"),

    ALGORITHM_PRI_IO_EXCEPTION("4004","读取私钥失败"),
    ALGORITHM_PRI_INVALID_KEY_EXCEPTION("4005","加载私钥失败"),
    ALGORITHM_PRI_BAD_PADDING_EXCEPTION("4006","密文数据已损坏"),
    ALGORITHM_PRI_ILLEGAL_BLOCK_SIZE_EXCEPTION("4007","私钥长度非法"),
    ALGORITHM_PRI_INVALID_KEY_SPEC_EXCEPTION("4008","私钥非法"),

    ALGORITHM_PUB_IO_EXCEPTION("4009","读取公钥失败"),
    ALGORITHM_PUB_INVALID_KEY_EXCEPTION("4010","加载公钥失败"),
    ALGORITHM_PUB_BAD_PADDING_EXCEPTION("4011","明文数据已损坏"),
    ALGORITHM_PUB_INVALID_KEY_SPEC_EXCEPTION("4012","公钥非法"),
    ALGORITHM_PUB_ILLEGAL_BLOCK_SIZE_EXCEPTION("4013","公钥长度非法"),

    ALGORITHM_SIGNATURE_EXCEPTION("4014","签名失败"),
    ALGORITHM_VERIFY_SIGNATURE("4015","验签失败"),

    ANNOTATION_NOT_NULL("5000","注解类型不能为空"),
    PARAM_NAME_NOT_NULL("5001","参数名不能为空"),
    PARAM_NOT_NULL("5002","参数不能为空");

    // 成员变量
    private String code;
    private String message;

    // 构造方法
    private ExceptionEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
