package com.iq47.booking.model.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@XmlRootElement(name = "ticketsArray")
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class TicketsArray {
    private Long page;
    private Long pagesTotal;
    private Long pageSize;
    private List<Ticket> tickets = new ArrayList<Ticket>();


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TicketsArray ticketsArray = (TicketsArray) o;
        return Objects.equals(page, ticketsArray.page) &&
                Objects.equals(pagesTotal, ticketsArray.pagesTotal) &&
                Objects.equals(pageSize, ticketsArray.pageSize) &&
                Objects.equals(tickets, ticketsArray.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pagesTotal, pageSize, tickets);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class TicketsArray {\n");

        sb.append("    page: ").append(toIndentedString(page)).append("\n");
        sb.append("    pagesTotal: ").append(toIndentedString(pagesTotal)).append("\n");
        sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
        sb.append("    tickets: ").append(toIndentedString(tickets)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
