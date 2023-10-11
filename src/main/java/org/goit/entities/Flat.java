package org.goit.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Flat implements MarkEntities{

    @Transient
    private static final String sqlQuery = "SELECT f FROM Flat f";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "building_id", referencedColumnName = "id")
    private Building building;

    @Column(name = "apartment_number")
    private int apartmentNumber;

    @Column(name = "area")
    private int area;

    @Column(name ="floor")
    private int floor;

    @Column(name = "num_of_rooms")
    private int numOfRooms;

    @OneToMany(mappedBy = "residentialFlat", cascade = CascadeType.DETACH)
    List<Person> persons;

    @OneToMany(mappedBy = "flat", cascade = CascadeType.REMOVE)
    private List<Ownership> ownerships;

    @Override
    public String toString() {
        return "Flat{" +
                "id=" + id +
                ", building=" + building +
                ", apartmentNumber=" + apartmentNumber +
                ", area=" + area +
                ", floor=" + floor +
                ", numOfRooms=" + numOfRooms +
                '}';
    }

    @Override
    public String getSqlQuery(){
        return sqlQuery;
    }

    @Override
    public void clone(MarkEntities entity) {
        Flat flat = (Flat) entity;
        building = flat.getBuilding();
        apartmentNumber = flat.apartmentNumber;
        area = flat.getArea();
        floor = flat.getFloor();
        numOfRooms = flat.numOfRooms;
    }

    @Override
    public Flat findById(EntityManager manager, int id) {
        return manager.find(Flat.class, id);
    }

    @Override
    public void delete(EntityManager manager, int id) throws Exception{
        Flat flatToRemove = findById(manager, id);
        if (flatToRemove == null){
            throw new Exception("Flat with id " + id + " does not exist");
        }
        List<Person> residents = flatToRemove.getPersons();
        for (Person person : residents) {
            person.setResidentialFlat(null);
        }
        EntityTransaction transaction = manager.getTransaction();
        transaction.begin();
        manager.remove(flatToRemove);
        transaction.commit();
    }
}
