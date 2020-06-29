package com.example.hellorestapiproject2;

import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Repository
@Transactional
public class VehicleDAO {

    @PersistenceContext
    private EntityManager entityManager;
    private int year = Calendar.getInstance().get(Calendar.YEAR);


    public void create(Vehicle vehicle) {
        entityManager.persist(vehicle);
        return;
    }


    public Vehicle getById(int id) {
        return entityManager.find(Vehicle.class, id);
    }

    public void update(Vehicle vehicle) {
        entityManager.merge(vehicle);
    }

    public void delete(int id) {
        Vehicle g = entityManager.find(Vehicle.class, id);
        entityManager.remove(g);
    }


    public List<Vehicle> getLastTen() {
        Query q = entityManager.createNativeQuery("select * from vehicle ORDER BY id DESC LIMIT 10");
        List<Vehicle> list = new ArrayList<>();
        list.addAll(q.getResultList());
        return list;
    }



    public List<Vehicle> tenYearOrOlderDiscount() {

        int backTen = this.year - 10;

        Query q = entityManager.createNativeQuery("select * from vehicle where year<" + backTen + ";", Vehicle.class);
        List<Vehicle> list = new ArrayList<>();
        list.addAll(q.getResultList());



        return list;
    }
    public List<Vehicle> annualDevaluation() {
        Query q = entityManager.createNativeQuery("select * from vehicle where year !=" + this.year , Vehicle.class);
        List<Vehicle> list = new ArrayList<>();
        list.addAll(q.getResultList());
        return list;
    }

}
