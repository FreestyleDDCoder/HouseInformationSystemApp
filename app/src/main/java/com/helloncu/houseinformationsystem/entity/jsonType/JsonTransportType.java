package com.helloncu.houseinformationsystem.entity.jsonType;

/**
 * Created by liangzhan on 18-3-16.
 * 这是对数据传送Json格式实体
 */
public class JsonTransportType {
    //包含传输的对象，业务的标号，信息名，信息ID号
    private Object object;
    private String messageName;
    private String messageId;
    private String serviceName;

    //一般从ESB到CORE端无需设置messageName和ID
    public JsonTransportType(Object object, String messageName, String messageId, String serviceName) {
        this.object = object;
        this.messageName = messageName;
        this.messageId = messageId;
        this.serviceName = serviceName;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
