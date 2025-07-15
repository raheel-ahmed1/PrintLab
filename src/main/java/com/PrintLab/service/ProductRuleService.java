package com.PrintLab.service;

import com.PrintLab.dto.ProductRuleDto;
import com.PrintLab.dto.SettingDto;

import java.util.List;

public interface ProductRuleService {
    ProductRuleDto save(ProductRuleDto productRuleDto);
    Boolean checkTitle(String title);
    List<ProductRuleDto> getAllProductRule();
    List<ProductRuleDto> searchByTitle(String title);
    ProductRuleDto getProductRuleById(Long id);
    ProductRuleDto update(Long id,ProductRuleDto productRuleDto);
    void deleteById(Long id);
}
