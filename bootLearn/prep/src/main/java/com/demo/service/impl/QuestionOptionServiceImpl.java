package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.QuestionOptionMapper;
import com.demo.model.entity.QuestionOption;
import com.demo.service.IQuestionOptionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 题目选项表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Service
public class QuestionOptionServiceImpl extends ServiceImpl<QuestionOptionMapper, QuestionOption> implements IQuestionOptionService {

}
