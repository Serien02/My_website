<template>
  <div class="message-board">
    <!-- 标题 -->
    <h2>留言板</h2>
    
    <!-- 留言输入区域 -->
    <div class="input-area">
      <input 
        v-model="inputName" 
        placeholder="请输入你的昵称" 
        class="name-input"
      >
      <textarea 
        v-model="inputContent" 
        placeholder="请输入留言内容" 
        class="content-input"
        rows="4"
      ></textarea>
      <button @click="submitMessage" class="submit-btn">提交留言</button>
      <button @click="clearAll" class="clear-btn">清空所有</button>
    </div>

    <!-- 留言展示区域 -->
    <div class="message-list">
      <div v-if="messageList.length === 0" class="empty-tip"></div>
      <div v-for="(item, index) in messageList" :key="index" class="message-item">
        <div class="message-name">💬 {{ item.name }}</div>
        <div class="message-content">{{ item.content }}</div>
        <div class="message-time">{{ item.time }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'

// 响应式数据：输入框内容、留言列表
const inputName = ref('')
const inputContent = ref('')
const messageList = ref([])

// 提交留言
const submitMessage = () => {
  // 简单校验
  if (!inputName.value.trim()) {
    alert('请输入昵称！')
    return
  }
  if (!inputContent.value.trim()) {
    alert('请输入留言内容！')
    return
  }

  // 构造留言对象
  const newMessage = {
    name: inputName.value,
    content: inputContent.value,
    time: new Date().toLocaleString() // 本地时间格式
  }

  // 添加到留言列表
  messageList.value.unshift(newMessage) // 新留言放最前面

  // 清空输入框
  inputName.value = ''
  inputContent.value = ''
}

// 清空所有留言
const clearAll = () => {
  if (confirm('确定清空所有留言吗？')) {
    messageList.value = []
  }
}
</script>

<style scoped>
/* 极简样式，保证整洁即可 */
.message-board {
  max-width: 600px;
  margin: 20px auto;
  padding: 0 20px;
  font-family: sans-serif;
}

.input-area {
  margin: 20px 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.name-input, .content-input {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

.submit-btn {
  background: #409eff;
  color: white;
  border: none;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
}

.clear-btn {
  background: #f56c6c;
  color: white;
  border: none;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
}

.message-list {
  margin-top: 20px;
  gap: 10px;
  display: flex;
  flex-direction: column;
}

.message-item {
  padding: 10px;
  border: 1px solid #eee;
  border-radius: 4px;
}

.message-name {
  font-weight: bold;
  margin-bottom: 5px;
}

.message-time {
  font-size: 12px;
  color: #999;
  margin-top: 5px;
}

.empty-tip {
  color: #999;
  text-align: center;
  padding: 20px;
}
</style>

<style>
/* 全局样式：给body添加背景图 */
body {
  margin: 0;
  padding: 0;
  background-image: url('@/assets/AnimeGirl1.png');
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment: fixed;
  min-height: 100vh; /* 让背景铺满整个视口高度 */
}
</style>