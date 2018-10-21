package com.tecode.enumBean;

/**
 * 评论内容类型 枚举类
 */
public enum TaskCommentType {

    TEXT("text", "文本"),
    VOICE("voice", "语音"),
    IMAGE("image", "图片"),
    URL("url", "链接");
    private String type;
    private String typeName;

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setType(String type) {

        this.type = type;
    }

    public String getType() {

        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    private TaskCommentType(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    /**
     * 根据评论类型返回评论的实例
     * @param type
     * @return
     */
    public static TaskCommentType fromTaskCommentType(String type) {
        for (TaskCommentType taskCommentType : TaskCommentType.values()) {
            if (taskCommentType.type.equals(type)) {
                return taskCommentType;
            }
        }
        return null;
    }
}
