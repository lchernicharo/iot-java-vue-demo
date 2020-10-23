package br.com.profch.iotdemo.controllers;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import br.com.profch.iotdemo.service.EmailService;
import br.com.profch.iotdemo.service.MqttService;


@RestController
@RequestMapping("/luzEmergencia")
@CrossOrigin
public class MqttController {
    @Autowired
    private MqttService mqttSrvc;
    @Autowired
    private EmailService emailSrvc;

    @Value("${mqtt.topic}")
    private String topico;

    @GetMapping
    public ResponseEntity<SseEmitter> luzEmergenciaStream() throws MqttException, InterruptedException {
        SseEmitter pushNotifier = new SseEmitter(100L);

        CompletableFuture.supplyAsync(() -> {
            try {
                mqttSrvc.subscrever(topico);
                return mqttSrvc.getUltimaMensagem();
            } catch (MqttException | InterruptedException e) {
                return "Erro ao subscrever no tópico.";
            }
        }).whenCompleteAsync((result, error) -> {
            try {
                pushNotifier.send(
                    SseEmitter.event()
                    .data(result)
                    .name("luzEmergencia")
                    .build()
                );
            } catch (IOException e) {}
        });

        return new ResponseEntity<>(pushNotifier, HttpStatus.OK);
    }

    @GetMapping(value = "/{status}")
    public ResponseEntity<Void> acenderLuzEmergencia(@PathVariable String status) {
        try {
            mqttSrvc.publicar(topico, status);
            return ResponseEntity.ok().build();
        } catch (MqttException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> enviarEmail() {
        try {
            emailSrvc.enviarEmailNotificacao();
            return new ResponseEntity<>("Notificação enviada com sucesso!", HttpStatus.OK);
        } catch(Exception e) {
            System.out.println(e.getMessage());
            return new ResponseEntity<>(
                "Erro ao enviar a mensagem de notificação. " + e.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}