package com.twsz.service;

public interface SendNoticeService {

    void sendTextNotice(String msg);
    void sendTextNotice(Exception e);
}
