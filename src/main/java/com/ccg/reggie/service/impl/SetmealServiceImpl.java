package com.ccg.reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ccg.reggie.dto.SetmealDto;
import com.ccg.reggie.entity.Setmeal;
import com.ccg.reggie.entity.SetmealDish;
import com.ccg.reggie.mapper.SetmealMapper;
import com.ccg.reggie.service.SetmealDishService;
import com.ccg.reggie.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ccg.reggie.common.customException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDishService.saveBatch(setmealDishes);

    }

    @Override
    @Transactional
    public void removeWithDish(List<Long> ids) {
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Setmeal::getId, ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count = this.count(queryWrapper);
        if (count>0) {
            throw new customException("套餐增在售卖中");
        }
        this.removeByIds(ids);
        LambdaQueryWrapper<SetmealDish> lambdaQueryWrapper=new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(SetmealDish::getSetmealId, ids);
        setmealDishService.remove(lambdaQueryWrapper);

    }

    @Override
    public void stopSale(Long ids) {

        LambdaUpdateWrapper<Setmeal> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Setmeal::getId, ids);

        updateWrapper.set(Setmeal::getStatus, 0);
        this.update(updateWrapper);
    }

    @Override
    public void startSale(Long ids) {
        LambdaUpdateWrapper<Setmeal> updateWrapper=new LambdaUpdateWrapper<>();
        updateWrapper.eq(Setmeal::getId, ids);

        updateWrapper.set(Setmeal::getStatus, 1);

        this.update(updateWrapper);

    }
}
