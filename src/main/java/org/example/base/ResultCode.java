package org.example.base;

/**
 * @author nishch
 * @version 1.0
 * @date 2020/3/11
 * @des  返回的结果码
 */
public enum ResultCode {
    SUCCESS("00000","code"),
    SUCCESS2("200","code");

    private String index;
    private String name;

    private ResultCode(String index, String name){
        this.index = index;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static String getName(String index) {
        for (ResultCode schemeTypeEnum : ResultCode.values()) {
            if (index.equals(schemeTypeEnum.getIndex())) {
                return schemeTypeEnum.getName();
            }
        }
        return null;
    }
}
