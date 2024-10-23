<template>
  <v-app id="inspire">
    <v-app-bar
      class="px-3"
      density="compact"
      flat
    >
      <v-avatar
        class="hidden-md-and-up"
        color="grey-darken-1"
        size="32"
      ></v-avatar>

      <v-spacer></v-spacer>

      <v-tabs
        color="grey-darken-2"
        centered
      >
        <router-link
          v-for="(link, index) in links"
          :key="index"
          :to="link"
          class="bg-grey-lighten-3"
        >
          <v-tab>
            {{ link }}
          </v-tab>
        </router-link>
      </v-tabs>
      <v-spacer></v-spacer>

      <v-avatar
        class="hidden-sm-and-down"
        color="grey-darken-1"
        size="32"
      ></v-avatar>
    </v-app-bar>

    <v-main class="bg-grey-lighten-3">
      <v-container>
        <router-view/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script setup lang="ts">
import {RouterLink, RouterView} from 'vue-router'

const links = [
  'demo',
  'systemParam',
  'demo',
  'demo',
]
</script>

<style scoped>
</style>
