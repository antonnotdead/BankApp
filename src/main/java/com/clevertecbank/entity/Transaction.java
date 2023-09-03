package com.clevertecbank.entity;

import lombok.*;

import java.sql.Timestamp;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class Transaction {
    @EqualsAndHashCode.Exclude
    private long id;
    private long sender_id;
    private long receiver_id;
    private double transaction_value;
    private Timestamp date;
    private Transaction_type type;
}
