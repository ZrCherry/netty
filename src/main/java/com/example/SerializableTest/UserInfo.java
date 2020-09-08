package com.example.SerializableTest;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * @Author zhangrui
 * @time 2020-09-08-9:23
 * @description 功能描述
 */
public class UserInfo implements Serializable {
    private static final long serialVerionUID = 1L;
    private String userName;
    private Integer userId;

    public UserInfo buildUserName(String userName){
        this.userName = userName;
        return this;
    }

    public UserInfo buildUserId(int userId){
        this.userId = userId;
        return this;
    }

    public final String getUserName() {
        return userName;
    }

    public final void setUserName(String userName) {
        this.userName = userName;
    }

    public final int getUserId() {
        return userId;
    }

    public final void setUserId(Integer userId) {
        this.userId = userId;
    }

    public byte[] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }

    public byte[] codeC(ByteBuffer buffer){
        buffer.clear();
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value = null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
