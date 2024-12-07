package org.projet.projetspring.dtos;


import java.time.LocalDate;

public record PersonDto(Long id, String firstName, String lastName, LocalDate birthDate, String profil) {}
