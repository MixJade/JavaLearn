package pojo;

public class UserMessage {
    String nameJade;
    String passwordJade;
    String remember;

    public String getNameJade() {
        return nameJade;
    }

    public void setNameJade(String nameJade) {
        this.nameJade = nameJade;
    }

    public String getPasswordJade() {
        return passwordJade;
    }

    public void setPasswordJade(String passwordJade) {
        this.passwordJade = passwordJade;
    }

    public String getRemember() {
        return remember;
    }

    public void setRemember(String remember) {
        this.remember = remember;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
                "nameJade='" + nameJade + '\'' +
                ", passwordJade='" + passwordJade + '\'' +
                ", remember='" + remember + '\'' +
                '}';
    }
}
