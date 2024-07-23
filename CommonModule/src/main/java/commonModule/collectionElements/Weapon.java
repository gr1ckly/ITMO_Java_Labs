package commonModule.collectionElements;

import commonModule.collectionElements.interfaces.Valuable;

public enum Weapon implements Valuable {
    HEAVY_BOLTGUN(1), BOLT_RIFLE(2), PLASMA_GUN(3), FLAMER(4), GRAV_GUN(5);
    private int value;
    Weapon(int value){
        this.value = value;
    }

    @Override
    public int getValue(){
        return this.value;
    }
}