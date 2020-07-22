package com.tecsun.jc.base.listener.apdu;

/**
 * 发送APDU指令
 * @author liuDongWen
 * @date 2019/10/30
 */
public interface ISend2SSC {
    byte[] begin(String order);
}
