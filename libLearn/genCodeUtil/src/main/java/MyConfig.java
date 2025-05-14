interface MyConfig {
    /**
     * 需要生成的表名
     */
    String[] tableNames = new String[]{"memo_sql", "prj_item"};

    /**
     * 数据库链接、账号、密码
     */
    String url = "jdbc:mysql://localhost:3306/memo?useSSL=true",
            username = "root",
            password = "MC@:(==ni2024";

    /**
     * 输出目录的路径，到项目名称为止
     */
    String outputDir = "../../bootLearn/memo";

    /**
     * 软件包、entity文件的包路径
     */
    String parentPack = "com.demo",
            entityPack = "model.entity",
            mapperPack = "mapper";

    /**
     * MapperXml文件的路径
     */
    String mapperXmlDir = "/src/main/resources/com/demo/mapper";

    /**
     * 文件作者
     */
    String author = "MixJade";
}
