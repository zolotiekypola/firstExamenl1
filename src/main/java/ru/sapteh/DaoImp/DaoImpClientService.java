package ru.sapteh.DaoImp;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.sapteh.DAO.DAO;
import ru.sapteh.Model.Client;
import ru.sapteh.Model.ClientService;

import java.util.List;

public class DaoImpClientService implements DAO<ClientService,Integer> {
    private final SessionFactory factory;

    public DaoImpClientService(SessionFactory factory) {
        this.factory = factory;
    }

    public void create(ClientService clientService) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.save(clientService);
            session.getTransaction().commit();
        }

    }

    public void delete(ClientService clientService) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.delete(clientService);
            session.getTransaction().commit();
        }

    }

    public void update(ClientService clientService) {
        try(Session session = factory.openSession()){
            session.beginTransaction();
            session.update(clientService);
            session.getTransaction().commit();
        }

    }

    public ClientService read(Integer integer) {
        try(Session session = factory.openSession()){
            ClientService result = session.get(ClientService.class,integer);
            return result;
        }

    }

    public List<ClientService> readByAll() {
        try(Session session = factory.openSession()){
            Query<ClientService> result = session.createQuery("FROM ClientService");
            return result.list();
        }
    }
}
