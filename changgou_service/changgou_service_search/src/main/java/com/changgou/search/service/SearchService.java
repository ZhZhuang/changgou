package com.changgou.search.service;

import java.util.Map;

public interface SearchService {

    //按照查询条件进行数据查询
    Map search(Map<String,String> searchMap);

    /**
     * 全文检索
     * @param paramMap 查询参数
     * @return
     */
    //public Map search(Map<String, String> paramMap) throws Exception;
}
