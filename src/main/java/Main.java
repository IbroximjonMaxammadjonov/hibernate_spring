import com.ibroximjon.model.Trainer;
import com.ibroximjon.model.Training;
import com.ibroximjon.service.TrainingService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

        TrainingService trainingService = new TrainingService(sessionFactory);

        // Yangi training qo‘shish
        trainingService.createTraining(
                "Spring Boot Advanced",
                10,
                new Date(),
                "Backend Development",
                List.of("ali123"),  // Trainees
                List.of("aziz99")   // Trainers
        );

        // Trainee-ning treninglarini olish
        List<Training> trainings = trainingService.getTraineeTrainings("ali123", null, null, null, null);
        trainings.forEach(t -> System.out.println("Trainee Training: " + t.getTrainingName()));

        // Trainer-ning treninglarini olish
        List<Training> trainerTrainings = trainingService.getTrainerTrainings("aziz99", null, null, null);
        trainerTrainings.forEach(t -> System.out.println("Trainer Training: " + t.getTrainingName()));

        // Unassigned trainers olish
        List<Trainer> unassigned = trainingService.getUnassignedTrainers("ali123");
        unassigned.forEach(t -> System.out.println("Unassigned Trainer: " + t.getFirstName()));

        sessionFactory.close();
    }
}
