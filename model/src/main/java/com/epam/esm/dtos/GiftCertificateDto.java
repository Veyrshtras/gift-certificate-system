package com.epam.esm.dtos;

import com.epam.esm.entities.GiftCertificate;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

@Getter
@Setter
public class GiftCertificateDto implements Function<GiftCertificate, GiftCertificateDto> {

    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String lastUpdateDate;
    private List<TagDto> tags;

    public GiftCertificateDto(){};

    public GiftCertificateDto(String name, String description, BigDecimal price, Integer duration, String lastUpdateDate, List<TagDto> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.lastUpdateDate = lastUpdateDate;
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GiftCertificateDto)) return false;
        GiftCertificateDto dto = (GiftCertificateDto) o;
        return getName().equals(dto.getName()) && getDescription().equals(dto.getDescription()) && getDuration().equals(dto.getDuration()) && getLastUpdateDate().equals(dto.getLastUpdateDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getDescription(), getDuration(), getLastUpdateDate());
    }

    @Override
    public GiftCertificateDto apply(GiftCertificate certificate) {

        GiftCertificateDto dto=new GiftCertificateDto();

        dto.setName(certificate.getName());
        dto.setDescription(certificate.getDescription());
        dto.setPrice(certificate.getPrice());
        dto.setDuration(certificate.getDuration());
        dto.setLastUpdateDate(certificate.getLastUpdateDate());

        List<TagDto> allTagDtos=new ArrayList<>();
        for (TagDto item:
                certificate.getTags()) {
            allTagDtos.add(item);
        }
        dto.setTags(allTagDtos);

        return dto;
    }
}
