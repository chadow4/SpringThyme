package org.projet.projetspring.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Relationship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "person1_id", nullable = false)
    private Person person1;

    @ManyToOne
    @JoinColumn(name = "person2_id", nullable = false)
    private Person person2;

    private String relationshipType;
    
}
