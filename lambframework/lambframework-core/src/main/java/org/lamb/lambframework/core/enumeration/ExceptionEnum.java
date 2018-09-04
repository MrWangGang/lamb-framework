package org.lamb.lambframework.core.enumeration;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public enum ExceptionEnum {

    E000000000("E000000000","操作成功"),
    ES00000001("ES00000001","链接点参数为空"),
    ES00000002("ES00000002","类或对象访问权限限制"),
    ES00000003("ES00000003","I/O异常"),
    ES00000005("ES00000005","不支持字符编码"),
    ES00000006("ES00000006","没有此算法"),
    ES00000007("ES00000007","缺少算法配置参数"),
    ES00000008("ES00000008","读取私钥失败"),
    ES00000009("ES00000009","加载私钥失败"),
    ES00000010("ES00000010","密文数据已损坏"),
    ES00000011("ES00000011","私钥长度非法"),
    ES00000012("ES00000012","私钥非法"),
    ES00000013("ES00000013","读取公钥失败"),
    ES00000014("ES00000014","加载公钥失败"),
    ES00000015("ES00000015","明文数据已损坏"),
    ES00000016("ES00000016","公钥非法"),
    ES00000017("ES00000017","公钥长度非法"),
    ES00000018("ES00000018","签名失败"),
    ES00000019("ES00000019","系统错误"),
    ES00000020("ES00000020","解析失败"),
    ES00000021("ES00000021","调用失败"),
    ES00000022("ES00000022","BeanPlasticityUtill异常"),
    ES00000023("ES00000023","类型不匹配String"),
    ES00000024("ES00000024","要转成date的value不能为空"),
    ES00000025("ES00000025","日期转化失败"),
    ES00000026("ES00000026","接口返回类型必须为LambResponseTemplete"),
    EI00000001("EI00000001","必入参数未填写");

    // 成员变量
    private String code;

    private String message;
    // 构造方法
    private ExceptionEnum(String code,String message) {
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
