package pojo;

public class UserMessage {
    String nameJade;
    String passwordJade;

    public void setUser(String nameJade, String passwordJade) {
        this.nameJade = nameJade;
        this.passwordJade = passwordJade;
    }

    public String getNameJade() {
        return nameJade;
    }

    @Override
    public String toString() {
        return "nameJade='" + nameJade;
    }
}
