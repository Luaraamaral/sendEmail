package br.com.luaraamaral.sendEmail.repository;

import br.com.luaraamaral.sendEmail.models.EmailModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository <EmailModel, Long> {
}
