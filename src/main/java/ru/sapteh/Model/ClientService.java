package ru.sapteh.Model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "clientService")
public class ClientService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private Date startTime;

    @Column
    private String comment;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "clientId")
    private Client client;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "serviceId")
    private Service service;

    @Override
    public String toString() {
        return "ClientService{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", comment='" + comment + '\'' +
                '}';
    }
}
