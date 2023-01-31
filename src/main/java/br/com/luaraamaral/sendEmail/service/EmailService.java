package br.com.luaraamaral.sendEmail.service;

import br.com.luaraamaral.sendEmail.enums.StatusEmail;
import br.com.luaraamaral.sendEmail.models.EmailModel;
import br.com.luaraamaral.sendEmail.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmailService {

    @Autowired
    EmailRepository emailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public EmailModel sendEmail(EmailModel emailModel) {
        //setar a data de envio
        emailModel.setSendDateEmail(LocalDateTime.now());

        //tentar enviar o email
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            //salvar o email com status
            emailModel.setStatusEmail(StatusEmail.SENT);
        } catch (MailException e) {
            //erro ao enviar
            emailModel.setStatusEmail(StatusEmail.ERROR);
        } finally {
            //salva o email com seu determinado status
            return emailRepository.save(emailModel);
        }
    }
}
