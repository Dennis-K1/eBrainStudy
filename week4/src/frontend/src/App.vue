<template>
  <div id="app">
    뷰 게시판
    <router-view/>
  </div>
  <a href="/">
    <button>처음으로</button>
  </a>
</template>

<script>

export default {
  name: 'App',
}
</script>