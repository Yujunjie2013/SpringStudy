package com.example.mongodemo.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
@Getter
@Setter
@ToString
public class Coffee {
    @Id
    private String id;
    private String name;
    private BigDecimal price;
    private Date createTime;
    private Date updateTime;
}
