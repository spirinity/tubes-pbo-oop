package project.tubespbo.Models;

public abstract class Entity {

    protected String username;

    protected String password;
    protected String role;
    protected String namaLengkap;
    protected String alamat;

    public Entity(String username, String password, String role, String namaLengkap, String alamat) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.namaLengkap = namaLengkap;
        this.alamat = alamat;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public String getAlamat() {
        return alamat   ;
    }

    public abstract boolean authenticate();

    public abstract Integer getId(); // Add this method
}
