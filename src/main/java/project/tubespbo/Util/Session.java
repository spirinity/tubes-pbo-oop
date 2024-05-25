package project.tubespbo.Util;
import project.tubespbo.Models.Entity;

public class Session {
    private static Session instance;
    private Entity currentUser;

    private Session() { }

    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    public void setCurrentUser(Entity user) {
        this.currentUser = user;
    }

    public Entity getCurrentUser() {
        return currentUser;
    }

    public void clearSession() {
        currentUser = null;
    }
}
