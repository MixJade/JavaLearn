package com.demo.model.entity;

import com.demo.model.enums.JobEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Schema(description = "男方的个人信息")
public class Man {

    @Schema(description = "姓名", example = "张三")
    @NotBlank(message = "姓名不能为空")
    String name;

    @Schema(description = "年龄", example = "23")
    @Max(value = 100, message = "年龄最大为100岁")
    int age;

    @Schema(description = "性别,true男", example = "true")
    boolean sex = true;

    @Schema(description = "工作", example = "ROGUE")
    JobEnum job;

    @Schema(description = "年薪", example = "0")
    long yearlyPay;

    @Schema(description = "有房吗", example = "false")
    boolean hasHome = false;

    @Schema(description = "有车吗", example = "false")
    boolean hasCar = false;

    /***
     * 重写获取职业方法,让返回字符串
     */
    @SuppressWarnings("unused")
    public String getJob() {
        return job.name();
    }
}
