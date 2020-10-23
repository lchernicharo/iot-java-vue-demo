<template>
  <v-app id="app">
    <v-container fluid>
      <v-row justify="space-around">
        <v-img src="./assets/logo.png" aspect-ratio="8.5" contain/>
      </v-row>
      <v-row justify="space-around">
        <HomePage />
      </v-row>
    </v-container>
  </v-app>
</template>

<script>
import HomePage from './components/HomePage.vue'

export default {
  name: 'App',
  components: {
    HomePage
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
