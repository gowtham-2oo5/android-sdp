package com.android_sdp.server.dtos;

import com.android_sdp.server.models.Item;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class MenuDTO {
    private Long id;
    private Long restaurantId;
    private List<Item> items;

    public MenuDTO(Long id, Long id1, List<Item> items) {
    }
}
