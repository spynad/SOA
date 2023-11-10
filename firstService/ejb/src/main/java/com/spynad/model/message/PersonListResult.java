package com.spynad.model.message;

import com.spynad.model.Person;
import com.spynad.model.PersonArray;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonListResult implements Serializable {
    @Serial
    private static final long serialVersionUID = -983478L;
    private String message;
    private PersonArray result;
    private int status;
}
