package br.com.luaraamaral.sendEmail.controller;

import br.com.luaraamaral.sendEmail.models.EmailModel;
import br.com.luaraamaral.sendEmail.service.EmailService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;

@RestController
public class EmailController {

    @Autowired
    EmailService emailService;

    @PostMapping("/sending-email")
    public ResponseEntity<EmailModel> sendingEmail(@RequestBody String jao) throws JSONException {
        EmailModel emailModel = new EmailModel();
        //método que converte DTO para MODEL
        //BeanUtils.copyProperties(emailDto, emailModel);


        JSONObject json = new JSONObject(jao);

        //nome do arquivo
        String fileName = (String) json.get("fileName");
        String attachment = (String) json.get("attachment");
        String extension = (String) json.get("extension");
        String address = (String) json.get("address");
        String subject = (String) json.get("subject");
        String message = (String) json.get("message");

        emailModel.setEmailTo(address);
        emailModel.setSubject(subject);
        emailModel.setText(message);
        emailModel.setAttachment(attachment);

        try {
            emailService.sendEmailWithAttachment(emailModel);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println(json.get("address"));

        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }


}
