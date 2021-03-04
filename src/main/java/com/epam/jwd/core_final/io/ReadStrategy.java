package com.epam.jwd.core_final.io;

import com.epam.jwd.core_final.domain.AbstractBaseEntity;
import com.epam.jwd.core_final.domain.BaseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public interface ReadStrategy<T extends BaseEntity>{
    Collection<T> readEntities() throws IOException;
}
