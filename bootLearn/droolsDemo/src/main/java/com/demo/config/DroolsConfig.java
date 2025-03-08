package com.demo.config;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.KieModule;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DroolsConfig {
    // 制定规则文件的路径
    private static final String RULES_INTRODUCE_DRL = "rules/introduce.drl";
    private static final String RULES_MARRIAGE_GIRL_DRL = "rules/marriage-girl.drl";
    private static final String RULES_MARRIAGE_MONEY_DRL = "rules/marriage-money.drl";
    // 获取kie对象
    private static final KieServices kieServices = KieServices.Factory.get();

    @Bean
    public KieContainer kieContainer() {
        KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_INTRODUCE_DRL));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_MARRIAGE_GIRL_DRL));
        kieFileSystem.write(ResourceFactory.newClassPathResource(RULES_MARRIAGE_MONEY_DRL));
        KieBuilder kb = kieServices.newKieBuilder(kieFileSystem);
        kb.buildAll();
        KieModule kieModule = kb.getKieModule();
        return kieServices.newKieContainer(kieModule.getReleaseId());
    }
}
