package com.ibroximjon.model;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "trainee")
public class Trainee extends User{

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    @Column()
    private String address;

    @ManyToMany
    @JoinTable(name = "trainee_trainer",
            joinColumns = @JoinColumn(name = "trainee_id"),
            inverseJoinColumns = @JoinColumn(name = "trainer_id"))
    private List<Trainer> trainers;



    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void setTrainers(List<Trainer> trainers) {
        this.trainers = trainers;
    }
}
