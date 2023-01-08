package com.ccg.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccg.reggie.entity.Category;
import com.ccg.reggie.entity.Dish;
import com.ccg.reggie.entity.Setmeal;
import com.ccg.reggie.mapper.CategoryMapper;
import com.ccg.reggie.service.CategoryService;
import com.ccg.reggie.service.DishService;
import com.ccg.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ccg.reggie.common.customException;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        dishLambdaQueryWrapper.eq(Dish::getCategoryId, id);
        int count= dishService.count(dishLambdaQueryWrapper);
        if (count>0) {
            throw new customException("关联菜品，不能删除");

        }

        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId, id);
        int count2=setmealService.count(setmealLambdaQueryWrapper);
        if (count2>0) {
            throw new customException("关联套餐，不能删除");

        }

        super.removeById(id);

    }
}
