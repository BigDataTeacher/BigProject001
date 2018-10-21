package com.tecode.enumBean;

/**
 *评论者类型枚举类
 */
public enum CommentatorType {
    SYSTEM("system","系统"),
    USER("user","用户");
    private String type;
    private String typeName;

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getType() {

        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    /**
     * 构造器
     * @param type
     * @param typeName

     */
    private CommentatorType(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    /**
     * 根据枚举的type值 返回枚举的类型
     * @param type
     * @return
     */
    public static CommentatorType fromCommentatorType(String type) {
        for (CommentatorType commentatorType : CommentatorType.values()) {
            if (commentatorType.type.equals(type)) {
                return commentatorType;
            }
        }
        return null;
    }
}
