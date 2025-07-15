package com.PrintLab.service;

import com.PrintLab.dto.OrderTransactionDto;
import com.PrintLab.dto.PaperMarketRatesDto;

import java.util.List;

public interface OrderTransactionService {
    OrderTransactionDto save(OrderTransactionDto orderTransactionDto);
    List<OrderTransactionDto> getAll();
    List<OrderTransactionDto> searchByPlateDimension(String plateDimension);
    OrderTransactionDto findById(Long id);
    void deleteById(Long id);
    OrderTransactionDto update(Long id, OrderTransactionDto orderTransactionDto);

}
