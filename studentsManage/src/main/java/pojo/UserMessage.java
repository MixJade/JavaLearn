package pojo;

public class UserMessage {
    Integer idJade;
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
        return "UserMessage{" +
                "idJade=" + idJade +
                ", nameJade='" + nameJade + '\'' +
                ", passwordJade='" + passwordJade + '\'' +
                '}';
    }
}
