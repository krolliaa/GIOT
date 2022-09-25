package com.kk.service.cms.pojo.vo;

import com.kk.service.cms.pojo.AdPosition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdReturnVo {
    private String id;
    private String positionId;
    private String title;
    private String imageUrl;
    private String color;
    private String linkUrl;
    private Integer sort;
    private AdPosition adPosition;
}
