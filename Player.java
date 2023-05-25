public class Player {
    private String nickname;
    //Espaço de inventário
    private double inventory;

    public Player(String nickname, double inventory) {
        this.nickname = nickname;
        this.inventory = inventory;
    }

    public void pickUpItem(Room sala, Item item) {
        if ((inventory -= item.getWeigth()) != 0){
            inventory -= item.getWeigth();
            sala.removeItem(item);
        }else{
            System.out.println("Invetário lotado");
        }
    }

    public void dropItem(Room sala, Item item) {
        if ((inventory -= item.getWeigth()) != 0){
            this.inventory -= item.getWeigth();
            sala.addItem(item);
        }else{
            System.out.println("Item dropado");
            inventory += item.getWeigth();
        }
    }

}
