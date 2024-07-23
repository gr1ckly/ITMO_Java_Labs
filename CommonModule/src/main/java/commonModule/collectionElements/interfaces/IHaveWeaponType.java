package commonModule.collectionElements.interfaces;

import commonModule.collectionElements.Weapon;

/**
 * Интерфейс, который показывает что объект может позвращать {@link Weapon}.
 */
public interface IHaveWeaponType {
    public Weapon getWeaponType();
}
