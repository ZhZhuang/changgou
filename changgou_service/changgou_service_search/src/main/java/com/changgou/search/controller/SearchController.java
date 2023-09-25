package com.changgou.search.controller;

import com.changgou.entity.Page;
import com.changgou.search.pojo.SkuInfo;
import com.changgou.search.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    //http://localhost:9009/search/list?keywords=%E6%89%8B%E6%9C%BA&brand=%E5%8D%8E%E4%B8%BA&

    @GetMapping("/list")
    public String list(@RequestParam Map<String,String> searchMap,Model model){

        //特殊符号处理
        this.handleSearchMap(searchMap);

        //获取查询结果
        Map resultMap = searchService.search(searchMap);
        model.addAttribute("result",resultMap);
        model.addAttribute("searchMap",searchMap);


        // 测试
        System.out.println(resultMap.get("total"));
        System.out.println(resultMap.get("pageNum"));


        //封装分页数据并返回
        //1.总记录数
        //2.当前页
        //3.每页显示多少条
        Page<SkuInfo> page = new Page<SkuInfo>(
                Long.parseLong(String.valueOf( resultMap.get("total"))),
                Integer.parseInt(String.valueOf(resultMap.get("pageNum"))),
                Page.pageSize
        );
        model.addAttribute("page",page);

        //拼装url
        StringBuilder url = new StringBuilder("/search/list");
        if (searchMap != null && searchMap.size()>0){
            //是由查询条件
            url.append("?");
            for (String paramKey : searchMap.keySet()) {
                if (!"sortRule".equals(paramKey) && !"sortField".equals(paramKey) && !"pageNum".equals(paramKey)){
                    url.append(paramKey).append("=").append(searchMap.get(paramKey)).append("&");
                }
            }
            //
            String urlString = url.toString();
            //去除路径上的最后一个&
            urlString=urlString.substring(0,urlString.length()-1);
            model.addAttribute("url",urlString);
        }else{
            model.addAttribute("url",url);
        }
        return "search";
    }

    @GetMapping
    @ResponseBody
    public Map search(@RequestParam Map<String,String> searchMap){
        //特殊符号处理
        this.handleSearchMap(searchMap);
        Map searchResult = searchService.search(searchMap);
        return searchResult;
    }

    private void handleSearchMap(Map<String, String> searchMap) {
        Set<Map.Entry<String, String>> entries = searchMap.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            if (entry.getKey().startsWith("spec_")){
                searchMap.put(entry.getKey(),entry.getValue().replace("+","%2B"));
            }
        }
    }
}


//package com.changgou.search.controller;
//
//import com.changgou.search.pojo.SkuInfo;
//import com.changgou.search.service.ESManagerService;
//import com.changgou.search.service.SearchService;
//import org.springframework.beans.factory.annotation.Autowired;
//import com.changgou.entity.Page;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//import java.util.Set;
//
////@RestController //返回json
//@Controller
//@RequestMapping("/sku_search")
//public class SearchController {
//    @Autowired
//    private ESManagerService esManagerService;
//    @Autowired
//    private SearchService searchService;
//
//    @GetMapping("/list")
//    public String list(@RequestParam Map<String,String> searchMap, Model model){
//
//        //特殊符号处理
//        this.handleSearchMap(searchMap);
//
//        //获取查询结果
//        Map resultMap = searchService.search(searchMap);
//        model.addAttribute("result",resultMap);
//        model.addAttribute("searchMap",searchMap);
//
//        //封装分页数据并返回
//        //1.总记录数
//        //2.当前页
//        //3.每页显示多少条
//        Page<SkuInfo> page = new Page<SkuInfo>(
//                Long.parseLong(String.valueOf( resultMap.get("total"))),
//                Integer.parseInt(String.valueOf(resultMap.get("pageNum"))),
//                Page.pageSize
//        );
//        model.addAttribute("page",page);
//
//        //拼装url
//        StringBuilder url = new StringBuilder("/search/list");
//        if (searchMap != null && searchMap.size()>0){
//            //是由查询条件
//            url.append("?");
//            for (String paramKey : searchMap.keySet()) {
//                if (!"sortRule".equals(paramKey) && !"sortField".equals(paramKey) && !"pageNum".equals(paramKey)){
//                    url.append(paramKey).append("=").append(searchMap.get(paramKey)).append("&");
//                }
//            }
//            //http://localhost:9009/search/list?keywords=手机&spec_网络制式=4G&
//            String urlString = url.toString();
//            //去除路径上的最后一个&
//            urlString=urlString.substring(0,urlString.length()-1);
//            model.addAttribute("url",urlString);
//        }else{
//            model.addAttribute("url",url);
//        }
//        return "search";
//    }
//
//    /**
//     * 全文检索
//     * @return
//     */
//    @GetMapping
//    public Map search(@RequestParam Map<String, String> paramMap) throws Exception {
//        //特殊符号处理
//        handlerSearchMap(paramMap);
//        Map resultMap = searchService.search(paramMap);
//        return resultMap;
//    }
//
//    //对搜索入参带有特殊符号进行处理
//    public void handlerSearchMap(Map<String,String> searchMap){
//        if(null != searchMap){
//            Set<Map.Entry<String, String>> entries = searchMap.entrySet();
//            for (Map.Entry<String, String> entry : entries) {
//                if(entry.getKey().startsWith("spec_")){
//                    searchMap.put(entry.getKey(),entry.getValue().replace("+","%2B"));
//                }
//            }
//        }
//    }
//
//    private void handleSearchMap(Map<String, String> searchMap) {
//        Set<Map.Entry<String, String>> entries = searchMap.entrySet();
//        for (Map.Entry<String, String> entry : entries) {
//            if (entry.getKey().startsWith("spec_")){
//                searchMap.put(entry.getKey(),entry.getValue().replace("+","%2B"));
//            }
//        }
//    }
//}