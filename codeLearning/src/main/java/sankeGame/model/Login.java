package sankeGame.model;
public class Login {
   boolean loginSuccess = false;
   String nameJade;
   String passwordJade;

   public boolean isLoginSuccess() {
      return loginSuccess;
   }

   public void setLoginSuccess(boolean loginSuccess) {
      this.loginSuccess = loginSuccess;
   }

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
}