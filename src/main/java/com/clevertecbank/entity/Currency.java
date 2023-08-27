package com.clevertecbank.entity;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Currency {
    @EqualsAndHashCode.Exclude
    private long id;
    private String code;
}
