package com.chat.llama.resp;


public record Usage(int completion_tokens, int prompt_tokens, int total_tokens) {
}