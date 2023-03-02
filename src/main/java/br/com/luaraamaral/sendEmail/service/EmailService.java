package br.com.luaraamaral.sendEmail.service;

import br.com.luaraamaral.sendEmail.models.EmailModel;
import br.com.luaraamaral.sendEmail.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;


@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public void sendEmailWithAttachment(EmailModel emailModel) throws MessagingException, IOException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        System.out.println(emailModel);

        //helper.setFrom(emailModel.getEmailFrom());

        helper.setTo(emailModel.getEmailTo());
        helper.setSubject(emailModel.getSubject());
        helper.setText(emailModel.getText());

        // Decode base64 string to byte array
        byte[] decodedBytes = Base64.getDecoder().decode(emailModel.getAttachment());
        ByteArrayDataSource dataSource = new ByteArrayDataSource(decodedBytes, "application/pdf");

        MimeBodyPart attachmentPart = new MimeBodyPart();
        attachmentPart.setDataHandler(new DataHandler(dataSource));
        attachmentPart.setFileName("arquivo.pdf");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(attachmentPart);
        helper.getMimeMessage().setContent(multipart);

        emailSender.send(message);
    }


}
