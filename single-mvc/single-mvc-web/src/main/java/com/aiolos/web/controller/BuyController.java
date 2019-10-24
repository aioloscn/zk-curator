package com.aiolos.web.controller;

import com.aiolos.common.utils.CustomizeJSONResult;
import com.aiolos.web.service.BuyService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 订购商品controller
 * @author Aiolos
 * @date 2019-10-24 15:03
 */
@Controller
public class BuyController {

    @Autowired
    private BuyService buyService;

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/buy")
    @ResponseBody
    public CustomizeJSONResult doGetlogin(String itemId) {

        if (StringUtils.isNotBlank(itemId)) {
            buyService.doBuyItem(itemId);
        } else {
            return CustomizeJSONResult.errorMsg("商品id不能为空");
        }

        return CustomizeJSONResult.ok();
    }
}
