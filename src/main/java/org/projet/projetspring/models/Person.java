package org.projet.projetspring.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    @Lob
    private String profil;

    @OneToMany(mappedBy = "fromUser", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Relationship> askedRelationships;

    @OneToMany(mappedBy = "toUser", fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Relationship> requestedRelationships;

    public Person(String firstName, String lastName, LocalDate birthDate, String profil) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.profil = profil;
    }

    public List<Relationship> getAllFriendships() {
        List<Relationship> allRelationship = new ArrayList<>();
        if (askedRelationships != null) allRelationship.addAll(askedRelationships);
        if (requestedRelationships != null) allRelationship.addAll(requestedRelationships);
        return allRelationship;
    }

}
