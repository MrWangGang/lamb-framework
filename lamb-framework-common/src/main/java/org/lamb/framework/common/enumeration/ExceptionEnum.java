package org.lamb.framework.common.enumeration;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public enum ExceptionEnum {

    E000000000("E000000000","操作成功"),
    //系统异常-普通异常
    ES00000000("ES00000000","系统错误"),
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
    ES00000019("ES00000019","JSON转换OBJ失败"),
    ES00000020("ES00000020","OBJ转换JSON失败"),
    ES00000021("ES00000021","调用失败"),
    ES00000022("ES00000022","BeanPlasticityUtill异常"),
    ES00000023("ES00000023","类型不匹配String"),
    ES00000024("ES00000024","要转成date的value不能为空"),
    ES00000025("ES00000025","日期转化失败"),
    ES00000026("ES00000026","redis链接失败"),
    ES00000027("ES00000027","空指针异常"),
    ES00000028("ES00000028","machineId Id can't be greater than %d or less than 0"),
    ES00000029("ES00000029","datacenterId can't be greater than MAX_DATACENTER_NUM or less than 0"),
    ES00000030("ES00000030","务必初始化状态机图谱"),
    ES00000031("ES00000031","状态机没有查找到有效的执行方法"),
    ES00000032("ES00000032","状态机无法匹配状态,请传入有效的状态"),
    ES00000033("ES00000033","状态机没有匹配到有效的event"),
    ES00000034("ES00000034","该事物状态不符合状态机中已配置流转关系"),
    ES00000035("ES00000035","MD5加密失败"),
    ES00000036("ES00000036","时钟回拨,GUID算法无法生成新的ID"),
    ES00000037("ES00000037","GUID生成新的ID发生未知异常"),

    ES00000099("ES00000099","方法缺少必入参数"),

    //系统异常-spring security 异常 - 權限類異常

    EA00000000("EA00000000","身份认证失败"), //AuthenticationException
    EA00000001("EA00000001","访问被拒绝"), //AccessDeniedException
    EA00000002("EA00000002","访问用户为空"),
    EA00000003("EA00000003","无效令牌"),
    EA00000004("EA00000004","无效用户"),
    EA00000005("EA00000005","无效的用户签名值"),
    EA00000006("EA00000006","无效的用户类型"),
    EA00000007("EA00000007","令牌格式不符合规范"),
    EA00000008("EA00000008","无法获取spring security全局上下文变量"),
    EA00000009("EA00000009","无效的用户类型"),
    EA00000010("EA00000010","当前活动用户与获取的用户类型不匹配"),








    EI00000000("EI00000000","接口缺少必入参数");

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
