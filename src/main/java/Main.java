import com.ibroximjon.Alien;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;




public class Main {
    public static void main(String[] args) {
       Alien a = new Alien();
        a.setAid(101);
        a.setAname("ibroximjon");
        a.setColor("blue");

        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        SessionFactory sf = configuration.buildSessionFactory();
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(a);

    }
}
