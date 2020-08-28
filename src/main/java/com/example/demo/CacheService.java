package com.example.demo;

import com.example.demo.model.FieldExam;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class CacheService {
    @Cacheable(value="method", key="T(com.example.demo.KeyGen).generate(#fieldExam)")
    public List<Integer> method(FieldExam fieldExam){
        return fieldExam.getList();
    }
}
