package org.lamb.lambframework.core.enumeration;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public enum ExceptionEnum {
    E000000000("E000000000","未知错误"),

    EC00000000("EC00000000","无法加载切点对象"),
    EC00000001("EC00000001","链接点参数为空"),
    EC00000002("EC00000002","类或对象访问权限限制"),
    EC00000003("EC00000003","I/O异常"),
    EC00000004("EC00000004","OBJ to json 失败"),
    EC00000005("EC00000005","不支持字符编码"),
    EC00000006("EC00000006","没有此算法"),
    EC00000007("EC00000007","缺少算法配置参数"),
    EC00000008("EC00000008","读取私钥失败"),
    EC00000009("EC00000009","加载私钥失败"),
    EC00000010("EC00000010","密文数据已损坏"),
    EC00000011("EC00000011","私钥长度非法"),
    EC00000012("EC00000012","私钥非法"),
    EC00000013("EC00000013","读取公钥失败"),
    EC00000014("EC00000014","加载公钥失败"),
    EC00000015("EC00000015","明文数据已损坏"),
    EC00000016("EC00000016","公钥非法"),
    EC00000017("EC00000017","公钥长度非法"),
    EC00000018("EC00000018","签名失败"),
    EC00000019("EC00000019","验签失败"),
    EL00000000("EL00000000","数据报为空"),
    EL00000001("EL00000001","转换数据报返回为空"),
    EL00000002("EL00000002","目标名称为空"),
    EL00000003("EL00000003","调用API失败"),
    EL00000004("EL00000004","参数名不能为空"),
    EL00000005("EL00000005","参数不能为空");


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
