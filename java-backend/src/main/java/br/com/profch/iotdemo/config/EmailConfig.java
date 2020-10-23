package br.com.profch.iotdemo.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
@ConfigurationProperties(prefix = "email")
public class EmailConfig {
    @Bean
    public JavaMailSender criarClienteEmail(
        @Value("${email.smtp.servidor}") String servidor,
        @Value("${email.smtp.porta}") int porta,
        @Value("${email.usuario}") String usuario,
        @Value("${email.senha}") String senha) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(servidor);
        mailSender.setPort(porta);
        
        mailSender.setUsername(usuario);
        mailSender.setPassword(senha);
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "false");
        
        return mailSender;
    }
}