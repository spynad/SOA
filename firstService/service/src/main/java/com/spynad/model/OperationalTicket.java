package com.spynad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "operationalTicket")
@Entity
@Table(name = "tickets_buffer")
public class OperationalTicket {
    @Id
    @GeneratedValue
    private Long id;
    private Long operationId;
    @Getter
    private StatusEnum status;
    private Long ticketId;
    private String name;
    @Embedded()
    private Coordinates coordinates;
    private Date creationDate;
    private Integer price;
    private Long discount;
    private Boolean refundable;
    private Ticket.TypeEnum type;
    private Long personId;

    public enum StatusEnum {
        PENDING("PENDING"),
        CANCELED("CANCELED"),
        SAVED("SAVED");

        private String value;

        StatusEnum(String value) {this.value = value;}

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        public static StatusEnum fromValue(String value){
            return Arrays.stream(StatusEnum.values())
                    .filter(e -> Objects.equals(e.value, value))
                    .findFirst()
                    .orElse(null);
        }
    }

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
    public Ticket.TypeEnum getType() {
        return type;
    }

    public void setType(Ticket.TypeEnum type) {
        this.type = type;
    }

    @Schema(example = "40", description = "")
    @JsonProperty("person_id")
    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getOperationId() {
        return operationId;
    }

    public void setOperationId(Long operationId) {
        this.operationId = operationId;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Boolean getRefundable() {
        return refundable;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }
}
