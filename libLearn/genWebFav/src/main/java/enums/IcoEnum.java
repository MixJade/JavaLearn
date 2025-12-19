package enums;

public enum IcoEnum {
    vue("vue.svg"),
    elementPlus("element-plus.svg"),
    sass("sass.png"),
    Bootstrap("Bootstrap.png"),
    axios("axios.png"),
    Eslint("Eslint.png"),
    csdn("csdn.png"),
    juejin("juejin.png"),
    webPage("webPage.png");

    final String icoName;

    IcoEnum(String icoName) {
        this.icoName = icoName;
    }

    public String getIcoName() {
        return icoName;
    }
}
