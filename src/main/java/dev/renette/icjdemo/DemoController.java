package dev.renette.icjdemo;

import dev.renette.icjdemo.leak.LeakyCache;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class DemoController {
    private static final int LEAK_LIST_SIZE = 100;
    private static final int REPETITIONS = 10;
    private final LeakyCache leakyCache;

    public DemoController(LeakyCache leakyCache) {
        this.leakyCache = leakyCache;
    }

    @PostMapping("/leak")
    public LuckyNumbers create() {
        LuckyNumbers cachedObject = getCachedObject( LEAK_LIST_SIZE);
        leakyCache.put(cachedObject.getUuid().toString(), cachedObject);
        return cachedObject;
    }

    private LuckyNumbers getCachedObject(int leakListSize) {
        UUID uuid = UUID.randomUUID();
        return new LuckyNumbers(uuid, getNumbers(leakListSize));
    }

    private List<String> getNumbers(int listSize) {
        return Stream.generate(() -> ThreadLocalRandom.current().nextInt())
                .limit(listSize)
                .map(String::valueOf)
                .collect(Collectors.toList());
    }

    @GetMapping("/slow")
    public LuckyNumbers getSlow() throws InterruptedException {
        synchronized (this) {
            Thread.sleep(1000);
            return getCachedObject( 5);
        }
    }

    @GetMapping("/busy")
    public LuckyNumbers getBusy() {
        var numbers = getNumbers(200);
        for (int i = 0; i < REPETITIONS; i++) {
            Collections.shuffle(numbers);
            Collections.sort(numbers);
        }
        return new LuckyNumbers(UUID.randomUUID(), numbers);
    }
}
