package org.projet.projetspring.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String relationshipType;

    @ManyToOne
    @JoinColumn(name = "fromUser", nullable = false)
    private Person fromUser;

    @ManyToOne
    @JoinColumn(name = "toUser", nullable = false)
    private Person toUser;

    public Relationship(String relationshipType, Person fromUser, Person toUser) {
        this.relationshipType = relationshipType;
        this.fromUser = fromUser;
        this.toUser = toUser;
    }


}
