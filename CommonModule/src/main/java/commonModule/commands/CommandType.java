package commonModule.commands;

/**
 * Enum, элементы которого - типы команд, которые реализует данная программа
 */
public enum CommandType {
    ADD("add"), UPDATE_ID("update"), HELP("help"), INFO("info"), MIN_BY_WEAPON_TYPE("min_by_weapon_type"), FILTER_GREATER_THAN_MELEE_WEAPON("filter_greater_than_melee_weapon"), PRINT_DESCENDING("print_descending"), SHOW("show"), HISTORY("history"), CLEAR("clear"), REMOVE_BY_ID("remove_by_id"), REMOVE_AT_INDEX("remove_at"), SORT("sort"), EXECUTE_SCRIPT("execute_script"), GET_FORM("get_form"), EXIT("exit"), LOGIN("login"), AUTHORIZATION("authorization");
    private String name;
    CommandType(String name){
        this.name = name;
    }

    /**
     * Метод, который по строковому представлению команды возвращает соответствующую константу.
     * @param name {@link String} - строковое представление команды.
     * @return соответствующая строковому представлению константа.
     */
    public static CommandType getCommand(String name){
        for (CommandType value: CommandType.values()){
            if (name.equals(value.toString())){
                return value;
            }
        }
        return null;
    }

    public String getName(){
        return this.name;
    }

    @Override
    public String toString(){
        return name;
    }
}
