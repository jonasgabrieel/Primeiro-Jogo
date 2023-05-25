public class Item {
    private final String description;
    private final double weigth;
    private final String nome;

    public Item(String description, double weigth, String nome) {
        this.description = description;
        this.weigth = weigth;
        this.nome = nome;
    }
    public boolean ehUmPato(){
        return false;
    }
    public boolean ehUmCoracao(){
        return false;
    }

    public String getDescription() {
        return description;
    }

    public String getNome() {
        return nome;
    }

    public double getWeigth() {
        return weigth;
    }
}
