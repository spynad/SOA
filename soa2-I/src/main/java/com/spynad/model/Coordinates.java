package com.spynad.model;

import java.util.Objects;
import java.util.ArrayList;
import java.util.HashMap;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import javax.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


public class Coordinates {
    private Long id;
    private Float x;
    private Float y;


    @Schema(example = "10", description = "")
    @JsonProperty("id")
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @Schema(example = "15", description = "")
    @JsonProperty("x")
    public Float getX() {
        return x;
    }
    public void setX(Float x) {
        this.x = x;
    }

    @Schema(example = "15", description = "")
    @JsonProperty("y")
    @DecimalMin("-235")  public Float getY() {
        return y;
    }
    public void setY(Float y) {
        this.y = y;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinates coordinates = (Coordinates) o;
        return Objects.equals(id, coordinates.id) &&
                Objects.equals(x, coordinates.x) &&
                Objects.equals(y, coordinates.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, x, y);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Coordinates {\n");

        sb.append("    id: ").append(toIndentedString(id)).append("\n");
        sb.append("    x: ").append(toIndentedString(x)).append("\n");
        sb.append("    y: ").append(toIndentedString(y)).append("\n");
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

