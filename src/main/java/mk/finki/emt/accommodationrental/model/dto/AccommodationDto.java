package mk.finki.emt.accommodationrental.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import mk.finki.emt.accommodationrental.model.enums.Category;
import mk.finki.emt.accommodationrental.model.enums.Condition;

public class AccommodationDto {

    @NotBlank
    private String name;

    @NotNull
    private Category category;

    @NotNull
    private Condition condition;

    @NotNull
    private Long hostId;

    @NotNull
    @Positive
    private Integer numRooms;

    public AccommodationDto() {
    }

    public AccommodationDto(String name, Category category, Condition condition, Long hostId, Integer numRooms) {
        this.name = name;
        this.category = category;
        this.condition = condition;
        this.hostId = hostId;
        this.numRooms = numRooms;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
    }

    public Integer getNumRooms() {
        return numRooms;
    }

    public void setNumRooms(Integer numRooms) {
        this.numRooms = numRooms;
    }
}