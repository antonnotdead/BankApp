package com.clevertecbank.entity;

import lombok.*;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class User {
    @EqualsAndHashCode.Exclude
    private long id;
    private String first_name;
    private String surname;
    private String patronymic;
}
