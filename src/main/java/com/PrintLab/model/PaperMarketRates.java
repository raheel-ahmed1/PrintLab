package com.PrintLab.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "paper_market_rates")
public class PaperMarketRates
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    private LocalDateTime timeStamp;

    private String paperStock;
    private String brand;
    private String madeIn;
    private Integer GSM;
    private Double length;
    private Double width;
    private String dimension;
    private Integer qty;
    private Double kg;
    private String recordType;
    private Double ratePkr;
    private Boolean verified;
    private String notes;
    private String status;
    private Long vendor;
}
