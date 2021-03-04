package ru.sapteh.DaoImp;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.Model.Client;

import java.util.List;

public class DaoImpClient implements DAO<Client,Integer> {
    private final SessionFactory factory;

    public DaoImpClient(SessionFactory factory) {
        this.factory = factory;
    }

    public void create(Client client) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(client);
            session.getTransaction().commit();
        }

    }

    public void delete(Client client) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(client);
            session.getTransaction().commit();
        }

    }

    public void update(Client client) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(client);
            session.getTransaction().commit();
        }

    }

    public Client read(Integer integer) {
        try(Session session = factory.openSession()){
            Client result = session.get(Client.class,integer);
            return result;
        }

    }

    public List<Client> readByAll() {
        try(Session session = factory.openSession()){
            Query<Client> result = session.createQuery("FROM Client");
            Hibernate.initialize(result.list());
            return result.list();
        }
    }
}
