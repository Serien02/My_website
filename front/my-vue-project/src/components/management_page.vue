<template>
  <div class="admin-jump-page">
    <!-- Jenkins 跳转按钮 -->
    <button 
      class="jump-btn jenkins-btn"
      @click="goToJenkins"
      :disabled="isDevEnv"
    >
    Jenkins
    </button>

    <!-- Loki 跳转按钮 -->
    <button 
      class="jump-btn loki-btn"
      @click="goToLoki"
      :disabled="isDevEnv"
    >
    Loki
    </button>
  </div>
</template>

<script setup>
const baseApi = process.env.VUE_APP_BASE_API
const isDevEnv = baseApi.includes('localhost')

// 配置后台地址（生产环境用你的服务器IP，开发环境用占位符）
const SERVER_HOST = 'http://8.137.81.249' // 你的阿里云服务器IP
const JENKINS_URL = isDevEnv ? 'http://localhost:8080' : `${SERVER_HOST}:8080`
const LOKI_URL = isDevEnv ? 'http://localhost:3000' : `${SERVER_HOST}:3000`

// 通用跳转方法
const jumpToUrl = (url, name) => {
  // 开发环境：提示禁止访问
  if (isDevEnv) {
    alert(`【开发环境限制】\n禁止访问 ${name}，仅生产环境可访问！`)
    return
  }

  // 生产环境：尝试跳转
  try {
    const newWindow = window.open(url, '_blank')
    // 检测浏览器弹窗拦截
    if (!newWindow) {
      alert(`${name} 跳转失败：\n浏览器弹窗拦截，请允许弹窗后重试！`)
    }
  } catch (error) {
    alert(`${name} 跳转失败：\n请检查地址是否正确：${url}\n错误信息：${error.message}`)
  }
}

// 跳转 Jenkins
const goToJenkins = () => jumpToUrl(JENKINS_URL, 'Jenkins')

// 跳转 Loki
const goToLoki = () => jumpToUrl(LOKI_URL, 'Loki')
</script>

<style scoped>

/* 通用按钮样式 */
.jump-btn {
  width: 280px;
  height: 70px;
  border: none;
  border-radius: 12px;
  font-size: 20px;
  font-weight: 600;
  color: white;
  cursor: pointer;
  margin: 15px 0;
  transition: all 0.3s ease;
  box-shadow: 0 4px 12px rgba(0,0,0,0.1);
}

/* 禁用状态样式（开发环境） */
.jump-btn:disabled {
  background-color: #cccccc !important;
  cursor: not-allowed;
  transform: none !important;
  box-shadow: none !important;
  opacity: 0.7;
}

/* Jenkins 按钮 */
.jenkins-btn {
  background-color: #d24939;
}
.jenkins-btn:hover:not(:disabled) {
  background-color: #b93c2f;
  transform: translateY(-3px);
  box-shadow: 0 6px 18px rgba(210,73,57,0.2);
}

/* Loki 按钮 */
.loki-btn {
  background-color: #005e99;
}
.loki-btn:hover:not(:disabled) {
  background-color: #004a7c;
  transform: translateY(-3px);
  box-shadow: 0 6px 18px rgba(0,94,153,0.2);
}
</style>