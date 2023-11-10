package com.spynad.model;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlSeeAlso;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "page")
@XmlSeeAlso({Person.class, Ticket.class})
public class Page<T> implements Serializable {
    private static final long serialVersionUID = -55855396708051371L;
    private List<T> objects;
    private Integer page;
    private Integer pageSize;
    private Integer totalPages;
    private Long totalCount;
}
