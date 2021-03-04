package ru.sapteh.DaoImp;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.Model.Client;
import ru.sapteh.Model.Users;

import java.util.List;

public class DaoImpUsers implements DAO<Users,Integer> {
    private final SessionFactory factory;

    public DaoImpUsers(SessionFactory factory){
        this.factory = factory;
    }


    @Override
    public void create(Users users) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(users);
            session.getTransaction().commit();
        }
    }

    @Override
    public void delete(Users users) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(users);
            session.getTransaction().commit();
        }
    }

    @Override
    public void update(Users users) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(users);
            session.getTransaction().commit();
        }
    }

    @Override
    public Users read(Integer integer) {
        try(Session session = factory.openSession()){
            Users result = session.get(Users.class,integer);
            return result;
        }
    }

    @Override
    public List<Users> readByAll() {
        try(Session session = factory.openSession()){
            Query<Users> result = session.createQuery("FROM Users");
            Hibernate.initialize(result.list());
            return result.list();
        }
    }
}
