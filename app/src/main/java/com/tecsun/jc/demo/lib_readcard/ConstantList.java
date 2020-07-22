package com.tecsun.jc.demo.lib_readcard;

/**
 * named string array'index
 */
public interface ConstantList {
    //left menu index
    int showLOG = 0;
    int deleteLOG = 1;
    int showResultLOG = 2;
    int deleteResultLOG = 3;
    int serverSetting = 4;
    int shareLog = 5;

    //module
    int mode_bt = 1;
    int mode_nfc = 2;
    int mode_otg = 3;

    //status
    int MESSAGE_VALID_OTGSTART = 10;
    int MESSAGE_VALID_NFCSTART = 11;
    int MESSAGE_VALID_NFCBUTTON = 12;
    int MESSAGE_VALID_BTSTART = 13;
    int MESSAGE_VALID_BTBUTTON = 14;
    int BLUETOOTH_CONNECTION_SUCCESS = 45;
    int BLUETOOTH_CONNECTION_FAILED = 44;
    int SERVER_CANNOT_CONNECT  =  90000001;
    int READ_CARD_START  =  10000001;
    int READ_CARD_SUCCESS  =  30000003;
    int READ_CARD_FAILED  =  90000009;
    int BTREAD_BUTTON_ENABLED = 101;
}
