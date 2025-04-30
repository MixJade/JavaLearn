import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collections;

/**
 * 正常的代码生成器
 *
 * @since 2023-8-22 22:02
 */
public class CodeGenerator {

    public static void main(String[] args) {
        FastAutoGenerator.create(MyConfig.url, MyConfig.username, MyConfig.password)
                // ============全局配置============
                .globalConfig(builder -> {
                    builder.author(MyConfig.author)
                            .disableOpenDir()
                            .outputDir(MyConfig.outputDir + "/src/main/java"); // 指定输出目录
                })
                // ===========软件包配置============
                .packageConfig(builder -> builder.parent(MyConfig.parentPack)
                        .entity(MyConfig.entityPack)
                        .mapper(MyConfig.mapperPack)
                        .pathInfo(Collections.singletonMap(OutputFile.xml, MyConfig.outputDir + MyConfig.mapperXmlDir)))
                // 使用Freemarker引擎模板
                .templateEngine(new FreemarkerTemplateEngine())
                // ============模板配置============
                .templateConfig(builder -> builder.disable(TemplateType.ENTITY)
                        .disable(TemplateType.ENTITY)
                        .entity("/templates/normal/entity.java")
                        .service("/templates/normal/service.java")
                        .serviceImpl("/templates/normal/serviceImpl.java")
                        .mapper("/templates/normal/mapper.java")
                        .xml("/templates/normal/mapper.xml")
                        .controller("/templates/normal/controller.java"))
                // ============策略配置============
                .strategyConfig(builder -> builder
                        // 需要生成的表名,注掉则为生成所有表
                        .addInclude(MyConfig.tableNames)
                        // 覆盖已经生成的entity文件
                        .entityBuilder().enableFileOverride()
                        // entity的字段自动填充策略,最好关掉
                        // .addTableFills(
                        //     new Column("create_time", FieldFill.INSERT),
                        //     new Column("update_time", FieldFill.INSERT_UPDATE)
                        // )
                        // mapper接口打上Mapper注解
                        .mapperBuilder().mapperAnnotation(Mapper.class)
                        .serviceBuilder()
                        .controllerBuilder().enableRestStyle())
                .execute();
    }
}
