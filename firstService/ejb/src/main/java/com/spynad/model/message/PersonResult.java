package com.spynad.model.message;

import com.spynad.model.Person;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonResult implements Serializable {
    @Serial
    private static final long serialVersionUID = -983578L;
    private String message;
    private Person result;
}
