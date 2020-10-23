const rootURL = "http://url.do.seu.backend";
export default {
    namespaced: true,
    state: {
        LuzEmergenciaStream: rootURL + "luzEmergencia",
        EnviarEmailEndpoint: rootURL + "enviarEmail",
    }
}