package com.demo.service;

import com.demo.model.domain.Introduce;
import com.demo.model.domain.MarriageRes;
import com.demo.model.entity.Man;
import org.drools.core.base.RuleNameStartsWithAgendaFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarriageService {
    private static final Logger log = LoggerFactory.getLogger(MarriageService.class);
    private final KieContainer kieContainer;

    @Autowired
    public MarriageService(KieContainer kieContainer) {
        this.kieContainer = kieContainer;
    }

    public String introduce(Introduce introduce) {
        // 开启会话
        KieSession kieSession = kieContainer.newKieSession();
        // 设置日志的全局变量
        kieSession.setGlobal("log", log);
        // 设置传参对象
        kieSession.insert(introduce);
        // 触发规则,这里使用过滤器,只执行"introduce_rule"开头的规则
        kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("introduce_rule"));
        // 中止会话
        kieSession.dispose();
        return introduce.getResult();
    }

    public MarriageRes marriage(Man man) {
        // 开启会话
        KieSession kieSession = kieContainer.newKieSession();
        // 设置日志的全局变量
        kieSession.setGlobal("log", log);
        // 设置返回值的对象
        MarriageRes marriageRes = new MarriageRes();
        kieSession.setGlobal("marriageRes", marriageRes);
        // 设置传参对象
        kieSession.insert(man);
        // 触发规则,这里匹配所有规则
        kieSession.fireAllRules();

        // 注意: 这里拿到结果了,再匹配一遍规则
        log.info("相亲结果(未追加):{}",marriageRes);
        kieSession.insert(marriageRes);
        kieSession.fireAllRules(new RuleNameStartsWithAgendaFilter("marriage-money-rule"));

        // 中止会话
        kieSession.dispose();
        return marriageRes;
    }
}
