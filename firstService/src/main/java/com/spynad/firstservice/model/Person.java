package com.spynad.firstservice.model;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementRef;
import jakarta.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement(name = "person")
public class Person {
    @Id
    @GeneratedValue
    private Long id;
    private Float weight;
    @Enumerated(EnumType.STRING)
    private EyeColorEnum eyeColor;
    @Enumerated(EnumType.STRING)
    private HairColorEnum hairColor;
    @Enumerated(EnumType.STRING)
    private CountryEnum country;

    @OneToMany(mappedBy = "person", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Ticket> tickets;

    public enum EyeColorEnum {
        GREEN("GREEN"),
        RED("RED"),
        ORANGE("ORANGE");

        private String value;

        EyeColorEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        public static EyeColorEnum fromValue(String value){
            return Arrays.stream(EyeColorEnum.values())
                    .filter(e -> Objects.equals(e.value, value))
                    .findFirst()
                    .orElse(null);
        }
    }


    public enum HairColorEnum {
        RED("RED"),
        YELLOW("YELLOW"),
        ORANGE("ORANGE"),
        BROWN("BROWN");
        private String value;

        HairColorEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        public static HairColorEnum fromValue(String value){
            return Arrays.stream(HairColorEnum.values())
                    .filter(e -> Objects.equals(e.value, value))
                    .findFirst()
                    .orElse(null);
        }
    }

    public enum CountryEnum {
        RUSSIA("RUSSIA"),
        USA("USA"),
        FRANCE("FRANCE"),
        SPAIN("SPAIN"),
        JAPAN("JAPAN");
        private String value;

        CountryEnum(String value) {
            this.value = value;
        }

        @Override
        @JsonValue
        public String toString() {
            return String.valueOf(value);
        }

        public static CountryEnum fromValue(String value){
            return Arrays.stream(CountryEnum.values())
                    .filter(e -> Objects.equals(e.value, value))
                    .findFirst()
                    .orElse(null);
        }
    }

    @Schema(example = "10", description = "")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Schema(example = "150", description = "")
    @JsonProperty("weight")
    @DecimalMin("1")
    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    @Schema(description = "")
    @JsonProperty("eyeColor")
    public EyeColorEnum getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(EyeColorEnum eyeColor) {
        this.eyeColor = eyeColor;
    }

    @Schema(required = true, description = "")
    @JsonProperty("hairColor")
    @NotNull
    public HairColorEnum getHairColor() {
        return hairColor;
    }

    public void setHairColor(HairColorEnum hairColor) {
        this.hairColor = hairColor;
    }

    @Schema(description = "")
    @JsonProperty("country")
    public CountryEnum getCountry() {
        return country;
    }

    public void setCountry(CountryEnum country) {
        this.country = country;
    }

    @JsonProperty("ticket")
    public List<Ticket> getTickets() {
        return tickets;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(id, person.id) &&
                Objects.equals(weight, person.weight) &&
                Objects.equals(eyeColor, person.eyeColor) &&
                Objects.equals(hairColor, person.hairColor) &&
                Objects.equals(country, person.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, weight, eyeColor, hairColor, country);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Person {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
        sb.append("    eyeColor: ").append(toIndentedString(eyeColor)).append("\n");
        sb.append("    hairColor: ").append(toIndentedString(hairColor)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
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

