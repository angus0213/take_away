package com.ccg.reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ccg.reggie.entity.Category;
import org.apache.ibatis.annotations.Mapper;

public interface CategoryService extends IService<Category> {
    public void remove(Long id);


}
