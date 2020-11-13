/**
 * FileName: EstateController
 * Author:   liaocm
 * Date:     2020/11/13 15:28
 * Description:
 * History:
 */
package com.lcm.esdemo.estate.controller;

import com.lcm.esdemo.estate.entity.ESEstate;
import com.lcm.esdemo.estate.service.EstateRepository;
import org.elasticsearch.common.geo.GeoDistance;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.GeoDistanceQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.GeoDistanceSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("estate")
public class EstateController {

    private List<ESEstate> estates;
    @Autowired
    private EstateRepository estateRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @PostMapping("addEstate")
    public String addEstate(@RequestBody ESEstate estate) {
        estate.setLocation(estate.getLat() + ", " + estate.getLon());
        estateRepository.save(estate);

        return "success";
    }

    @PostMapping("deleteEstate")
    public String deleteEstate(@RequestBody ESEstate estate) {
        if(null == estate) {
            estateRepository.deleteAll();
            return "deleteAll success";
        }
        estateRepository.delete(estate);
        return "success";
    }

    @GetMapping("queryEstate")
    public List<ESEstate> queryEstate(String param, BigDecimal lat, BigDecimal lon) {
        //距离
        Double distance = 2000.0;

        //构建距离查询
        GeoDistanceQueryBuilder builder = QueryBuilders
                //设置位置字段
                .geoDistanceQuery("location")
                //设置经纬度
                .point(lat.doubleValue(), lon.doubleValue())
                //设置距离
                .distance(distance, DistanceUnit.METERS)
                .geoDistance(GeoDistance.ARC);
        //构建排序方式
        GeoDistanceSortBuilder sortBuilder = SortBuilders
                //设置位置字段
                .geoDistanceSort("location", lat.doubleValue(), lon.doubleValue())
                //设置距离单位
                .unit(DistanceUnit.METERS)
                //升序
                .order(SortOrder.ASC);
        //查询条件
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder()
                .withFilter(builder)
                .withSort(sortBuilder);

        List<ESEstate> esEstates = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), ESEstate.class);
        return esEstates;
//        if(StringUtils.isEmpty(param)) {
//            return estateRepository.findAll();
//        }
//        return estateRepository.findByEstateNameOrAddress(param, param);
    }

}
