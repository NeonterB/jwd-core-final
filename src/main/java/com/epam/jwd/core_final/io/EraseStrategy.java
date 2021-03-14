package com.epam.jwd.core_final.io;

import com.epam.jwd.core_final.domain.BaseEntity;
import com.epam.jwd.core_final.exception.EntityNotFoundException;

import java.io.IOException;

public interface EraseStrategy<T extends BaseEntity> {
    void erase(T entity) throws EntityNotFoundException, IOException;
}
