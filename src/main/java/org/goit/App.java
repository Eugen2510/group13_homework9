package org.goit;

import jakarta.persistence.*;
import org.goit.connection_properties.PathConstants;
import org.goit.crud.CRUD;
import org.goit.entities.*;
import org.goit.flyway.MigrationExecutor;

import java.util.List;


/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        MigrationExecutor executor = new MigrationExecutor(PathConstants.DB_CONNECTION_PROPERTIES_FILE_PATH);
        executor.executeMigrations();

        EntityManagerFactory factory = Persistence.createEntityManagerFactory("residential_complex");
        EntityManager manager = factory.createEntityManager();
        CRUD crud = new CRUD(factory);

        //create entity
        Building building = new Building();
        building.setAddress("Stusa 1");
        building.setNumOfFlats(131);
        crud.createEntity(building);

        //update entity
        Person person = new Person();
        person.setName("aaa");
        person.setEmail("@@@");
        person.setPhone("444444");
        person.setResidentialFlat((Flat) crud.getEntityById(new Flat(), 1));
        crud.updateEntity(person, 1);

        //delete entity
        Flat flat = new Flat();
        crud.delete(flat, 1);

        //get all
        List<Building> allBuildings = crud.getAllEntities(new Building());
        for (Building building1 : allBuildings) {
            System.out.println(building1);
        }

        System.out.println();

        //get entities according to hw instructions
        crud.getNeededResidents();
        factory.close();
        manager.close();
    }
}
