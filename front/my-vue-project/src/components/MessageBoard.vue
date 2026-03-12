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
        placeholder="" 
        class="content-input"
        rows="4"
      ></textarea>
      <button @click="submitMessage" class="submit-btn">提交留言</button>
      <button @click="clearAll" class="clear-btn">清空所有</button>
    </div>

    <!-- 留言展示区域 -->
    <div class="message-list">
      <div v-if="messageList.length === 0" class="empty-tip"></div>
      <div v-for="(item, index) in messageList" :key="item.id || index" class="message-item">
        <div class="message-name">💬 {{ item.username }}</div>
        <div class="message-content">{{ item.content }}</div>
        <div class="message-time">{{ formatTime(item.createTime) }}</div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'
//import { ElCascader } from 'element-plus'
import 'element-plus/dist/index.css'

// 配置axios基础路径
axios.defaults.baseURL = process.env.VUE_APP_BASE_API

// 响应式数据：输入框内容、留言列表
const inputName = ref('')
const inputContent = ref('')
const messageList = ref([])

// 初始化加载历史留言
const loadMessages = async () => {
  try {
    const response = await axios.get('/message/list')
    messageList.value = response.data
  } catch (error) {
    console.error('加载留言失败：', error)
    alert('加载历史留言失败，请稍后重试')
  }
}

// 格式化时间
const formatTime = (timeStr) => {
  if (!timeStr) return ''
  const date = new Date(timeStr)
  return date.toLocaleString()
}

// 提交留言
const submitMessage = async () => {
  // 简单校验
  if (!inputName.value.trim()) {
    alert('请输入昵称！')
    return
  }
  if (!inputContent.value.trim()) {
    alert('请输入留言内容！')
    return
  }

  try {
    // 构造留言对象
    const newMessage = {
      username: inputName.value,
      content: inputContent.value
    }

    // 提交到后端
    const response = await axios.post('/message/submit', newMessage)
    alert(response.data)
    
    // 提交成功后重新加载留言列表
    if (response.data.includes('成功')) {
      // 清空输入框
      inputName.value = ''
      inputContent.value = ''
      // 重新加载留言
      loadMessages()
    }
  } catch (error) {
    console.error('提交留言失败：', error)
    alert('提交留言失败，请稍后重试')
  }
}

// 清空所有留言
const clearAll = async () => {
  if (confirm('确定清空所有留言吗？')) {
    try {
      const response = await axios.delete('/message/clear')
      alert(response.data)
      // 清空成功后重新加载列表
      if (response.data.includes('成功')) {
        loadMessages()
      }
    } catch (error) {
      console.error('清空留言失败：', error)
      alert('清空留言失败，请稍后重试')
    }
  }
}

// 页面挂载时加载历史留言
onMounted(() => {
  loadMessages()
})
</script>

<style scoped>
/* 极简样式，保证整洁即可 */
.message-board {
  max-width: 600px;
  margin: 20px auto;
  padding: 0 20px;
  font-family: sans-serif;
  background: rgba(255, 255, 255, 0.85); /* 增加背景透明度，提升可读性 */
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
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
  background: rgba(255, 255, 255, 0.9);
}

.submit-btn {
  background: #409eff;
  color: white;
  border: none;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}

.submit-btn:hover {
  background: #337ecc;
}

.clear-btn {
  background: #f56c6c;
  color: white;
  border: none;
  padding: 8px;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}

.clear-btn:hover {
  background: #e45656;
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
  background: rgba(255, 255, 255, 0.9);
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