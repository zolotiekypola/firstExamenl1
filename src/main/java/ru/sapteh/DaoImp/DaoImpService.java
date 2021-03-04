package ru.sapteh.DaoImp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.Model.Client;
import ru.sapteh.Model.Service;

import java.util.List;

public class DaoImpService implements DAO<Service,Integer> {
    private final SessionFactory factory;

    public DaoImpService(SessionFactory factory) {
        this.factory = factory;
    }

    public void create(Service service) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(service);
            session.getTransaction().commit();
        }

    }

    public void delete(Service service) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(service);
            session.getTransaction().commit();
        }

    }

    public void update(Service service) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(service);
            session.getTransaction().commit();
        }

    }

    public Service read(Integer integer) {
        try(Session session = factory.openSession()){
            Service result = session.get(Service.class,integer);
            return result;
        }

    }

    public List<Service> readByAll() {
        try(Session session = factory.openSession()){
            Query<Service> result = session.createQuery("FROM Service");
            return result.list();
        }
    }
}
