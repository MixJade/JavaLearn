package work.utils;

import work.ddlGen.DdlGenFactory;
import work.enums.DbType;
import work.enums.JType;
import work.model.dto.CodeCol;
import work.model.dto.CodeTab;
import work.model.entity.OriTabCol;
import work.model.entity.TableName;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.*;

/**
 * 代码生成器
 *
 * @since 2025-12-18 17:00:56
 */
public final class GenCodeUtil {
    /**
     * 将数据库字段转为Java字段
     */
    public static CodeTab convertCodeCol(DbType dbType, TableName tableName, List<OriTabCol> oriTabCols) {
        // 引入的包装类路径
        Set<JType> jTypeSet = new HashSet<>();
        // 将数据库字段转为Java字段
        List<CodeCol> codeColList = new ArrayList<>();
        for (OriTabCol oriTabCol : oriTabCols) {
            JType jType = DdlGenFactory.get(dbType).toJavaType(oriTabCol);
            jTypeSet.add(jType);
            codeColList.add(new CodeCol(
                    ConvertCase.snakeToSCamel(oriTabCol.columnName()),
                    oriTabCol.columnName(),
                    oriTabCol.comments(),
                    jType.tpNm,
                    oriTabCol.isPri()
            ));
        }
        List<String> pkgList = new ArrayList<>();
        for (JType jType : jTypeSet) {
            if (!"".equals(jType.pkg)) pkgList.add(jType.pkg);
        }
        // 组装表参数
        String lJNm = ConvertCase.snakeToLCamel(tableName.tableName());
        String sJNm = ConvertCase.snakeToSCamel(tableName.tableName());
        return new CodeTab(lJNm, sJNm, tableName, pkgList, codeColList);
    }

    /**
     * 生成代码文件
     */
    public static void genCodeFile(String outDir, String author, String pack, boolean isNormal, List<CodeTab> codeTabs) {
        // 1. 准备模板数据
        Map<String, Object> dataModel = new HashMap<>();
        dataModel.put("date", LocalDate.now().toString());
        dataModel.put("author", author);
        dataModel.put("pack", pack);
        String rootDir = "生成结果";
        String useTemp = isNormal ? "normal/" : "swagger/";
        for (CodeTab codeTab : codeTabs) {
            dataModel.put("tab", codeTab);
            String serviceName = codeTab.lJNm() + "Service";
            if (isNormal) serviceName = "I" + serviceName;
            dataModel.put("serviceName", serviceName);
            // 加载模板并渲染, 返回生成的代码,输出为文件
            System.out.println("=".repeat(15) + " 开始生成 " + codeTab.lJNm() + " " + "=".repeat(15));
            // 生成实体类
            if (isNormal) {
                StrToFile.toFilePath(
                        FtlUtil.fillTempStr(dataModel, useTemp + "entity.java.ftl"),
                        Path.of(rootDir, outDir, "model", "entity", String.format("%s.java", codeTab.lJNm()))
                );
            } else {
                StrToFile.toFilePath(
                        FtlUtil.fillTempStr(dataModel, useTemp + "entity.java.ftl"),
                        Path.of(rootDir, outDir, "entity", String.format("%s.java", codeTab.lJNm()))
                );
            }
            // 生成Ts实体类
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "tsEntity.ts.ftl"),
                    Path.of(rootDir, outDir, "tsEntity", String.format("%s.ts", codeTab.lJNm()))
            );
            // 生成controller
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "controller.java.ftl"),
                    Path.of(rootDir, outDir, "controller", String.format("%sController.java", codeTab.lJNm()))
            );
            // 生成Service
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "service.java.ftl"),
                    Path.of(rootDir, outDir, "service", String.format("%s.java", serviceName))
            );
            // 生成ServiceImpl
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "serviceImpl.java.ftl"),
                    Path.of(rootDir, outDir, "service", "impl", String.format("%sServiceImpl.java", codeTab.lJNm()))
            );
            // 生成Mapper
            if (isNormal) {
                StrToFile.toFilePath(
                        FtlUtil.fillTempStr(dataModel, useTemp + "mapper.java.ftl"),
                        Path.of(rootDir, outDir, "mapper", String.format("%sMapper.java", codeTab.lJNm()))
                );
            } else {
                StrToFile.toFilePath(
                        FtlUtil.fillTempStr(dataModel, useTemp + "mapper.java.ftl"),
                        Path.of(rootDir, outDir, "dao", String.format("%sMapper.java", codeTab.lJNm()))
                );
            }
            // 生成MapperXml
            StrToFile.toFilePath(
                    FtlUtil.fillTempStr(dataModel, useTemp + "mapper.xml.ftl"),
                    Path.of(rootDir, outDir, "mapperXml", String.format("%sMapper.xml", codeTab.lJNm()))
            );
        }
    }
}
