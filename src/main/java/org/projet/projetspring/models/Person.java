package org.projet.projetspring.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "person1")
    @ToString.Exclude
    private List<Relationship> friendshipsAsPerson1;

    @OneToMany(mappedBy = "person2")
    @ToString.Exclude
    private List<Relationship> friendshipsAsPerson2;

    public List<Relationship> getAllFriendships() {
        List<Relationship> allFriendships = new ArrayList<>();
        if (friendshipsAsPerson1 != null) allFriendships.addAll(friendshipsAsPerson1);

        if (friendshipsAsPerson2 != null) allFriendships.addAll(friendshipsAsPerson2);
        return allFriendships;
    }

}
