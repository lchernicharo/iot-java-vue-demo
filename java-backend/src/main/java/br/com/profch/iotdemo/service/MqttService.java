package br.com.profch.iotdemo.service;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MqttService {
    @Autowired
	private IMqttAsyncClient mqttClient;
	private String ultimaMensagem;

	public String getUltimaMensagem() {
		return ultimaMensagem;
	}

	public void publicar(final String topic, final String payload, int qos, boolean retained)
			throws MqttPersistenceException, MqttException {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(payload.getBytes());
		mqttMessage.setQos(qos);
		mqttMessage.setRetained(retained);

		mqttClient.publish(topic, payload.getBytes(), qos, retained);
	}

	public void publicar(final String topic, final String payload)
			throws MqttPersistenceException, MqttException {
		mqttClient.publish(topic, payload.getBytes(), 1, true);
	}

	public void subscrever(final String topic) throws MqttException, InterruptedException {
		mqttClient.subscribe(topic, 1, (tpic, msg) -> ultimaMensagem = new String(msg.getPayload()));
	}
}