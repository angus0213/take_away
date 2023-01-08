package com.ccg.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccg.reggie.dto.SetmealDto;
import com.ccg.reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    public void removeWithDish(List<Long> ids);
    public void stopSale(Long ids);
    public void startSale(Long ids);
}
