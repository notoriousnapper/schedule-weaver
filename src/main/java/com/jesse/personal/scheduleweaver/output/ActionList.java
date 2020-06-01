package com.jesse.personal.scheduleweaver.output;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ActionList {

    private String group; //folder
    private String name; //file
    private List<String> content;
}
