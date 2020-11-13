/**
 * FileName: ESEstate
 * Author:   liaocm
 * Date:     2020/11/13 15:23
 * Description: ES-小区对象
 * History:
 */
package com.lcm.esdemo.estate.entity;

import com.lcm.esdemo.es.constant.ESConstant;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.math.BigDecimal;

@Data
@Document(indexName = ESConstant.INDEX_GMRFID, type = "estate")
public class ESEstate {

    /**
     * id
     */
    private Integer id;
    /**
     * 小区名称
     */
    private String estateName;
    /**
     * 地址
     */
    private String address;
    /**
     * 位置信息
     */
    @GeoPointField
    private String location;
    /**
     * 精度
     */
    private BigDecimal lat;
    /**
     * 纬度
     */
    private BigDecimal lon;
}
