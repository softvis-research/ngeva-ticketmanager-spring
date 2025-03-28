package org.example.ngevaticketmanagerspring.idgenerator.annotation;

import org.example.ngevaticketmanagerspring.idgenerator.IdService;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomIdGenerator extends SequenceStyleGenerator {

    private final IdService idService;

    @Override
    public Object generate(SharedSessionContractImplementor session, Object owner) throws HibernateException {
        return idService.generateUniqueId();
    }
}
