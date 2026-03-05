import { createRouter, createWebHistory } from 'vue-router'
import Home_page from '@/components/Home_page.vue'
import MessageBoard from '@/components/MessageBoard.vue'

const routes = [
  {
    path: '/',           
    name: 'Home',        
    component: Home_page 
  },
  {
    path: '/message',   
    name: 'Message',
    component: MessageBoard 
  }
  
]

const router = createRouter({

  history: createWebHistory(import.meta.env.BASE_URL),
  routes
})

export default router