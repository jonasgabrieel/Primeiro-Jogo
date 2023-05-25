public class GoldenDuck extends Item {

    public GoldenDuck(String description, double weigth,String nome) {
        super(description, weigth,nome);
    }
    @Override
    public boolean ehUmPato() {
        return true;
    }
}
