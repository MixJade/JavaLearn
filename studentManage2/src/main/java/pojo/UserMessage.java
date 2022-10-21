package pojo;

public class UserMessage {
    String nameJade;
    String passwordJade;
    boolean remember;

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

    public boolean isRemember() {
        return remember;
    }

    public void setRemember(boolean remember) {
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
