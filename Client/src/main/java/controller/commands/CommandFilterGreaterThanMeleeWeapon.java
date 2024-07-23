package controller.commands;

import commonModule.collectionElements.MeleeWeapon;
import commonModule.commands.CommandType;
import commonModule.requests.NotAuthorizationException;
import controller.UserContainer;
import view.output.OutputUnit;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class CommandFilterGreaterThanMeleeWeapon extends AbstractCommand{
    public CommandFilterGreaterThanMeleeWeapon(OutputUnit output) throws SocketException {
        super(CommandType.FILTER_GREATER_THAN_MELEE_WEAPON, output);
    }

    @Override
    public Boolean execute(String[] command) throws SocketException, SocketTimeoutException, IOException {
        if (command.length != 2){
            this.output.writeln("The wrong number of elements was entered to execute the command" + this.getCommandType().toString() + "(" + (command.length-1) + " вместо 1)");
            return false;
        }else{
            this.builder.setCommandType(CommandType.FILTER_GREATER_THAN_MELEE_WEAPON);
            try{
                MeleeWeapon filterWeapon = MeleeWeapon.valueOf(command[1].toUpperCase());
                this.builder.setElement(filterWeapon);
                this.builder.setUserName(UserContainer.getUser().getUserName());
                try {
                    this.output.writeln(this.connector.getResponceFromServer(this.builder.build()).getElement());
                    return true;
                } catch (ClassNotFoundException e) {
                    this.output.writeln("The server response could not be recognized.");
                    return false;
                } catch (NotAuthorizationException e){
                    this.output.writeln(e.getMessage());
                    return false;
                }
            }
            catch (IllegalArgumentException e){
                String message = "";
                for (MeleeWeapon meleeWeapon: MeleeWeapon.values()){
                    message += meleeWeapon.toString() + " ";
                }
                this.output.writeln("There is no such value of meleeWeapon - " + command[1] + " ( meleeWeapon: " + message + ")");
                return false;
            }
        }
    }

    @Override
    public String toString(){
        String message = "";
        for (MeleeWeapon meleeWeapon: MeleeWeapon.values()){
            message += meleeWeapon.toString() + " ";
        }
        return this.commandType.getName() + " meleeWeapon : output elements whose MeleeWeapon field value is greater than the specified one ( meleeWeapon: " + message + ")";
    }
}
