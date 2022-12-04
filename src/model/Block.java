package model;

public class Block {

    private int[] element;

    private String type;

    public Block(int[] element, String type){
        this.element = element;
        this.type = type;
    }

    public int[] getElement() {
        return element;
    }

    public String getType() {
        return type;
    }

    public void setElement(int[] element) {
        this.element = element;
    }

    public void setType(String type) {
        this.type = type;
    }
}