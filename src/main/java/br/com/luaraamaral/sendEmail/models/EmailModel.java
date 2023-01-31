package br.com.luaraamaral.sendEmail.models;

import br.com.luaraamaral.sendEmail.enums.StatusEmail;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "EMAIL")
public class EmailModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long emailId;
    //referência de quem enviou
    private String ownerRef;

    //quem está enviando o e-mail
    private String emailFrom;

    //quem vai receber o e-mail
    private String emailTo;

    //assunto
    private String subject;

    //corpo do e-mail
    @Column(columnDefinition = "TEXT")
    private String text;

    //data de envio
    private LocalDateTime sendDateEmail;

    //status do e-mail
    private StatusEmail statusEmail;


}
