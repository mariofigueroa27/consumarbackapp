package com.fabrica.hutchisonspring.repository;

import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Statement;

@Repository
public class UpdateRoroOperationRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public void updateTable() {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE o SET o.vehicle_id = v.id FROM operacion_roro o inner join hu_vehicle v on v.chassis = o.chassis");
        });
    }
}
