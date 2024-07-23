package commonModule.collectionElements;

import commonModule.collectionElements.interfaces.Valuable;

public enum MeleeWeapon implements Valuable {
    CHAIN_SWORD(1), CHAIN_AXE(2), POWER_BLADE(3), POWER_FIST(4);
    private int value;
    MeleeWeapon(int value){
        this.value = value;
    }

    @Override
    public int getValue(){
        return this.value;
    };
}