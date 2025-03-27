package org.example.ngevaticketmanagerspring.idgenerator.impl;

import org.example.ngevaticketmanagerspring.idgenerator.IdGenerator;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class InternalGuidGenerator implements IdGenerator {

    private final ConcurrentSkipListSet<Long> generatedIds = new ConcurrentSkipListSet<>();
    private final Random random = new Random();

    public InternalGuidGenerator() {}

    @Override
    public Long generateUniqueId() {

        long id;

        do {
            id = generateRandom10DigitId();
        } while (!generatedIds.add(id));

        return id;
    }

    private long generateRandom10DigitId() {
        return 1_000_000_000L + random.nextLong(9_000_000_000L);
    }
}
