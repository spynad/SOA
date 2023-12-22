package com.spynad.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@XmlRootElement(name = "ticket")
@Entity
public class Ticket implements Serializable {

    @Serial
    private static final long serialVersionUID = -4858435645L;
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Embedded()
    private Coordinates coordinates;

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    private Date creationDate;
    private Integer price;
    private Long discount;
    private Boolean refundable;
    private TypeEnum typeStr;
    @ManyToOne
    @JoinColumn(name = "person_id", nullable = true)
    @XmlElementRef(name = "Person")
    private Person person;

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

        public static TypeEnum fromValue(String value){
            return Arrays.stream(TypeEnum.values())
                    .filter(e -> Objects.equals(e.value, value))
                    .findFirst()
                    .orElse(null);
        }
    }

    @JsonProperty("id")
    @NotNull
    @Min(1L)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty("name")
    @NotNull
    @Size(min = 1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("creationDate")
    @NotNull
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @JsonProperty("price")
    @NotNull
    @Min(1)
    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @JsonProperty("discount")
    @Min(1L)
    @Max(101L)
    public Long getDiscount() {
        return discount;
    }

    public void setDiscount(Long discount) {
        this.discount = discount;
    }

    @JsonProperty("refundable")
    @NotNull
    public Boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(Boolean refundable) {
        this.refundable = refundable;
    }

    @JsonProperty("type")
    public TypeEnum getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(TypeEnum typeStr) {
        this.typeStr = typeStr;
    }

    @JsonProperty("person")
    public Person getPerson() {
        return person;
    }

    public void setPersonId(Person person) {
        this.person = person;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
                Objects.equals(name, ticket.name) &&
                Objects.equals(coordinates.getX(), ticket.coordinates.getX()) &&
                Objects.equals(coordinates.getY(), ticket.coordinates.getY()) &&
                Objects.equals(creationDate, ticket.creationDate) &&
                Objects.equals(price, ticket.price) &&
                Objects.equals(discount, ticket.discount) &&
                Objects.equals(refundable, ticket.refundable) &&
                Objects.equals(typeStr, ticket.typeStr) &&
                Objects.equals(person, ticket.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates.getX(), coordinates.getY(), creationDate, price, discount, refundable, typeStr, person);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Ticket {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    name: ").append(toIndentedString(name)).append("\n");
        sb.append("    coordinatesX: ").append(toIndentedString(coordinates.getX())).append("\n");
        sb.append("    coordinatesY: ").append(toIndentedString(coordinates.getY())).append("\n");
        sb.append("    creationDate: ").append(toIndentedString(creationDate)).append("\n");
        sb.append("    price: ").append(toIndentedString(price)).append("\n");
        sb.append("    discount: ").append(toIndentedString(discount)).append("\n");
        sb.append("    refundable: ").append(toIndentedString(refundable)).append("\n");
        sb.append("    type: ").append(toIndentedString(typeStr)).append("\n");
        sb.append("    person: ").append(toIndentedString(person)).append("\n");
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
