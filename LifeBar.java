public class LifeBar {
    private String lifebar = "";

    public void setLifebar(int totalSteps) {
        for (int i = 0; i < totalSteps; i++) {
            this.lifebar += "♥";
        }
    }

    public String getLifebar() {
        return ("Life Bar: " + lifebar);
    }

    public void removeHeart(){
        this.lifebar = lifebar.substring(0,lifebar.length()-1);
    }

    public void addHeart(){
        this.lifebar += "♥";
    }
}
