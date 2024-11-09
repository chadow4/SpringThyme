package org.projet.projetspring.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RequestDto {
    private Long toUser;
    private Long fromUser;
    private String requestStr;

}