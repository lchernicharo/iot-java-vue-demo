#include <Adafruit_MQTT_Client.h>
#include <Adafruit_MQTT.h>
#include <ESP8266WiFi.h>

#define SENSOR A0
#define LUZ_PRINCIPAL D3
#define LUZ_EMERGENCIA D4

#define MQTT_SERVER "url.do.seu.servidor.mqtt"
#define MQTT_SERVERPORT 1950
#define MQTT_USERNAME "seu.usuario.no.servidor.mqtt"
#define MQTT_PWD "sua.senha.no.servidor.mqtt"

char* ssid = "nome.da.sua.rede.wifi";
char* pass = "senha.da.sua.rede.wifi";

int status = WL_IDLE_STATUS;
WiFiClient wfClient;
Adafruit_MQTT_Client mqtt(&wfClient, MQTT_SERVER, MQTT_SERVERPORT, MQTT_USERNAME, MQTT_PWD);
Adafruit_MQTT_Publish mqttThread = Adafruit_MQTT_Publish(&mqtt, "nome.do.seu.topico.no.servidor.mqttt");

void setup() {
  pinMode(LUZ_PRINCIPAL, OUTPUT);
  pinMode(LUZ_EMERGENCIA, OUTPUT);
  pinMode(SENSOR, INPUT);

  digitalWrite(LUZ_PRINCIPAL, LOW);
  digitalWrite(LUZ_EMERGENCIA, LOW);
  
  Serial.begin(9600);
  delay(10);
  WiFi.begin(ssid, pass);

  while (status != WL_CONNECTED) {
    delay(500);
    status = WiFi.status();
    Serial.print(".");
  }

  Serial.println("");
}

void loop() {
  conectarMQTT();
  String json = "";
  const char* msg;
  int luminosidade = analogRead(SENSOR);

  if(luminosidade < 340) {
    digitalWrite(LUZ_PRINCIPAL, LOW);
    digitalWrite(LUZ_EMERGENCIA, HIGH);

    json = "\"principal\":\"off\", \"emergencia\":\"on\"";
  } else {
    digitalWrite(LUZ_PRINCIPAL, HIGH);
    digitalWrite(LUZ_EMERGENCIA, LOW);

    json = "\"principal\":\"on\", \"emergencia\":\"off\"";
  }
  
  json = "{" + json + "}";
  msg = json.c_str();

  mqttThread.publish(msg);
  delay(3000);
}

void conectarMQTT() {
  int ret;

  // Aborta se já estiver conectado.
  if (mqtt.connected()) {
    return;
  }

  Serial.print("Conectando ao servidor MQTT... ");

  int tentativas = 3;
  while ((ret = mqtt.connect()) != 0 && tentativas > 0) { // connect() retorna 0 se conectado
       Serial.println(mqtt.connectErrorString(ret));
       Serial.println("Tentando novamente em 5 segundos...");
       mqtt.disconnect();
       delay(5000);  // Aguarda 5 segundos
       tentativas--;
       if (tentativas == 0) {
         Serial.println("Não foi possível conectar ao servidor MQTT.");
       } else {
         Serial.println("MQTT conectado.");
       }
  }
}
