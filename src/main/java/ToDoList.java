
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;

/**
 *
 * @author alex
 */
public class ToDoList {

    // Variable para control del menú principal.
    private boolean mainMenu = true;

    // Variables de control de menus de la aplicación.
    private boolean dateTypeMainMenu;
    private int valorMainMenu;

    // Array con tareas
    private ArrayList<Task> taskList;

    // Variable para leer información del usuario por teclado.
    private Scanner scan;

    // Variables de control para añadir tarea.
    private boolean addTaskMenu;

    // Variables para eliminar tarea
    private boolean delTaskMenu, delDataType, valorSearch;
    private int valorDelTask;

    // Variables para editar tarea
    private boolean editDataType;
    private int valorEditTask;

    // Variables para finalizar tarea
    private boolean finiTaskMenu, finiDataType, valorFini;
    private int valorFiniTask;

    private FileManager fileManager = new FileManager();

    public static void main(String[] args) {
        ToDoList todo = new ToDoList();
        todo.initializer();
    }

    public void initializer() {

        if(fileManager.checkFileExist()){
            taskList = fileManager.loadTaskList();
        }else{
            taskList = new ArrayList<>();
        }
        

        scan = new Scanner(System.in);

        System.out.println("################################################");
        System.out.println("## BIENVENIDO A TU LISTA DE TAREAS PENDIENTES ##");
        System.out.println("################################################");
        System.out.println("");

        // Bucle para navegar por el menú principal
        do {

            System.out.println("##############");
            System.out.println("## OPCIONES ##");
            System.out.println("##############");

            System.out.println("1) Listar tareas pendientes.");
            System.out.println("2) Añadir tarea.");
            System.out.println("3) Editar tarea.");
            System.out.println("4) Eliminar tarea.");
            System.out.println("5) Finalizar tarea.");
            System.out.println("6) Salir");
            System.out.println("");

            dateTypeMainMenu = scan.hasNextInt();

            if (dateTypeMainMenu) {
                valorMainMenu = scan.nextInt();

                if (valorMainMenu == 1 | valorMainMenu > 2 && valorMainMenu < 6) {
                    if (!fileManager.checkFileExist()) {
                        System.out.println(
                                "INFO: No existe la base de datos de tereas, se creará la próxima vez que añada una.\n1");
                        continue;
                    }

                }

                switch (valorMainMenu) {
                    case 1:
                        listTasks();
                        break;
                    case 2:
                        addTask();
                        break;
                    case 3:
                        editTask();
                        break;
                    case 4:
                        delTask();
                        break;
                    case 5:
                        taskFinished();
                        break;
                    case 6:
                        mainMenu = false;
                        break;
                    default: {
                        System.out.println(
                                "El valor introducido es incorrecto, debe ser un número de una opción válida del menú OPCIONES.");
                        System.out.println("");
                    }
                }

            } else {
                System.out.println(
                        "El valor introducido es incorrecto, debe ser un número de una opción válida del menú OPCIONES.");
                System.out.println("");
                scan.nextLine();
            }

        } while (mainMenu);

        System.out.println("####################");
        System.out.println("## ¡HASTA PRONTO! ##");
        System.out.println("####################");
    }

    public void addTask() {

        System.out.println("########################");
        System.out.println("## AÑADIR NUEVA TAREA ##");
        System.out.println("########################");
        System.out.println("");

        addTaskMenu = true;

        do {
            scan = new Scanner(System.in);

            GregorianCalendar createdDate = new GregorianCalendar(Locale.ITALY);

            System.out.println("Por favor, indique la descripción de la tarea.");
            String textTask = scan.nextLine();

            if (textTask.equals(" ") | textTask.length() == 0) {
                System.out.println("Debe introducir texto para añadir una tarea.");
            } else {
                Task newTask = new Task(getLastTask(), textTask, createdDate);
                taskList.add(newTask);
                fileManager.saveTaskList(taskList);
                addTaskMenu = false;
                listTasks();
            }
        } while (addTaskMenu);

    }

    public int getLastTask() {
        int id = 0;

        if (!fileManager.checkFileExist()) {
            return id;
        }

        // Comprobamos cuál es el último id utilizado y añadimos el inmediatamente
        // superior.
        System.out.println("HEMOS PASADO");
        if (!taskList.isEmpty()) {
            for (Task t : taskList) {
                if (id <= t.getId()) {
                    id = t.getId() + 1;
                }
            }
        }
        return id;
    }

    public void editTask() {

        if (taskList.isEmpty()) {
            System.out.println("No dispone de ninguna tarea para editar, añada alguna.");
            System.out.println("");
            return;
        }

        boolean editTaskMenu = true;

        do {
            System.out.println("##################");
            System.out.println("## EDITAR TAREA ##");
            System.out.println("##################");
            System.out.println("");

            for (Task t : taskList) {
                System.out.print("ID " + t.getId());
                System.out.print(" - Task Text: " + t.getTextTask());
                System.out.print(" - Date Task: " + t.getCreatedDate().getTime());
                System.out.println("");
            }
            System.out.println("");
            System.out.println("Elija el ID de la tarea a editar.");
            scan = new Scanner(System.in);
            editDataType = scan.hasNextInt();

            // Comprobamos que el tipo de dato sea un entero.
            if (editDataType) {

                valorEditTask = scan.nextInt();

                // condicional para cancelar con -1 el proceso de borrado.
                if (valorEditTask >= 0) {

                    // Comprobamos que el ID a editar solicitado sea correcto y lo editamos.
                    valorSearch = false;
                    for (Task t : taskList) {
                        if (valorEditTask == t.getId()) {
                            valorSearch = true;
                            System.out.println("");
                            System.out.println("Por favor, indique la nueva descripción de la tarea.");
                            scan = new Scanner(System.in);
                            String newTextTask = scan.nextLine();
                            t.setTextTask(newTextTask);
                            GregorianCalendar editDate = new GregorianCalendar(Locale.ITALY);
                            t.setCreatedDate(editDate);
                            fileManager.saveTaskList(taskList);
                            System.out.println("Datos de la tarea editados satisfactoriamente.");
                            System.out.println("");
                            listTasks();
                            break;
                        }
                    }

                    // Si hemos encontrado el ID en el array, para detener el proceso de editado
                    // porque ya ha sucedido.
                    if (valorSearch) {
                        fileManager.saveTaskList(taskList);
                        return;
                    }

                    System.out.println("El valor introducido es incorrecto,no se encuentra en la lista de tareas");
                    System.out.println("");
                    scan.nextLine();
                    return;

                }

                editTaskMenu = false;

            } else {
                System.out.println("El valor introducido es incorrecto, debe ser un número de un ID válido.");
                System.out.println("");
                scan.nextLine();
            }

        } while (editTaskMenu);

    }

