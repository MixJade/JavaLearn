package com.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.mapper.SubjectMapper;
import com.demo.model.entity.Subject;
import com.demo.service.ISubjectService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 科目表 服务实现类
 * </p>
 *
 * @author MixJade
 * @since 2025-06-06
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements ISubjectService {

}
