package com.changgou.goods.feign;

import com.changgou.goods.pojo.Sku;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "goods")
public interface SkuFeign {

    @GetMapping("/sku/spu/{spuId}")
    public List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId);
}


//package com.changgou.goods.feign;
//
//import com.changgou.goods.pojo.Sku;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.util.List;
//
//@FeignClient(name="goods")
//@RequestMapping("/sku")       # 问题在这
//public interface SkuFeign {
//    /***
//     * 多条件搜索品牌数据
//     * @param spuId
//     * @return
//     */
//    @GetMapping("/sku/spu/{spuId}")
//    public List<Sku> findSkuListBySpuId(@PathVariable("spuId") String spuId);
//}