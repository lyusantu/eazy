package com.eazy.api.service.impl;

import com.eazy.api.service.MailTaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Service
public class MailTaskServiceImpl implements MailTaskService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    JavaMailSender mailSender;

    @Autowired
    SimpleMailMessage simpleMailMessage;

    /**
     * @param subject 主题
     * @param content 正文
     * @param toMail  收件人邮箱
     * @Description:普通文本发邮件形式
     */
    public void sendSimpleMail(String subject, String content, String toMail) {
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(content);
        simpleMailMessage.setTo(toMail);
        mailSender.send(simpleMailMessage);
        LOG.info("邮件发送成功..");
    }

    /**
     * @param subject 主题
     * @param content 正文
     * @param toMail  收件人邮箱
     * @Description:html发邮件形式
     */
    public void sendHtmlMail(String subject, String content, String toMail) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage);
        try {
            messageHelper.setTo(toMail);
            messageHelper.setSubject(subject);
            messageHelper.setText("<html><head></head><body><h1>" + content + "</h1></body></html>", true);
            mailSender.send(mailMessage);
            LOG.info("邮件发送成功..");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param subject     主题
     * @param content     正文
     * @param toMail      收件人邮箱
     * @param picturePath 图片路径
     * @Description: 带图片发邮件形式
     */
    public void sendPictureMail(String subject, String content, String toMail, String picturePath) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mailMessage, true);
            messageHelper.setTo(toMail);
            messageHelper.setSubject(subject);
            messageHelper.setText("<html><head></head><body><h1>" + content + "</h1><img src=/" + content + "/></body></html>", true);
            FileSystemResource img = new FileSystemResource(new File(picturePath));
            messageHelper.addInline("aaa", img);
            //发送邮件
            mailSender.send(mailMessage);
            LOG.info("邮件发送成功..");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param subject       标题
     * @param content       正文
     * @param toMail        收件人邮箱
     * @param accessoryPath 附件路径
     * @param accessoryName 附件名
     * @Description:带附件发邮件形式
     */
    public void sendMailTakeAccessory(String subject, String content, String toMail, String accessoryPath, String accessoryName) {
        MimeMessage mailMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        try {
            messageHelper = new MimeMessageHelper(mailMessage, true, "utf-8");
            messageHelper.setTo(toMail);
            messageHelper.setSubject(subject);
            messageHelper.setText("<html><head></head><body><h1>" + content + "</h1></body></html>", true);
            FileSystemResource file = new FileSystemResource(new File(accessoryPath));
            messageHelper.addAttachment(accessoryName, file);
            mailSender.send(mailMessage);
            LOG.info("邮件发送成功..");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
