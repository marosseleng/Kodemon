package cz.muni.fi.pa165.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by mseleng on 10/19/16.
 */
@Entity
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;
}
