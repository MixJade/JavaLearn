package com.chat.llama.resp;

public record AiMsg(String role, String content, String reasoning_content) {
}