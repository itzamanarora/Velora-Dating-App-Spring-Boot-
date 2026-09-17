package com.aman.Velora.user_service.mapper;

import com.aman.Velora.user_service.dto.page.PageResponseDTO;
import org.springframework.data.domain.Page;

public class PageDTOMapper {
    public static <T> PageResponseDTO<T> mapToPageResponse(Page<T> page) {
        return PageResponseDTO.<T>builder()
                .results(page.getContent())
                .page(page.getNumber())
                .pageSize(page.getSize())
                .count(page.getNumberOfElements())
                .totalPages(page.getTotalPages())
                .build();
    }
}
