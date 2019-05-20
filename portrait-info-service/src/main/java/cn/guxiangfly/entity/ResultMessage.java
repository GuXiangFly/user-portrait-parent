package cn.guxiangfly.entity;

public class ResultMessage {
    //状态 fail 、 success
    private String status;
    //消息内容
    private String message;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
