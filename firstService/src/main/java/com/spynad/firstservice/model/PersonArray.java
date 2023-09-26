package com.spynad.firstservice.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.List;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public class PersonArray {
    private Long page;
    private Long pagesTotal;
    private Long pageSize;
    private List<Person> tickets = new ArrayList<Person>();


    @Schema(example = "0", description = "")
    @JsonProperty("page")
    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    @Schema(example = "100", description = "")
    @JsonProperty("pagesTotal")
    public Long getPagesTotal() {
        return pagesTotal;
    }

    public void setPagesTotal(Long pagesTotal) {
        this.pagesTotal = pagesTotal;
    }

    @Schema(example = "100", description = "")
    @JsonProperty("pageSize")
    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }


    @Schema(description = "")
    @JsonProperty("tickets")
    public List<Person> getTickets() {
        return tickets;
    }

    public void setTickets(List<Person> tickets) {
        this.tickets = tickets;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonArray personArray = (PersonArray) o;
        return Objects.equals(page, personArray.page) &&
                Objects.equals(pagesTotal, personArray.pagesTotal) &&
                Objects.equals(pageSize, personArray.pageSize) &&
                Objects.equals(tickets, personArray.tickets);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pagesTotal, pageSize, tickets);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonArray {\n");

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
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

