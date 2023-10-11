package org.goit.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Building implements MarkEntities{

    @Transient
    private static final String sqlQuery = "SELECT b FROM Building b";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "num_of_flats")
    private int numOfFlats;

    private String address;

    @OneToMany(mappedBy = "building", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Flat> flats;

    @Override
    public String toString() {
        return "Building{" +
                "id=" + id +
                ", numOfFlats=" + numOfFlats +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public String getSqlQuery(){
        return sqlQuery;
    }

    @Override
    public void clone(MarkEntities entity) {
        Building building = (Building) entity;
        numOfFlats = building.getNumOfFlats();
        address = building.getAddress();
    }

    @Override
    public Building findById(EntityManager manager, int id) {
        return manager.find(Building.class, id);
    }

    @Override
    public void delete(EntityManager manager, int id) throws Exception{
        Building buildingToRemove = findById(manager, id);
        if (buildingToRemove == null){
            throw new Exception("Building with id " + id + " does not exist");
        }
        EntityTransaction transaction = manager.getTransaction();
        List<Flat> flats1 = buildingToRemove.getFlats();
        for (Flat flat : flats1) {
            flat.delete(manager, flat.getId());
        }
        transaction.begin();
        manager.remove(buildingToRemove);
        transaction.commit();
    }
}
