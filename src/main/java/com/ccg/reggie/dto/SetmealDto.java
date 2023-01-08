package com.ccg.reggie.dto;


import com.ccg.reggie.entity.Setmeal;
import com.ccg.reggie.entity.SetmealDish;
import lombok.Data;
import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
