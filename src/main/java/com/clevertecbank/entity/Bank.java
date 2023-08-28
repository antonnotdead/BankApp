package com.clevertecbank.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Bank {
    @EqualsAndHashCode.Exclude
    private long id;
    private String bank_name;
}
