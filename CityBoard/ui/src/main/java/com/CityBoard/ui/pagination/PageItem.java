package com.CityBoard.ui.pagination;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PageItem {

    private PageItemType pageItemType;

    private int index;

    private boolean active;

}
