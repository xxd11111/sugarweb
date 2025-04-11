package com.sugarweb.digitalHuman.component.llm.thought;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleMsg {

    private String role;

    private String content;

}