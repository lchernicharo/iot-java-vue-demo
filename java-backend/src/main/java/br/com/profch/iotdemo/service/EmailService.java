package br.com.profch.iotdemo.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender clienteEmail;

    @Value("${email.de}")
    private String de;
    @Value("${email.para}")
    private String para;
    @Value("${email.assunto}")
    private String assunto;
    @Value("${email.mensagemNotificacao}")
    private String mensagemNotificacao;

    public void enviarEmailNotificacao() throws MailException, MessagingException {
        clienteEmail.send(criarMensagemNotificacao());
    }

    private MimeMessage criarMensagemNotificacao() throws MessagingException {
        MimeMessage mail = clienteEmail.createMimeMessage();
        MimeMessageHelper message = new MimeMessageHelper(mail);
        message.setFrom(de);
        message.setTo(para); 
        message.setSubject(assunto); 
        message.setText(mensagemNotificacao, true);

        return mail;
    }
}