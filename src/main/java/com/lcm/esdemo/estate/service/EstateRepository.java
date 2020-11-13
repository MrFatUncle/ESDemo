/**
 * FileName: EstateRepository
 * Author:   liaocm
 * Date:     2020/11/13 15:25
 * Description: 小区搜索类
 * History:
 */
package com.lcm.esdemo.estate.service;

import com.lcm.esdemo.estate.entity.ESEstate;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface EstateRepository extends ElasticsearchCrudRepository<ESEstate, Integer> {

    List<ESEstate> findByEstateNameOrAddress(String estateName, String address);

}
