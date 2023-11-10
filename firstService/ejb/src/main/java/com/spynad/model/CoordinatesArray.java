package com.spynad.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoordinatesArray implements Serializable {
    private static final long serialVersionUID = 558553967080513790L;
    private Long page;
    private Long pagesTotal;
    private Long pageSize;
    private List<Coordinates> tickets = new ArrayList<Coordinates>();

    @JsonProperty("page")
    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    @JsonProperty("pagesTotal")
    public Long getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Long pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    @JsonProperty("pageSize")
    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    @JsonProperty("tickets")
    public List<Coordinates> getTickets() {
        return tickets;
    }

    public void setTickets(List<Coordinates> tickets) {
        this.tickets = tickets;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CoordinatesArray coordinatesArray = (CoordinatesArray) o;
        return Objects.equals(page, coordinatesArray.page) &&
                Objects.equals(pagesTotal, coordinatesArray.pagesTotal) &&
                Objects.equals(pageSize, coordinatesArray.pageSize) &&
                Objects.equals(tickets, coordinatesArray.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pagesTotal, pageSize, tickets);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class CoordinatesArray {\n");

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
