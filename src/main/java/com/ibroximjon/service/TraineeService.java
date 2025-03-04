package com.ibroximjon.service;

import com.ibroximjon.model.Trainee;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TraineeService {
    private final SessionFactory sessionFactory;

    public TraineeService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createTrainee(Trainee trainee) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(trainee);
        tx.commit();
        session.close();
        System.out.println("Trainee created successfully");
    }

    public Trainee getTraineeByUsername(String username) {
        Session session = sessionFactory.openSession();
        Trainee trainee = session.createQuery("FROM Trainee WHERE username = :username", Trainee.class)
                .setParameter("username", username)
                .uniqueResult();
        session.close();
        return trainee;
    }

    public void updateTraineeAddress(String username, String address) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Trainee trainee = getTraineeByUsername(username);
        if (trainee != null) {
            trainee.setAddress(address);
            session.update(trainee);
        }
        tx.commit();
        session.close();
    }

    public void changeTraineePassword(String username, String password) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Trainee trainee = getTraineeByUsername(username);
        if (trainee != null) {
            trainee.setPassword(password);
            session.update(trainee);
        }
        tx.commit();
        session.close();
    }

    public void toggleTraineeStatus(String username, boolean isActive) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Trainee trainee = getTraineeByUsername(username);
        if (trainee != null) {
            trainee.setActive(isActive);
            session.update(trainee);
        }
        tx.commit();
        session.close();
    }
    public void deleteTraineeByUsername(String username) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Trainee trainee = getTraineeByUsername(username);
        if (trainee != null) {
            session.remove(trainee);
        }
        tx.commit();
        session.close();
        System.out.println("Trainee deleted successfully");
    }
}
