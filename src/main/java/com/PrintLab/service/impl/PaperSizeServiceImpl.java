package com.PrintLab.service.impl;

import com.PrintLab.dto.PaperSizeDto;
import com.PrintLab.exception.RecordNotFoundException;
import com.PrintLab.model.*;
import com.PrintLab.repository.PaperSizeRepository;
import com.PrintLab.service.PaperSizeService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PaperSizeServiceImpl implements PaperSizeService {
    private final PaperSizeRepository paperSizeRepository;
    public PaperSizeServiceImpl(PaperSizeRepository paperSizeRepository) {
        this.paperSizeRepository = paperSizeRepository;
    }

    @Override
    @Transactional
    public PaperSizeDto save(PaperSizeDto paperSizeDto) {
        PaperSize paperSize = paperSizeRepository.save(toEntity(paperSizeDto));
        return toDto(paperSize);
    }

    @Override
    public List<PaperSizeDto> getAll() {
        List<PaperSize> paperSizeList = paperSizeRepository.findByStatus("Active");
        List<PaperSizeDto> paperSizeDtoList = new ArrayList<>();

        for (PaperSize paperSize : paperSizeList) {
            PaperSizeDto paperSizeDto = toDto(paperSize);
            paperSizeDtoList.add(paperSizeDto);
        }
        return paperSizeDtoList;
    }

    @Override
    public List<PaperSizeDto> searchByLabel(String label) {
        List<PaperSize> paperSizeList = paperSizeRepository.findPaperSizesByLabel(label);
        List<PaperSizeDto> paperSizeDtoList = new ArrayList<>();

        for (PaperSize paperSize : paperSizeList) {
            PaperSizeDto paperSizeDto = toDto(paperSize);
            paperSizeDtoList.add(paperSizeDto);
        }
        return paperSizeDtoList;
    }

    @Override
    public PaperSizeDto findById(Long id){
        Optional<PaperSize> optionalPaperSize = paperSizeRepository.findById(id);

        if (optionalPaperSize.isPresent()) {
            PaperSize paperSize = optionalPaperSize.get();
            return toDto(paperSize);
        } else {
            throw new RecordNotFoundException(String.format("Paper Size not found for id => %d", id));
        }
    }

    @Override
    public PaperSizeDto findByLabel(String label) {
        Optional<PaperSize> paperSizeOptional = Optional.ofNullable(paperSizeRepository.findByLabel(label));

        if(paperSizeOptional.isPresent()){
            PaperSize paperSize = paperSizeOptional.get();
            return toDto(paperSize);
        }
        else {
            throw new RecordNotFoundException(String.format("Paper Size not found at => %s", label));
        }
    }

    @Override
    @Transactional
    public String deleteById(Long id) {
        Optional<PaperSize> optionalPaperSize = paperSizeRepository.findById(id);

        if (optionalPaperSize.isPresent()) {
            PaperSize paperSize = optionalPaperSize.get();
            paperSizeRepository.setStatusInactive(id);
        } else {
            throw new RecordNotFoundException(String.format("Paper Size not found for id => %d", id));
        }
        return null;
    }

    @Override
    @Transactional
    public PaperSizeDto updatePaperSize(Long id, PaperSizeDto paperSizeDto) {
        Optional<PaperSize> optionalPaperSize = paperSizeRepository.findById(id);
        if (optionalPaperSize.isPresent()) {
            PaperSize existingPaperSize = optionalPaperSize.get();
            existingPaperSize.setLabel(paperSizeDto.getLabel());

            PaperSize updatedPaperSize = paperSizeRepository.save(existingPaperSize);
            return toDto(updatedPaperSize);
        } else {
            throw new RecordNotFoundException(String.format("Paper Size not found for id => %d", id));
        }
    }

    public PaperSizeDto toDto(PaperSize paperSize) {
        return PaperSizeDto.builder()
                .id(paperSize.getId())
                .label(paperSize.getLabel())
                .status(paperSize.getStatus())
                .build();
    }

    public PaperSize toEntity(PaperSizeDto paperSizeDto) {
        return PaperSize.builder()
                .id(paperSizeDto.getId())
                .label(paperSizeDto.getLabel())
                .status(paperSizeDto.getStatus())
                .build();
    }
}
