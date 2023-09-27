package com.spynad.firstservice.model;

import java.util.Objects;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "personArray")
public class PersonArray {
    private Long page;
    private Long pagesTotal;
    private Long pageSize;
    private List<Person> persons = new ArrayList<Person>();


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
    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
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
                Objects.equals(persons, personArray.persons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page, pagesTotal, pageSize, persons);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class PersonArray {\n");

        sb.append("    page: ").append(toIndentedString(page)).append("\n");
        sb.append("    pagesTotal: ").append(toIndentedString(pagesTotal)).append("\n");
        sb.append("    pageSize: ").append(toIndentedString(pageSize)).append("\n");
        sb.append("    tickets: ").append(toIndentedString(persons)).append("\n");
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

