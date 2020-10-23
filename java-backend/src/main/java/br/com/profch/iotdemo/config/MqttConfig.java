package br.com.profch.iotdemo.config;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttConfig {
    @Bean
    @ConfigurationProperties(prefix = "mqtt")
    public MqttConnectOptions criarOpcoesConexaoMqtt() {
        return new MqttConnectOptions();
    }

    @Bean
    public IMqttAsyncClient criarClienteMqtt(
        @Value("${mqtt.cliente}") String cliente,
        @Value("${mqtt.servidor}") String servidor, 
        @Value("${mqtt.porta}") int porta) throws MqttException {

        IMqttAsyncClient clienteMqtt = new MqttAsyncClient("tcp://" + servidor + ":" + porta, cliente);

        clienteMqtt.connect(criarOpcoesConexaoMqtt());

        return clienteMqtt;
    }
}