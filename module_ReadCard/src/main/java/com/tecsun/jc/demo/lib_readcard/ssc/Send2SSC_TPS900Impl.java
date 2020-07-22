package com.tecsun.jc.demo.lib_readcard.ssc;

import android.util.Log;

import com.tecsun.jc.base.listener.apdu.ISend2SSC;
import com.tecsun.jc.base.utils.HexUtil;
import com.telpo.tps550.api.reader.SmartCardReader;
import com.telpo.tps550.api.util.StringUtil;
import com.telpo.tps550.api.util.SystemUtil;

/**
 * 发送APDU指令 针对TPS900机器
 * @author liuDongWen
 * @date 2019/10/30
 */
public final class Send2SSC_TPS900Impl implements ISend2SSC {
    private SmartCardReader reader;
    public Send2SSC_TPS900Impl(SmartCardReader reader) {
        this.reader = reader;
    }

    @Override
    public byte[] begin(String order) {
        if(reader == null){
            return null;
        }

        byte[] data;
        byte[] apdu = HexUtil.hexStringToByte(order);
        if (SystemUtil.getDeviceType() == StringUtil.DeviceModelEnum.TPS360IC.ordinal()) {
            data = reader.transmit(apdu, 1);
        } else {
            data = reader.transmit(apdu);
        }

        if(data == null){
            Log.e("TAG", " 获取指令后,返回的数据是null " );
        }
        return data;
    }
}
