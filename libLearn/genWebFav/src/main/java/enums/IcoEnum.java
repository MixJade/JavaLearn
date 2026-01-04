package enums;

public enum IcoEnum {
    axios("axios.png"),
    bootstrap("Bootstrap.png"),
    container("container.png"),
    caitc("caitc.jpg"),
    crc("crc.png"),
    csdn("csdn.png"),
    dhc("dhc.png"),
    elementPlus("elementPlus.svg"),
    eslint("Eslint.png"),
    excel("excel.png"),
    excelPlus("excelPlus.png"),
    fotic("fotic.png"),
    gitlab("gitLab.png"),
    juejin("juejin.png"),
    kdocs("kdocs.png"),
    liucheng("liucheng.png"),
    mq("mq.png"),
    nacos("nacos.png"),
    nexus("nexus.png"),
    oracle("oracle.png"),
    pdf("pdf.png"),
    sass("sass.png"),
    swagger("swagger.png"),
    vue("vue.svg"),
    web("web.png"),
    word("word.png"),
    wuliHub("WuliHub.png"),
    xxlJob("xxlJob.png"),
    zentao("zentao.png");

    final String icoName;

    IcoEnum(String icoName) {
        this.icoName = icoName;
    }

    public String getIcoName() {
        return icoName;
    }
}
