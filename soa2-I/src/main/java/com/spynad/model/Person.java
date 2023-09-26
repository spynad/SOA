package com.spynad.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


public class Person   {
    private Long id;
    private Float weight;

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
    }
    private EyeColorEnum eyeColor;

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
    }
    private HairColorEnum hairColor;

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
    }
    private CountryEnum country;

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
    @DecimalMin("1")  public Float getWeight() {
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

