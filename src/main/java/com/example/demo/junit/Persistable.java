package com.example.demo.junit;


import java.time.Instant;

public interface Persistable {
    Integer getId();
    void setId(Integer id);
    Instant getCreateTimestamp();
    void setCreateTimestamp(Instant instant);
}
