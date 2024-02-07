package quacky;


/**
 * The main class for the Quacky application.
 * Initializes the application's user interface, storage system, and task list,
 * and orchestrates the main application flow.
 */
public class Quacky {
    private static TaskList tasks = new TaskList();

    private Storage storage;
    private UI ui;

    private static final String DEFAULT_PATH = "./data/data.txt";


    /**
     * Constructs a new Quacky application instance.
     * Attempts to load tasks from the specified file path upon instantiation.
     *
     * @param filePath The path to the file from which tasks should be loaded.
     */
    public Quacky(String filePath) {
        try {
            ui = new UI();
            storage = new Storage(filePath);
            // Use the filePath parameter instead of hardcoding
            tasks = storage.load();
        } catch (Storage.InvalidStorageFilePathException e) {
            System.out.println(e.getMessage());
        } catch (Storage.StorageOperationException e) {
            System.out.println(e.getMessage());
        }
    }

    public Quacky() {
        this(DEFAULT_PATH);
    }
    /**
     * Starts the main application loop, processing user commands until termination.
     * Handles command parsing, execution, and any resulting exceptions.
     */
    public void run() {
        ui.showGreeting();
        while (ui.isRunning) {
            try {
                String command = ui.readCommand();
                Parser.parseCommand(command, tasks, ui);
            } catch (QuackyException e) {
                ui.showErrorMessage(e);
            }
        }
        try {
            storage.save(tasks);
        } catch (Storage.StorageOperationException e) {
            ui.showErrorMessage(e);
        }
    }


    protected String getResponse(String command) {
       try {
           return Parser.parseCommand(command, tasks, ui);
       } catch (QuackyException e){
           return ui.showErrorMessage(e);
        }
    }

    protected String endQuacky() {
        try {
            storage.save(tasks);
        } catch (Storage.StorageOperationException e) {
            ui.showErrorMessage(e);
        }
        return ui.showFarewell();
    }
    public static void main(String[] args) {
        new Quacky("./data/data.txt").run();
    }
}
