package com.changgou.goods.controller;


import com.changgou.goods.pojo.Brand;
import com.changgou.goods.service.BrandService;

import com.changgou.pojo.PageResult;
import com.changgou.pojo.Result;
import com.changgou.pojo.StatusCode;
import com.github.pagehelper.Page;  // 注意，不是pojo中的page
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController     // = @Controller和@ResponseBody
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    BrandService brandService;

    // http://localhost:9011/brand
    @GetMapping
    public Result findAll(){
        List<Brand> brandList = brandService.findAll();
        return new Result(true, StatusCode.OK, "查找成功", brandList);
    }

    @GetMapping("/{id}")
    public Result findById(@PathVariable Integer id){
        Brand brand = brandService.findById(id);
        return new Result(true,StatusCode.OK,"查找成功",brand);
    }

    @PostMapping
    public Result add(@RequestBody Brand brand){
        brandService.add(brand);
        return new Result(true,StatusCode.OK,"添加成功");
    }

    /***
     * 修改品牌数据
     * @param brand
     * @param id
     * @return
     */
    @PutMapping(value="/{id}")
    public Result update(@RequestBody Brand brand,@PathVariable Integer id){
        brand.setId(id);
        brandService.update(brand);
        return new Result(true,StatusCode.OK,"修改成功");
    }

    /**
     * 根据id删除
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable int id){
        brandService.delete(id);
        return new Result(true,StatusCode.OK,"删除成功");
    }

    /***
     * 多条件搜索品牌数据
     * @param searchMap
     * @return
     */
    @GetMapping(value = "/search" )
    public Result findList(@RequestParam Map searchMap){
        List<Brand> list = brandService.findList(searchMap);
        return new Result(true,StatusCode.OK,"查询成功",list);
    }

    /***
     * 分页搜索实现
     * @param page
     * @param size
     * @return
     */
    @GetMapping(value = "/search/{page}/{size}" )
    public Result findPage(@PathVariable int page, @PathVariable int size){

        int i =1/0;     // ? 异常默认是谁处理的
        Page<Brand> pageList = brandService.findPage(page, size);
        PageResult pageResult=new PageResult(pageList.getTotal(),pageList.getResult());
        return new Result(true,StatusCode.OK,"查询成功",pageResult);
    }
}
