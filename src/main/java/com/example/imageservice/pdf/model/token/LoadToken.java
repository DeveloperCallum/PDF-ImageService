package com.example.imageservice.pdf.model.token;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LoadToken implements Token {
    private final int start;
    private final int end;
    private long timestamp;

    @JsonCreator
    public LoadToken(@JsonProperty("start") int start,
                     @JsonProperty("end") int end,
                     @JsonProperty("timestamp") long timestamp) {
        this.start = start;
        this.end = end;
        this.timestamp = timestamp;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
