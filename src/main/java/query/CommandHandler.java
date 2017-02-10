package query;

/**
 * Created by ali on 2/10/17.
 */
public class CommandHandler {
    private String command;
    private String[] commandArgs;

    public CommandHandler(String command) {
        this.command = command;
    }

    public String getCommandType() {
        return this.commandArgs[0];
    }

    public void parseCommand() {
        this.commandArgs = this.command.split("\\s+");
    }

    public ClientReserveQuery createReserveQuery() {
        return new ClientReserveQuery(commandArgs);
    }

    public ClientSearchQuery createSearchQuery(){
        return new ClientSearchQuery(commandArgs);
    }

    public ClientFinalizeQuery createFinalizeQuery(){
        return new ClientFinalizeQuery(commandArgs);
    }

}
