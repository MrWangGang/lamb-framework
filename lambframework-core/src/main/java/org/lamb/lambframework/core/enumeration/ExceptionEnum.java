package org.lamb.lambframework.core.enumeration;

/**
 * Created by WangGang on 2017/6/22 0022.
 * E-mail userbean@outlook.com
 * The final interpretation of this procedure is owned by the author
 */
public enum ExceptionEnum {
    E000000000("E000000000"),

    EC00000000("EC00000000"),
    EC00000001("EC00000001"),
    EC00000002("EC00000002"),
    EC00000003("EC00000003"),
    EC00000004("EC00000004"),
    EC00000005("EC00000005"),
    EC00000006("EC00000006"),
    EC00000007("EC00000007"),
    EC00000008("EC00000008"),
    EC00000009("EC00000009"),
    EC00000010("EC00000010"),
    EC00000011("EC00000011"),
    EC00000012("EC00000012"),
    EC00000013("EC00000013"),
    EC00000014("EC00000014"),
    EC00000015("EC00000015"),
    EC00000016("EC00000016"),
    EC00000017("EC00000017"),
    EC00000018("EC00000018"),
    EC00000019("EC00000019"),
    EL00000000("EL00000000"),
    EL00000001("EL00000001"),
    EL00000002("EL00000002"),
    EL00000003("EL00000003"),
    EL00000004("EL00000004"),
    EL00000005("EL00000005");


    // 成员变量
    private String code;
    // 构造方法
    private ExceptionEnum(String code) {
        this.code = code;

    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
