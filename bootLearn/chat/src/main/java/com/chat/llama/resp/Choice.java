package com.chat.llama.resp;

public record Choice(String finish_reason, int index, AiMsg message) {
}