package dev.renette.icjdemo;

import java.util.List;
import java.util.UUID;

public class LuckyNumbers {

    private final UUID uuid;
    private final List<String> numbers;

    public LuckyNumbers(UUID uuid, List<String> paragraphs) {
        this.numbers = paragraphs;
        this.uuid = uuid;
    }

    public List<String> getNumbers() {
        return numbers;
    }

    public UUID getUuid() {
        return uuid;
    }
}
