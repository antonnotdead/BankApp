package com.clevertecbank.entity;

import lombok.*;

import java.sql.Timestamp;
import java.util.Date;
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
    private int value;
    private Timestamp date;
}
