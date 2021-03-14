package com.epam.jwd.core_final.io;

import com.epam.jwd.core_final.domain.BaseEntity;

import java.io.IOException;

public interface WriteStrategy<T extends BaseEntity> {
    void writeEntity(T entity) throws IOException;
}
