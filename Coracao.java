public class Coracao extends Item{
    public Coracao(String description, double weigth,String nome) {
            super(description, weigth,nome);
        }
        @Override
        public boolean ehUmCoracao() {
            return true;
        }
    }

