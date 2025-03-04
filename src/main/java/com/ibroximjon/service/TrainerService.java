package com.ibroximjon.service;


import com.ibroximjon.model.Trainer;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class TrainerService {
    private final SessionFactory sessionFactory;

    public TrainerService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createTrainer(Trainer trainer) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        session.persist(trainer);
        tx.commit();
        session.close();
        System.out.println("Trainer created successfully");
    }
    public Trainer getTrainerByUsername(String username) {
        Session session = sessionFactory.openSession();
        Trainer trainer = session.createQuery("FROM Trainer WHERE username = :username", Trainer.class)
                .setParameter("username", username)
                .uniqueResult();
        session.close();
        return trainer;
    }

    public void updateTrainerSpecialization(String username, String specialization) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Trainer trainer = getTrainerByUsername(username);
        if (trainer != null) {
            trainer.setSpecialization(specialization);
            session.update(trainer);
        }
        tx.commit();
        session.close();
    }

    public void changeTrainerPassword(String username, String password) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Trainer trainer = getTrainerByUsername(username);
        if (trainer != null) {
            trainer.setPassword(password);
            session.update(trainer);
        }
        tx.commit();
        session.close();
    }

    public void toggleTrainerStatus(String username, boolean isActive) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        Trainer trainer = getTrainerByUsername(username);
        if (trainer != null) {
            trainer.setActive(isActive);
            session.update(trainer);
        }
        tx.commit();
        session.close();
    }
}
