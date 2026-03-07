package com.example.demo.controller;

import com.example.demo.entity.ollama_request;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/message/ollama")
@CrossOrigin(origins = "*") 
public class ollama_chat_controller {
    private static final String OLLAMA_API_URL = "http://localhost:11434/api/chat";

    private final ObjectMapper objectMapper = new ObjectMapper();
        private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(5, TimeUnit.MINUTES)    
            .readTimeout(5, TimeUnit.MINUTES)    
            .writeTimeout(5, TimeUnit.MINUTES)      
            .retryOnConnectionFailure(true)       
            .build();

    @RequestMapping("/chat")
    public Map<String, Object> chat(@org.springframework.web.bind.annotation.RequestBody ollama_request ollama_request) { 
               Map<String, Object> result = new HashMap<>();
        try {
            if (ollama_request.getModel() == null || ollama_request.getModel().trim().isEmpty()) {
                ollama_request.setModel("qwen3:8b");
            }
            if (ollama_request.getMessages() == null || ollama_request.getMessages().isEmpty()) {
                ollama_request.Message defaultMsg = new ollama_request.Message("user", "Hello!");
                ollama_request.setMessages(Collections.singletonList(defaultMsg));
            }

            ollama_request.setStream(false); 

            String requestBody = objectMapper.writeValueAsString(ollama_request);
            Request request = new Request.Builder()
                    .url(OLLAMA_API_URL)
                    .post(RequestBody.create(requestBody, MediaType.get("application/json; charset=utf-8")))
                    .build();

            try (Response response = okHttpClient.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    throw new RuntimeException("Ollama API 调用失败，状态码：" + response.code() 
                            + "，响应信息：" + (response.body() != null ? response.body().string() : "无"));
                }

                String responseBody = response.body().string();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String answer = jsonNode.get("message").get("content").asText();

                result.put("code", 200);
                result.put("success", true);
                result.put("answer", answer);
            }

        } catch (Exception e) {
            result.put("code", 500);
            result.put("success", false);
            result.put("message", "调用失败：" + e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
