package com.chat.llama.resp;

import java.util.List;

public record ChatResponse(List<Choice> choices, long created, String model, String system_fingerprint, String object,
                           Usage usage, String id, Timings timings) {
}