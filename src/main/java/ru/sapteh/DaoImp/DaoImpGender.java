package ru.sapteh.DaoImp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.Model.Client;
import ru.sapteh.Model.Gender;

import java.util.List;

public class DaoImpGender implements DAO<Gender, Integer> {
    private final SessionFactory factory;

    public DaoImpGender(SessionFactory factory) {
        this.factory = factory;
    }

    public void create(Gender gender) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(gender);
            session.getTransaction().commit();
        }

    }

    public void delete(Gender gender) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(gender);
            session.getTransaction().commit();
        }

    }

    public void update(Gender gender) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(gender);
            session.getTransaction().commit();
        }

    }

    public Gender read(Integer integer) {
        try(Session session = factory.openSession()){
            Gender result = session.get(Gender.class,integer);
            return result;
        }

    }

    public List<Gender> readByAll() {
        try(Session session = factory.openSession()){
            Query<Gender> result = session.createQuery("FROM Gender");
            return result.list();
        }
    }
}
