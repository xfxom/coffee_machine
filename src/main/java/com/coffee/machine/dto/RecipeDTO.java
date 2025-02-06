package com.coffee.machine.dto;

import java.util.List;
import lombok.Data;

@Data
public class RecipeDTO {
    private String name;
    private List<RecipeComponentDTO> components;
}
