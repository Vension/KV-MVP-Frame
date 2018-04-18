package com.vension.frame.beans;


/**
 * ========================================================
 * 作  者：Vension
 * 日  期：2018/4/18 17:54
 * 描  述：eventBus发送消息帮助类
 * ========================================================
 */

public class BaseEventBusBean<T> {

    private int eventCode = -1;

    private T data;

    public BaseEventBusBean(int eventCode) {
        this.eventCode = eventCode;
    }

    public BaseEventBusBean(int eventCode, T data) {
        this.eventCode = eventCode;
        this.data = data;
    }

    public int getEventCode() {
        return eventCode;
    }

    public T getData() {
        return data;
    }
}
