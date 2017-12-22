package com.eazy.api.service;

public interface MailTaskService {

    void sendSimpleMail(String subject, String content, String toMail);

    void sendHtmlMail(String subject, String content, String toMail);

    void sendPictureMail(String subject, String content, String toMail, String picturePath);

    void sendMailTakeAccessory(String subject, String content, String toMail, String accessoryPath, String accessoryName);

}
