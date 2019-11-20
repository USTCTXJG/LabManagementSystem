public class Client {
    private boolean isOpen;
    private String id;
    private String name;
    private String ip;

    public Client(boolean isOpen, String id, String name, String ip) {
        this.isOpen = isOpen;
        this.id = id;
        this.name = name;
        this.ip = ip;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
