package org.goit.entities;

import jakarta.persistence.*;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.List;


@Data
@Entity
public class Person implements MarkEntities{
    @Transient
    private static final String sqlQuery = "SELECT p FROM Person p";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "parking_right")
    private int parkingRight;
    @ManyToOne
    @JoinColumn(name = "residential_flat", referencedColumnName = "id")
    private Flat residentialFlat;

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE)
    private List<Ownership> ownerships;


    @Override
    public String getSqlQuery(){
        return sqlQuery;
    }


    @Override
    public void clone(MarkEntities entity) {
       Person personToClone = (Person) entity;
       name = personToClone.getName();
       email = personToClone.getEmail();
       phone = personToClone.getPhone();
       parkingRight = personToClone.getParkingRight();
       residentialFlat = personToClone.getResidentialFlat();
    }

    @Override
    public Person findById(EntityManager manager, int id) {
        return manager.find(Person.class, id);
    }

    @Override
    public void delete(EntityManager manager, int id) {
        Person personToRemove = findById(manager, id);
        EntityTransaction transaction = manager.getTransaction();

        transaction.begin();
        manager.remove(personToRemove);
        transaction.commit();
    }

    @Override
    public String toString() {
        String flatId = this.residentialFlat == null ? null : String.valueOf(residentialFlat.getId());
        String address = this.residentialFlat == null ? "not resident" : residentialFlat.getBuilding().getAddress();
        String area = this.residentialFlat == null ? null : String.valueOf(residentialFlat.getArea());
        return "Person{" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", address= " + address +
                ", flat # " + flatId +
                ", area " + area +
                ", parkingRight=" + parkingRight +
                ", residentialFlatId=" + flatId +
                '}';
    }
}
