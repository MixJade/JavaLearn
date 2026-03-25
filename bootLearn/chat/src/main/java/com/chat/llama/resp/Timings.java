package com.chat.llama.resp;

public record Timings(int cache_n, int prompt_n, double prompt_ms, double prompt_per_token_ms, double prompt_per_second,
                      int predicted_n, double predicted_ms, double predicted_per_token_ms,
                      double predicted_per_second) {
}