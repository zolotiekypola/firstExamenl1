package ru.sapteh.Model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
//@RequiredArgsConstructor
@Entity
@Table(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String title;

    @Column
    private int cost;

    @Column(name = "DurationInSeconds")
    private int durationInSeconds;

    @Column
    private String description;

    @Column
    private double discount;

    @Column
    private String mainImagePath;

    @OneToMany(mappedBy = "service")
    private Set<ClientService> clientServiceSet;

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", cost=" + cost +
                ", durationInSecond=" + durationInSeconds +
                ", description=" + description +
                ", discount=" + discount +
                ", mainImagePath='" + mainImagePath + '\'' +
                '}';
    }
}
