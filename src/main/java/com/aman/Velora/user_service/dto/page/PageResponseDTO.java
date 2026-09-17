package com.aman.Velora.user_service.dto.page;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PageResponseDTO<T> {
    private List<T> results;
    private int page;
    private int pageSize;
    private long count;
    private int totalPages;
}
