package com.PrintLab.model;

import com.PrintLab.dto.projectEnums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "product_field")
public class ProductField {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;
    private LocalDate created_at;
    private Integer sequence;

    @Enumerated(EnumType.STRING)
    private Type type;

    @OneToMany(mappedBy = "productField", cascade = CascadeType.ALL)
    private List<ProductFieldValues> productFieldValuesList;
}
