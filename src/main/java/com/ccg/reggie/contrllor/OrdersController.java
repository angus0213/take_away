package com.ccg.reggie.contrllor;

import com.ccg.reggie.common.R;
import com.ccg.reggie.entity.Orders;
import com.ccg.reggie.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;
    @PostMapping("/submit")
    public R<String> submit (@RequestBody Orders orders) {
        ordersService.submit(orders);
        return R.success("提交成功");

    }
}
