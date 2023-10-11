package org.goit.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Ownership implements MarkEntities{

    @Transient
    private static final String sqlQuery = "SELECT o FROM Ownership o";
    @Id
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person person;

    @Id
    @ManyToOne
    @JoinColumn(name = "flat_id", referencedColumnName = "id")
    private Flat flat;

    @Override
    public String toString() {
        return "Ownership{" +
                "personId=" + person.getId() +
                ", flatId=" + flat.getId() +
                '}';
    }

    @Override
    public String getSqlQuery(){
        return sqlQuery;
    }

    //
    @Override
    public void clone(MarkEntities entity) {
        Ownership ownership = (Ownership) entity;
        person = ownership.getPerson();
        flat = ownership.getFlat();
    }

    //метод необхідний для знаходження сутності за id, проте в цьому класі первинним ключем виступають 2 поля.
    @Override
    public Ownership findById(EntityManager manager, int id){
        return null;
    }

    //метод необхідний для видалення сутності за id, проте в цьому класі первинним ключем виступають 2 поля.
    @Override
    public void delete(EntityManager manager, int id) {

    }
}
