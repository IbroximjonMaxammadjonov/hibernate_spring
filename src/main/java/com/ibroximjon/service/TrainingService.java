package com.ibroximjon.service;

import com.ibroximjon.model.Trainee;
import com.ibroximjon.model.Trainer;
import com.ibroximjon.model.Training;
import com.ibroximjon.model.TrainingType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Date;
import java.util.List;

public class TrainingService {
    private final SessionFactory sessionFactory;

    public TrainingService(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void createTraining(String trainingName, int duration, Date trainingDate, String trainingTypeName, List<String>traineeUsernames, List<String>trainerUsernames) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        TrainingType trainingType = session.createQuery("FROM TrainingType WHERE typeName = :name", TrainingType.class)
                .setParameter("name", trainingTypeName)
                .uniqueResult();

        if (trainingType == null) {
            trainingType = new TrainingType();
            trainingType.setTypeName(trainingTypeName);
            session.persist(trainingType);
        }

        List<Trainee> trainees = session.createQuery("FROM Trainee WHERE username IN (:usernames)", Trainee.class)
                .setParameter("usernames", traineeUsernames)
                .list();

        List<Trainer> trainers = session.createQuery("FROM Trainer WHERE username IN (:usernames)", Trainer.class)
                .setParameter("usernames", trainerUsernames)
                .list();

        Training training = new Training();
        training.setTrainingName(trainingName);
        training.setDuration(duration);
        training.setTrainingDate(trainingDate);
        training.setTrainingType(trainingType);
        training.setTrainers(trainers);
        training.setTrainees(trainees);

        session.persist(training);
        tx.commit();
        session.close();
        System.out.println("Training " + trainingName + " created");
    }

    public List<Training> getTraineeTrainings(String traineeUsername, Date fromDate, Date toDate, String trainerName, String trainingTypeName) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT t FROM Training t JOIN t.trainees tr WHERE tr.username = :username";

        if (fromDate != null) hql += " AND t.trainingDate >= :fromDate";
        if (toDate != null) hql += " AND t.trainingDate <= :toDate";
        if (trainerName != null) hql += " AND EXISTS (SELECT 1 FROM t.trainers trn WHERE trn.firstName = :trainerName)";
        if (trainingTypeName != null) hql += " AND t.trainingType.typeName = :trainingTypeName";

        var query = session.createQuery(hql, Training.class);
        query.setParameter("username", traineeUsername);
        if (fromDate != null) query.setParameter("fromDate", fromDate);
        if (toDate != null) query.setParameter("toDate", toDate);
        if (trainerName != null) query.setParameter("trainerName", trainerName);
        if (trainingTypeName != null) query.setParameter("trainingTypeName", trainingTypeName);

        List<Training> trainings = query.list();
        session.close();
        return trainings;
    }

    public List<Training> getTrainerTrainings(String trainerUsername, Date fromDate, Date toDate, String traineeName) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT t FROM Training t JOIN t.trainers tr WHERE tr.username = :username";

        if (fromDate != null) hql += " AND t.trainingDate >= :fromDate";
        if (toDate != null) hql += " AND t.trainingDate <= :toDate";
        if (traineeName != null) hql += " AND EXISTS (SELECT 1 FROM t.trainees trn WHERE trn.firstName = :traineeName)";

        var query = session.createQuery(hql, Training.class);
        query.setParameter("username", trainerUsername);
        if (fromDate != null) query.setParameter("fromDate", fromDate);
        if (toDate != null) query.setParameter("toDate", toDate);
        if (traineeName != null) query.setParameter("traineeName", traineeName);

        List<Training> trainings = query.list();
        session.close();
        return trainings;
    }

    public List<Trainer> getUnassignedTrainers(String traineeUsername) {
        Session session = sessionFactory.openSession();
        String hql = "SELECT t FROM Trainer t WHERE t NOT IN (SELECT tr FROM Trainee trn JOIN trn.trainers tr WHERE trn.username = :username)";

        var query = session.createQuery(hql, Trainer.class);
        query.setParameter("username", traineeUsername);

        List<Trainer> trainers = query.list();
        session.close();
        return trainers;
    }

    public void updateTraineeTrainers(String traineeUsername, List<String> trainerUsernames) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Trainee trainee = session.createQuery("FROM Trainee WHERE username = :username", Trainee.class)
                .setParameter("username", traineeUsername)
                .uniqueResult();

        if (trainee != null) {
            List<Trainer> trainers = session.createQuery("FROM Trainer WHERE username IN (:usernames)", Trainer.class)
                    .setParameter("usernames", trainerUsernames)
                    .list();
            trainee.setTrainers(trainers);
            session.update(trainee);
        }

        tx.commit();
        session.close();
    }

}
