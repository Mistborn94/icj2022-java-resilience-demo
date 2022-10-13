package dev.renette.icjdemo.leak;

import dev.renette.icjdemo.LuckyNumbers;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class LeakyCache {

    private final ConcurrentMap<String, LuckyNumbers> map = new ConcurrentHashMap<>();

    public void put(String key, LuckyNumbers object) {
        map.put(key, object);
    }

    public Object get(String key) {
        return map.get(key);
    }
}
