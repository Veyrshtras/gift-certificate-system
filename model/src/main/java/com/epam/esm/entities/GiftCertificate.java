package com.epam.esm.entities;

import com.epam.esm.dtos.TagDto;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class GiftCertificate extends BaseEntity {

    private String description;
    private BigDecimal price;
    private int duration;
    private String createDate;
    private String lastUpdateDate;
    private List<TagDto> tags;


}
