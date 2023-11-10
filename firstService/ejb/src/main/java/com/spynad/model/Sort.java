package com.spynad.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sort implements Serializable {
    private static final long serialVersionUID = -67830692995L;
    private boolean desc;
    private String fieldName;
    private String nestedName;
}
