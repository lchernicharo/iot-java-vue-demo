# IoT com Java e Vue.js
Aplicação de exemplo de como utilizar NodeMCU, MQTT, backend Java e frontend Vue.js

## Configurações necessárias

No programa que você executará no **Arduino**, algumas constantes precisam ser preenchidas, **obrigatoriamente**, para que tudo funcione. São elas:

- *url.do.seu.servidor.mqtt*
- *seu.usuario.no.servidor.mqtt*
- *sua.senha.no.servidor.mqtt*
- *nome.da.sua.rede.wifi*
- *senha.da.sua.rede.wifi*
- *nome.do.seu.topico.no.servidor.mqttt*

No backend Java, no arquivo de configurações **application.properties** você também precisará definir algumas configurações:

- mqtt.username=*seu.usuario.no.servidor.mqtt*
- mqtt.cliente=*nome.do.seu.client*
- mqtt.password=*sua.senha.no.servidor.mqtt*
- mqtt.servidor=*url.do.seu.servidor.mqtt*
- mqtt.topic=*nome.do.seu.topico.no.servidor.mqttt*
- email.usuario=*seu.usuario.no.gmail*
- email.senha=*sua.senha.no.gmail*
- email.para=*email.do.destinatario.da.mensagem.do.sistema*

No frontend Vue.js, no arquivo **api.js** você também precisará alterar o valor de uma constante:

- *url.do.seu.backend*

***Constantes com o mesmo nome no Arduino e no backend devem ter os mesmos valores atribuídos***

### Vejamos um exemplo
Se no Arduino você colocou o valor **192.168.1.10** em *url.do.seu.servidor.mqtt*, no backend você precisará fazer **mqtt.servidor=192.168.1.10**.

## Circuito

Na pasta Arduino há o arquivo do circuito para edição no software [Fritzing](https://fritzing.org/) e também sua imagem JPG.