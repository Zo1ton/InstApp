public class PersonObsList {
    private String login;
    private Long id;

    public PersonObsList(String login, Long id) {
        this.login = login;
        this.id = id;
    }

    public PersonObsList() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}