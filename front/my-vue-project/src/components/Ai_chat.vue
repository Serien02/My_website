<template>
  <!-- 模板部分和之前一致，无需修改 -->
  <div class="chat-container">
    <div class="chat-header">
      <h3>简易聊天窗口</h3>
    </div>
    <div class="chat-messages" ref="messageContainer">
      <div 
        class="message" 
        :class="msg.role === 'user' ? 'my-message' : 'other-message'"
        v-for="(msg, index) in messageList" 
        :key="index"
      >
        <div class="message-avatar">{{ msg.role === 'user' ? '我' : 'TA' }}</div>
        <div class="message-content">{{ msg.content }}</div>
      </div>
      <div v-if="isLoading" class="message other-message">
        <div class="message-avatar">TA</div>
        <div class="message-content loading">正在回复中...</div>
      </div>
    </div>
    <div class="chat-input-area">
      <input
        v-model="inputMessage"
        placeholder="请输入消息..."
        class="message-input"
        @keyup.enter="sendMessage"
        :disabled="isLoading"
      />
      <button @click="sendMessage" :disabled="isLoading">
        {{ isLoading ? '发送中...' : '发送' }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, nextTick } from 'vue';
import axios from 'axios';

axios.defaults.baseURL = process.env.VUE_APP_BASE_API
axios.defaults.timeout = 30000;
axios.defaults.headers.post['Content-Type'] = 'application/json;charset=utf-8';

const inputMessage = ref('');
const messageList = ref([]); // 存储显示用的消息（{role: 'user/ai', content: ''}）
const chatHistory = ref([]); // 新增：存储传给Ollama的完整对话历史（{role: 'user/assistant', content: ''}）
const messageContainer = ref(null);
const isLoading = ref(false);

const sendMessage = async () => {
  const content = inputMessage.value.trim();
  if (!content || isLoading.value) return;

  // 1. 添加当前用户消息到显示列表
  messageList.value.push({ role: 'user', content });
  // 2. 添加当前用户消息到对话历史（Ollama要求AI角色是assistant，不是ai）
  chatHistory.value.push({ role: 'user', content });
  
  inputMessage.value = '';
  isLoading.value = true;

  try {
    // 3. 构建请求体：传入完整的对话历史
    const requestBody = {
      model: 'qwen3:8b',
      messages: [...chatHistory.value], // 关键：传递全部历史
      stream: false
    };

    const response = await axios.post('/message/ollama/chat', requestBody);
    const resData = response.data;

    if (resData.success && resData.code === 200) {
      const aiReply = resData.answer;
      // 4. 添加AI回复到显示列表
      messageList.value.push({ role: 'ai', content: aiReply });
      // 5. 添加AI回复到对话历史（角色必须是assistant）
      chatHistory.value.push({ role: 'assistant', content: aiReply });
    } else {
      const errorMsg = `回复失败：${resData.message || '未知错误'}`;
      messageList.value.push({ role: 'ai', content: errorMsg });
    }

    nextTick(() => {
      messageContainer.value.scrollTop = messageContainer.value.scrollHeight;
    });

  } catch (error) {
    const errorMsg = error.response 
      ? `接口错误：${error.response.status} - ${error.response.data?.message || '无详情'}`
      : '网络异常：无法连接到后端服务';
    messageList.value.push({ role: 'ai', content: errorMsg });
  } finally {
    isLoading.value = false;
    nextTick(() => {
      messageContainer.value.scrollTop = messageContainer.value.scrollHeight;
    });
  }
};
</script>

<style scoped>
/* 样式和之前一致，无需修改 */
.chat-container {
  width: 1400px;
  height: 800px;
  border: 1px solid #e5e7eb;
  border-radius: 8px;
  display: flex;
  flex-direction: column;
  margin: 20px auto;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.05);
}
.chat-header {
  padding: 12px 16px;
  border-bottom: 1px solid #e5e7eb;
  background-color: #f9fafb;
  border-radius: 8px 8px 0 0;
}
.chat-header h3 {
  margin: 0;
  font-size: 16px;
  color: #333;
}
.chat-messages {
  flex: 1;
  padding: 20px;
  overflow-y: auto;
  background-color: #f9fafb;
}
.message {
  display: flex;
  margin-bottom: 16px;
  max-width: 80%;
}
.other-message {
  align-self: flex-start;
  flex-direction: row;
}
.my-message {
  align-self: flex-end;
  flex-direction: row-reverse;
}
.message-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background-color: #e5e7eb;
  display: flex;
  align-items: center;
  justify-content: center;
  color: #666;
  font-size: 14px;
  margin: 0 10px;
}
.message-content {
  padding: 10px 16px;
  border-radius: 16px;
  background-color: #fff;
  box-shadow: 0 1px 2px rgba(0, 0, 0, 0.05);
  word-wrap: break-word;
  font-size: 15px;
}
.message-content.loading {
  color: #999;
  font-style: italic;
}
.my-message .message-content {
  background-color: #4096ff;
  color: #fff;
}
.chat-input-area {
  padding: 16px 20px;
  border-top: 1px solid #e5e7eb;
  display: flex;
  gap: 12px;
  background-color: #fff;
  border-radius: 0 0 8px 8px;
}
.message-input {
  flex: 1;
  padding: 12px 16px;
  border: 1px solid #e5e7eb;
  border-radius: 24px;
  outline: none;
  font-size: 15px;
}
.message-input:focus {
  border-color: #4096ff;
}
.message-input:disabled {
  background-color: #f5f5f5;
  cursor: not-allowed;
}
.send-btn {
  padding: 12px 24px;
  background-color: #4096ff;
  color: #fff;
  border: none;
  border-radius: 24px;
  cursor: pointer;
  font-size: 15px;
}
.send-btn:hover {
  background-color: #337ecc;
}
.send-btn:disabled {
  background-color: #99c2ff;
  cursor: not-allowed;
}
</style>