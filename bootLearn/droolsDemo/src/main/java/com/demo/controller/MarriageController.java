package com.demo.controller;

import com.demo.model.domain.Introduce;
import com.demo.model.domain.MarriageRes;
import com.demo.model.entity.Man;
import com.demo.service.MarriageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/marriage")
@Tag(name = "唯一的控制器", description = "婚介所控制器接口")
public class MarriageController {
    private static final Logger log = LoggerFactory.getLogger(MarriageController.class);
    private final MarriageService marriageService;

    @Autowired
    public MarriageController(MarriageService marriageService) {
        this.marriageService = marriageService;
    }

    @GetMapping
    @Operation(summary = "婚介所介绍")
    @Parameters({
            @Parameter(name = "sex", description = "性别,1男0女", example = "1", required = true, in = ParameterIn.QUERY),
            @Parameter(name = "age", description = "年龄", example = "23", required = true, in = ParameterIn.QUERY),
            // 重点：这里的参数需要隐藏起来
            @Parameter(name = "introduce", hidden = true)
    })
    public ResponseEntity<String> introduce(Introduce introduce) {
        // 重点：这里的get参数会自动封装
        log.info("性别:{},年龄:{}", introduce.isSex() ? "男" : "女", introduce.getAge());
        // 重点：Spring自带的返回封装
        return new ResponseEntity<>(marriageService.introduce(introduce), HttpStatus.OK);
    }

    @PostMapping("/begin")
    @Operation(summary = "正式开始相亲")
    public ResponseEntity<MarriageRes> begin(@Valid @RequestBody Man man) {
        log.info(man.toString());


        // Spring自带的返回封装
        return new ResponseEntity<>(marriageService.marriage(man), HttpStatus.OK);
    }
}
