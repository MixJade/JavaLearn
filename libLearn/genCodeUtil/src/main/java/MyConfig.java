interface MyConfig {
    /**
     * 需要生成的表名(设为空即为生成所有表)
     */
    String[] tableNames = new String[]{"source_category", "source_image", "source_image"};

    // 数据库链接、账号、密码
    // 注意数据库名称
    String url = "jdbc:mysql://localhost:3306/prep?useSSL=true",
            username = "root",
            password = "MC@:(==ni2024";
    // 输出目录的路径，到项目名称为止
    String outputDir = "../../bootLearn/prep";
    // 软件包、entity文件的包路径
    String parentPack = "com.demo",
            entityPack = "model.entity",
            mapperPack = "mapper";
    // MapperXml文件的路径
    String mapperXmlDir = "/src/main/resources/com/demo/mapper";
    // 文件作者
    String author = "MixJade";
}
