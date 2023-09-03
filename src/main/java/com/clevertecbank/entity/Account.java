package com.clevertecbank.entity;

import lombok.*;

import java.sql.Date;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Account {
    @EqualsAndHashCode.Exclude
    private long id;
    private String account_number;
    private long bank_id;
    private long user_id;
    private long currency_id;
    private Date creation_date;
    private double moneyValue;
}
