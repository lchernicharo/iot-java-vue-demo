<template>
  <v-col>
    <br />
    <h2>
      Status da luz principal: 
      <span
        v-bind:class="{
          'verde': this.statusLuzPrincipal == 'on'
          , 'vermelho': this.statusLuzPrincipal == 'off'
      }">{{this.statusLuzPrincipal}}</span>
    </h2>
    <h2 v-if="statusLuzEmergencia == 'on'">
      Status da luz de emergência: 
      <span
        v-bind:class="{
          'verde': this.statusLuzEmergencia == 'on'
          , 'vermelho': this.statusLuzEmergencia == 'off'
      }">{{this.statusLuzEmergencia}}</span>
    </h2>

    <br />

    <div v-if="confirmacaoEmail != ''" class="mensagem">
      {{this.confirmacaoEmail}}
      <span @click="confirmacaoEmail = ''">(fechar)</span>
    </div>

    <div>
      <v-btn id="btnLuzEmergencia" :disabled="this.statusLuzPrincipal=='on' || this.sending"
          :loading="this.sending" color="primary" @click="enviarEmailNotificacao">Enviar email de notificação para responsável
      </v-btn>
    </div>
  </v-col>
  
</template>

<script>

import axios from "axios";

export default {
  name: 'HomePage',
  data() {
    return {
      statusLuzPrincipal: "Aguardando o servidor...",
      statusLuzEmergencia: "Aguardando o servidor...",
      confirmacaoEmail: "",
      sending: false
    }
  },
  mounted() {
    let es = new EventSource(this.$store.state.api.LuzEmergenciaStream);
    es.addEventListener("message", event => {
      try {
        let json = JSON.parse(event.data);        

        if(json[1].data != "") {
          json = JSON.parse(json[1].data);
          this.statusLuzPrincipal = json.principal;
          this.statusLuzEmergencia = json.emergencia;
        }
      } catch(e) {
        alert(e)
        this.statusLuzPrincipal = "Falha no processamento.",
        this.statusLuzEmergencia = "Falha no processamento.";
      }
    }, false);
  },
  methods: {
    async enviarEmailNotificacao() {
      this.sending = true;
      axios.get(this.$store.state.api.EnviarEmailEndpoint)
      .then(
        (confirmacao) => {
          this.confirmacaoEmail = confirmacao.data;
          this.sending = false;
        }
      );
    }
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
h2 {
  margin-top: 5px;
  margin-bottom: 0px;
}
.verde {
  color: #009419;
}
.vermelho {
  color: #ee1111;
}
.mensagem {
  height: 45px;
  font-weight: bold;
}

.mensagem span {
  cursor: pointer;
  text-decoration: underline;
}

</style>
