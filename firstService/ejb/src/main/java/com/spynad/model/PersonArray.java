package com.spynad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "personArray")
public class PersonArray implements Serializable {
    private static final long serialVersionUID = -345678532L;
    private Long page;
    private Long pagesTotal;
    private Long pageSize;
    private List<Person> persons = new ArrayList<Person>();


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


    @XmlElement(name = "person")
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }


    @Override
    public boolean equals(Object o) {
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
    private String toIndentedString(Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}

