package com.sportsbet.depthcharts.model;

import lombok.*;

@RequiredArgsConstructor
@Data
public class Player {
    @NonNull
    private Integer id;
    @NonNull
    private String name;
    private String position;
}
