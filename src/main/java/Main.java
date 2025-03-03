
import com.ibroximjon.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.Date;
import java.util.List;


public class Main {
    public static void main(String[] args) {


        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sf = configuration.buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();

        Trainee trainee = new Trainee();
        trainee.setFirstName("John");
        trainee.setLastName("Doe");
        trainee.setUsername("johndoe");
        trainee.setPassword("password");
        trainee.setActive(true);

        Trainer trainer = new Trainer();
        trainer.setFirstName("Kamil");
        trainer.setLastName("Karate");
        trainer.setUsername("karate");
        trainer.setPassword("password");
        trainer.setActive(true);
        trainer.setSpecialization("Java developer");

        trainee.setTrainers(List.of(trainer));
        trainer.setTrainees(List.of(trainee));



        Training training = new Training();
        training.setTrainingName("Spring Boot Masterclass");
        training.setTrainingDate(new Date());
        training.setDuration(10);




        session.save(trainee);
        session.save(trainer);
        session.save(training);
        session.getTransaction().commit();

    }
}
