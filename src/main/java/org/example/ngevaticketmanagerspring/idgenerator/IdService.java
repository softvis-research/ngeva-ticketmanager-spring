package org.example.ngevaticketmanagerspring.idgenerator;

import org.example.ngevaticketmanagerspring.idgenerator.impl.InternalGuidGenerator;
import org.springframework.stereotype.Service;

@Service
public class IdService {

    IdGenerator idGenerator;

    public IdService() {
        // Hier Implementierung einklinken
        this.idGenerator = new InternalGuidGenerator();
    }

    public Long generateUniqueId() {
        return idGenerator.generateUniqueId();
    }
}