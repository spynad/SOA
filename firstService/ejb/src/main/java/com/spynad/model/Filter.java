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
public class Filter implements Serializable {
    private static final long serialVersionUID = -6000092L;
    private String fieldName;
    private String nestedName;
    private FilteringOperation filteringOperation;
    private String fieldValue;
}
