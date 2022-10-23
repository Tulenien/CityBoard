package com.CityBoard.ui.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paged<T> {
    private Page<T> page;
    private Paging paging;
}
