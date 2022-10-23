package com.CityBoard.ui.pagination;

import com.CityBoard.models.enums.PageItemType;

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
