package org.bainsight.market.Model.Dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VolumeWrapper {
    private String exchange;
    private long volume;
}
