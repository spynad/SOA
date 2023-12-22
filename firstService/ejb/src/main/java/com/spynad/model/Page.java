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
@XmlRootElement
public class Page implements Serializable {
    private static final long serialVersionUID = -55855396708051371L;
    private Long page;
    private Long pageTotal;
    private Long pageSize;
    private TicketsArray ticketsArray;
}
