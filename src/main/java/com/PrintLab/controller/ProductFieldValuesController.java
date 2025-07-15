package com.PrintLab.controller;

import com.PrintLab.dto.ProductFieldValuesDto;
import com.PrintLab.model.ProductFieldValues;
import com.PrintLab.service.ProductFieldValuesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pfv")
public class ProductFieldValuesController {

    private final ProductFieldValuesService productFieldValuesService;

    public ProductFieldValuesController(ProductFieldValuesService productFieldValuesService) {
        this.productFieldValuesService = productFieldValuesService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductFieldValuesDto> createProductField(@RequestBody ProductFieldValuesDto productFieldValuesDto) {
        try {
            return ResponseEntity.ok(productFieldValuesService.save(productFieldValuesDto));
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<ProductFieldValuesDto>> getAllProductField() {
        List<ProductFieldValuesDto> productFieldValuesDtoList = productFieldValuesService.getAll();
        return ResponseEntity.ok(productFieldValuesDtoList);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductFieldValuesDto> getProductFieldById(@PathVariable Long id) {
        try {
            ProductFieldValuesDto productFieldValuesDto = productFieldValuesService.findById(id);
            return ResponseEntity.ok(productFieldValuesDto);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteProductField(@PathVariable Long id) {
        return ResponseEntity.ok(productFieldValuesService.deleteById(id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ProductFieldValuesDto> updateProductField(@PathVariable Long id, @RequestBody ProductFieldValues productFieldValues) {
        try {
            ProductFieldValuesDto updatedPfVDto = productFieldValuesService.updatedProductField(id, productFieldValues);
            return ResponseEntity.ok(updatedPfVDto);
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
