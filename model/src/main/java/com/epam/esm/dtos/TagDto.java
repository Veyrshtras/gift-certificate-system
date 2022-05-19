package com.epam.esm.dtos;

import com.epam.esm.entities.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.function.Function;

@Getter
@Setter
public class TagDto implements Function<Tag, TagDto> {
    private String name;

    public TagDto(){}

    public TagDto(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TagDto)) return false;
        TagDto tagDto = (TagDto) o;
        return getName().equals(tagDto.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public TagDto apply(Tag tag) {
        return new TagDto(tag.getName());
    }
}