    public void listTasks() {

        if (!taskList.isEmpty()) {
            for (Task t : taskList) {
                System.out.println(t.toString());
            }
            System.out.println("");
            return;
        }

        System.out.println("No dispone de ninguna tarea pendiente, añada alguna.");
        System.out.println("");

    }

    public void delTask() {

        // Comprobamops si el array de tareas contiene alguna, si no, nos envía a añadir
        // una.
        if (!taskList.isEmpty()) {

            do {
                System.out.println("####################");
                System.out.println("## ELIMINAR TAREA ##");
                System.out.println("####################");
                System.out.println("");
                listTasks();
                System.out.println("");
                System.out.println("Por favor, indique el número de id de la tarea a eliminar, -1 para cancelar.");

                delTaskMenu = true;
                scan = new Scanner(System.in);
                delDataType = scan.hasNextInt();
                // Comprobamos que el tipo de dato sea un entero.
                if (delDataType) {

                    valorDelTask = scan.nextInt();

                    // condicional para cancelar con -1 el proceso de borrado.
                    if (valorDelTask >= 0) {

                        // Comprobamos que el ID a borrar solicitado sea correcto y lo borramos.
                        valorSearch = false;
                        for (Task t : taskList) {
                            if (valorDelTask == t.getId()) {
                                valorSearch = true;
                                taskList.remove(t);
                                fileManager.saveTaskList(taskList);
                                System.out.println("Tarea con ID " + valorDelTask + " eliminada con éxito.");
                                System.out.println("");
                                listTasks();
                                break;
                            }
                        }

                        // Si hemos encontrado el ID en el array, para detener el proceso de borrado
                        // porque ya ha sucedido.
                        if (valorSearch) {

                            delTaskMenu = false;

                        } else {
                            System.out
                                    .println("El valor introducido es incorrecto,no se encuentra en la lista de ID's");
                            System.out.println("");
                            scan.nextLine();
                        }

                    } else {
                        delTaskMenu = false;
                    }

                } else {
                    System.out.println("El valor introducido es incorrecto, debe ser un número de un ID válido.");
                    System.out.println("");
                    scan.nextLine();
                }

            } while (delTaskMenu);
        } else {

            // Cuando el array está vacío.
            System.out.println("No dispone de ninguna tarea pendiente, añada alguna.");
            System.out.println("");
        }

    }

    public void taskFinished() {

        // Comprobamops si el array de tareas contiene alguna, si no, nos envía a añadir
        // una.
        if (taskList.isEmpty()) {

            // Cuando el array está vacío.
            System.out.println("No dispone de ninguna tarea pendiente, añada alguna.");
            System.out.println("");
            return;
        }

        do {
            System.out.println("#####################");
            System.out.println("## FINALIZAR TAREA ##");
            System.out.println("#####################");
            System.out.println("");
            listTasks();
            System.out.println("");
            System.out.println("Por favor, indique el número de id de la tarea a finalizar, -1 para cancelar.");

            finiTaskMenu = true;
            scan = new Scanner(System.in);
            finiDataType = scan.hasNextInt();
            // Comprobamos que el tipo de dato sea un entero.
            if (finiDataType) {

                valorFiniTask = scan.nextInt();

                // condicional para cancelar con -1 el proceso de borrado.
                if (valorFiniTask >= 0) {

                    // Comprobamos que el ID a borrar solicitado sea correcto y lo borramos.
                    valorFini = false;
                    for (Task t : taskList) {
                        if (valorFiniTask == t.getId()) {
                            valorFini = true;
                            GregorianCalendar finishdDate = new GregorianCalendar(Locale.ITALY);
                            t.setFinishDate(finishdDate);
                            t.setFinish(true);
                            fileManager.saveTaskList(taskList);
                            System.out.println("Tarea con ID " + valorFiniTask
                                    + " marcada como finalizada con fecha " + finishdDate.getTime());
                            System.out.println("");
                            listTasks();
                            break;
                        }
                    }

                    // Si hemos encontrado el ID en el array, para detener el proceso de borrado
                    // porque ya ha sucedido.
                    if (valorFini) {

                        finiTaskMenu = false;

                    } else {
                        System.out
                                .println("El valor introducido es incorrecto,no se encuentra en la lista de ID's");
                        System.out.println("");
                        scan.nextLine();
                    }

                } else {
                    finiTaskMenu = false;
                }

            } else {
                System.out.println("El valor introducido es incorrecto, debe ser un número de un ID válido.");
                System.out.println("");
                scan.nextLine();
            }

        } while (finiTaskMenu);
    }

}
