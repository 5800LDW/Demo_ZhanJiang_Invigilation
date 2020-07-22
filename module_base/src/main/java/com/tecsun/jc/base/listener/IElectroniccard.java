package com.tecsun.jc.base.listener;

public interface IElectroniccard {

    /***部里的sdk的接口的回调*/
    void onResult(String originalData, String signNo, IClose iClose);

    /***部里的sdk的接口的回调*///{"busiSeq":"4c15f9efd4e04f1a83e34f651d4c901f","sceneType":"004"}
    void onSceneResult(String originalData, String busiSeq, IClose iClose);

}
