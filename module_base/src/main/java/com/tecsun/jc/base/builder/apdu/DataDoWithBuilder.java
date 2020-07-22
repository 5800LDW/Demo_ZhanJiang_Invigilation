package com.tecsun.jc.base.builder.apdu;

import android.util.Log;

import com.tecsun.jc.base.listener.IEvents;
import com.tecsun.jc.base.listener.apdu.ISend2SSC;
import com.tecsun.jc.base.utils.HexUtil;
import com.tecsun.jc.base.utils.log.LogUtil;

import java.util.HashMap;


/**
 * 处理APDU的数据
 * @author liuDongWen
 * @date 2019/10/30
 */
public final class DataDoWithBuilder {
    public DataDoWithBuilder() {
        contentHashMap.clear();
    }
    /**
     * 姓名
     */
    public final String NAME_ORDER = "00B2020420";
    /**
     * 社保卡号
     */
    public final String SOCIAL_SECURITY_CARD_NUMBER_ORDER = "00B207040B";
    /**
     * 社会保障号
     */
    public final String SOCIAL_NUMBER_ORDER = "00B2010414";
    public final String[] NORMAL_STEP_ORDER = new String[]{
            "00A404000F7378312E73682EC9E7BBE1B1A3D5CF",
            "00A4020002EF05",
            SOCIAL_SECURITY_CARD_NUMBER_ORDER,
            "0020000003888888",
            //"0020000003123456",
            "00A4000002EF06",
            SOCIAL_NUMBER_ORDER,
            NAME_ORDER};

    public final HashMap contentHashMap = new HashMap<String, String>();

    /**
     * 发送数据和处理数据
     *
     * @param iSend2SSC 具体发送数据到社保卡的实现类
     * @param iEvents 处理完成后的回调 , 数据都在contentHashMap里面了,自己拿
     */
    public final void doWithData(ISend2SSC iSend2SSC , IEvents iEvents) {
        LogUtil.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> doWithData");
        int size = NORMAL_STEP_ORDER.length;
        for (int i = 0; i < size; i++) {
            if (convertAPDUData(NORMAL_STEP_ORDER[i],iSend2SSC)) {
                try{
                    Thread.sleep(20);
                }catch (InterruptedException e){
                    LogUtil.e(">>>>>>>>>>>>>>>>>> InterruptedException : " + e);
                }
                continue;
            } else if (NORMAL_STEP_ORDER[i].contains("888888")) {
                continue;
            } else {
                break;
            }
        }
        if(iEvents!=null){
            iEvents.biz();
        }
    }

    private final boolean convertAPDUData(String order, ISend2SSC iSend2SSC) {
        Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>> 指令: order = " + order);

        byte[] data;
        byte[] apdu = HexUtil.hexStringToByte(order);
        try {
            //TODO 发送指令
            //data = iccard.apduComm(apdu);

            data = iSend2SSC.begin(order);

            if(data == null){
                Log.e("TAG", " 获取指令后,返回的数据是null " );
            }

            Log.e("TAG", " 获取指令后,返回的数据: " + HexUtil.bcd2str(data));
            if (null != data) {
                String s1 = HexUtil.bcd2str(data);
                Log.e("TAG", " 解析得到的数据:  s1 = " + s1);
                //最后一个判断是过滤掉第一个指令的返回的解析数据
                if (s1.contains("9000") || order.contains("8888") || order == NORMAL_STEP_ORDER[0]) {
                    String result = HexUtil.bcd2str(data).replaceAll("9000", "").replaceAll("00", "");
                    //姓名
                    if (order.equals(NAME_ORDER)) {

                        String subresult = result.substring(4, result.length());
                        String utf8 = HexUtil.hexGBK2String(subresult);
                        Log.e("TAG", utf8);
                        contentHashMap.put(NAME_ORDER, utf8);

                        //社保卡号
                    } else if (order.equals(SOCIAL_SECURITY_CARD_NUMBER_ORDER)) {
                        String subresult = result.substring(4, result.length());
                        String sbkh = "";
                        for (int i = 1; i < subresult.length(); ) {
                            sbkh += subresult.charAt(i);
                            i = i + 2;
                        }
                        Log.e("TAG", sbkh);
                        contentHashMap.put(SOCIAL_SECURITY_CARD_NUMBER_ORDER, sbkh);

                        //社会保障号
                    } else if (order.equals(SOCIAL_NUMBER_ORDER)) {
                        String subresult = result.substring(4, result.length());
                        String sfzh = "";
                        for (int i = 1; i < subresult.length(); ) {
                            sfzh += subresult.charAt(i);
                            i = i + 2;
                        }
                        Log.e("TAG", sfzh);
                        contentHashMap.put(SOCIAL_NUMBER_ORDER, sfzh);
                    }
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("TAG", ">>>>>>>>>>>>>>>>>>>>> 报错了 " + e);
        }

//        if (isCardExists == 1) {
//            Toast.makeText(getActivity(), "读卡错误,请退出重试", Toast.LENGTH_LONG).show();
//        }
//        new Handler().postDelayed(runnable, 300);
        return false;
    }


    private final   byte[] toByteArray(String hexString) {
        int hexStringLength = hexString.length();
        byte[] byteArray = null;
        int count = 0;
        char c;
        int i;

        // Count number of hex characters
        for (i = 0; i < hexStringLength; i++) {

            c = hexString.charAt(i);
            if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F' || c >= 'a' && c <= 'f') {
                count++;
            }
        }

        byteArray = new byte[(count + 1) / 2];
        boolean first = true;
        int len = 0;
        int value;
        for (i = 0; i < hexStringLength; i++) {

            c = hexString.charAt(i);
            if (c >= '0' && c <= '9') {
                value = c - '0';
            } else if (c >= 'A' && c <= 'F') {
                value = c - 'A' + 10;
            } else if (c >= 'a' && c <= 'f') {
                value = c - 'a' + 10;
            } else {
                value = -1;
            }

            if (value >= 0) {

                if (first) {

                    byteArray[len] = (byte) (value << 4);

                } else {

                    byteArray[len] |= value;
                    len++;
                }

                first = !first;
            }
        }

        return byteArray;
    }


}
























