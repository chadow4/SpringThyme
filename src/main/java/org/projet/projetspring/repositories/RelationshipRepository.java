package org.projet.projetspring.repositories;

import org.projet.projetspring.models.Person;
import org.projet.projetspring.models.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, Long> {
    @Query("SELECT r.relationshipType, COUNT(r.relationshipType) FROM Relationship r GROUP BY r.relationshipType ORDER BY COUNT(r.relationshipType) DESC")
    Set<String> findSortedRelationTypes();

    @Query("SELECT p FROM Person p WHERE p.id IN " +
            "(SELECT r.toUser.id FROM Relationship r WHERE r.fromUser.id = :userid " +
            " UNION " +
            "SELECT r.fromUser.id FROM Relationship r WHERE r.toUser.id = :userid)")
   List<Person> findAllByFriendWith(@Param("userid") Double userid);

    @Query("SELECT p FROM Person p WHERE p.id IN " +
            "(SELECT r.toUser.id FROM Relationship r WHERE r.fromUser.firstName = :firstname AND r.fromUser.lastName = :lastname" +
            " UNION " +
            "SELECT r.fromUser.id FROM Relationship r WHERE r.toUser.firstName = :firstname AND r.toUser.lastName = :lastname)")
    List<Person> findAllByFriendWithFirstNameLastNameOfFriend(@Param("firstname") String firstname, @Param("lastname") String lastname);
}