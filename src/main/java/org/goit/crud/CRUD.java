package org.goit.crud;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.goit.entities.MarkEntities;
import org.goit.entities.Ownership;
import org.goit.entities.Person;

import java.util.List;

public class CRUD {
    private final EntityManagerFactory factory;

    public CRUD(EntityManagerFactory factory) {
        this.factory = factory;
    }

    public <T extends MarkEntities> void createEntity(T... entities) {

        EntityTransaction transaction = null;

        try (EntityManager manager = factory.createEntityManager()){
            transaction = manager.getTransaction();
            transaction.begin();

            for (T entity : entities) {
                manager.persist(entity);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public <T extends MarkEntities> List<T> getAllEntities(T entity) {
        List<T> result;
        try (EntityManager manager = factory.createEntityManager()) {
            String query = entity.getSqlQuery();
            result = manager.createQuery(query).getResultList();
        }
        return result;
    }

    public <T extends MarkEntities> MarkEntities getEntityById(T entity, int id){
        EntityManager manager = factory.createEntityManager();
        MarkEntities findEntity = entity.findById(manager, id);
        manager.close();
        return findEntity;
    }


    public <T extends MarkEntities>  void updateEntity(T entity, int id){

        EntityTransaction transaction = null;
        try (EntityManager manager = factory.createEntityManager()) {
            transaction = manager.getTransaction();
            MarkEntities byId = entity.findById(manager, id);
            byId.clone(entity);
            transaction.begin();
            manager.merge(byId);
            transaction.commit();
        }catch (Exception e){
            if(transaction != null && transaction.isActive()){
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    public <T extends MarkEntities> void delete(T entity, int id) {

        EntityManager manager = factory.createEntityManager();
        try {
            entity.delete(manager, id);
        }catch (Exception e){
            e.printStackTrace();
        }

        manager.close();
    }


    public void getNeededResidents() {
        EntityManager manager = factory.createEntityManager();
        CriteriaBuilder builder = manager.getCriteriaBuilder();
        CriteriaQuery<Integer> personIdcriteriaQuery = builder.createQuery(Integer.class);

        Root<Ownership> ownershipRoot = personIdcriteriaQuery.from(Ownership.class);

        personIdcriteriaQuery.select(
                    ownershipRoot.get("person").get("id"))
                .groupBy(ownershipRoot.get("person"))
                .having(builder.le(builder.count(ownershipRoot.get("flat")), 1));
        List<Integer> ownersIds = manager.createQuery(personIdcriteriaQuery).getResultList();


        CriteriaQuery<Person> personCriteriaQuery = builder.createQuery(Person.class);

        Root<Person> personRoot = personCriteriaQuery.from(Person.class);
        Predicate predicate = personRoot.get("id").in(ownersIds);
        Predicate predicate1 = builder.equal(personRoot.get("parkingRight"), 1);
        Predicate predicate2  = builder.gt(personRoot.get("residentialFlat").get("id"), 0);

        personCriteriaQuery
                .where(builder.and(predicate, predicate1, predicate2));


        List<Person> resultList = manager.createQuery(personCriteriaQuery).getResultList();
        for (Person person : resultList) {
            System.out.println(person);
        }
        manager.close();
    }


}

