package com.spynad.firstservice.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Date;

import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;

public class Ticket {
    private Long id;
    private String name;
    private Long coordinatesId;
    private Date creationDate;
    private Integer price;
    private Long discount;
    private Boolean refundable;

    public enum TypeEnum {
        VIP("VIP"),
        USUAL("USUAL"),
        BUDGETARY("BUDGETARY"),
        CHEAP("CHEAP");
        private String value;

        TypeEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }
    }

    private TypeEnum type;
    private Long personId;


    @Schema(example = "10", required = true, description = "")
    @JsonProperty("id")
    @NotNull
    @Min(1L)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Schema(example = "Ticket name", required = true, description = "")
    @JsonProperty("name")
    @NotNull
    @Size(min = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Schema(example = "40", description = "")
    @JsonProperty("coordinatesId")
    public Long getCoordinatesId() {
        return coordinatesId;
    }

    public void setCoordinatesId(Long coordinatesId) {
        this.coordinatesId = coordinatesId;
    }

    @Schema(required = true, description = "")
    @JsonProperty("creationDate")
    @NotNull
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Schema(example = "10", required = true, description = "")
    @JsonProperty("price")
    @NotNull
    @Min(1)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Schema(example = "40", description = "")
    @JsonProperty("discount")
    @Min(1L)
    @Max(101L)
    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    @Schema(required = true, description = "")
    @JsonProperty("refundable")
    @NotNull
    public Boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    @Schema(description = "")
    @JsonProperty("type")
    public TypeEnum getType() {
        return type;
    }

    public void setType(TypeEnum type) {
        this.type = type;
    }

    @Schema(example = "40", description = "")
    @JsonProperty("personId")
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
                Objects.equals(name, ticket.name) &&
                Objects.equals(coordinatesId, ticket.coordinatesId) &&
                Objects.equals(creationDate, ticket.creationDate) &&
                Objects.equals(price, ticket.price) &&
                Objects.equals(discount, ticket.discount) &&
                Objects.equals(refundable, ticket.refundable) &&
                Objects.equals(type, ticket.type) &&
                Objects.equals(personId, ticket.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinatesId, creationDate, price, discount, refundable, type, personId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Ticket {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    coordinatesId: ").append(toIndentedString(coordinatesId)).append("\n");
        sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
        sb.append("    refundable: ").append(toIndentedString(refundable)).append("\n");
        sb.append("    type: ").append(toIndentedString(type)).append("\n");
        sb.append("    personId: ").append(toIndentedString(personId)).append("\n");
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
