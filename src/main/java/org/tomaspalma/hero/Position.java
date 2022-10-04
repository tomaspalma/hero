package org.tomaspalma.hero;

public class Position {
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public boolean isLeft(Position positionToCompare) {
        if(x > positionToCompare.getX()) return false;
        return true;
    }

    public boolean isAbove(Position positionToCompare) {
        if(y > positionToCompare.getY()) return false;
        return true;
    }

    @Override
    public boolean equals(Object objectToCompareWith) {
        if(this == objectToCompareWith) return true;
        //Não podemos chamar métodos a objetos que sejam nulos
        if(objectToCompareWith == null) return false;

        if(getClass() != objectToCompareWith.getClass()) return false;

        return this.x == ((Position)objectToCompareWith).getX() && this.y == ((Position)objectToCompareWith).getY();
    }

    private int x, y;
}
