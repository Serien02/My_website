<template>
  <div class="home-container">
    
    <div class="button-group">
      <button @click="goToPage('/message')" class="nav-btn">
        留言板
      </button>
      
      <button @click="goToPage('/ai_chat')" class="nav-btn">
        AI聊天
      </button>
      
      <button @click="goToPage('/management')" class="nav-btn">
        后台管理功能
      </button>
      
      <button @click="goToPage('/page3')" class="nav-btn">
        页面3（预留）
      </button>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router'

const router = useRouter()

const goToPage = (path) => {
  router.push(path)
}
</script>

<style scoped>
.home-container {
  max-width: 800px;
  margin: 50px auto;
  text-align: center;
  padding: 20px;
}

h1 {
  color: #333;
  margin-bottom: 20px;
}

p {
  color: #666;
  margin-bottom: 30px;
}

.button-group {
  display: flex;
  flex-direction: column;
  gap: 15px;
  max-width: 300px;
  margin: 0 auto;
}

.nav-btn {
  padding: 15px 30px;
  font-size: 18px;
  border: none;
  border-radius: 8px;
  background-color: #42b983;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s;
}

.nav-btn:hover {
  background-color: #3aa876;
}
</style>