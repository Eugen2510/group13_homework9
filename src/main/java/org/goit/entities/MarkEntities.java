package org.goit.entities;

import jakarta.persistence.EntityManager;

public interface MarkEntities {
    void clone(MarkEntities entity);
    String getSqlQuery();
    MarkEntities findById(EntityManager manager, int id);
    void delete(EntityManager manager, int id)  throws Exception;
}
