package com.tecode.enumBean;

/**
 * 任务处理理状态枚举类
 */
public enum TaskState {
    HANDLE("handle","处理中"),
    FINISH("finish","已完成");

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    private String type;
    private String typeName;

    private TaskState(String type, String typeName){
        this.type = type;
        this.typeName = typeName;
    }

    /**
     * 根据任务类型返回任务的实例的实例
     * @param type
     * @return
     */
    public static TaskState fromHandleState(String type) {
        for (TaskState handleState : TaskState.values()) {
            if (handleState.type.equals(type)) {
                return handleState;
            }
        }
        return null;
    }
}
