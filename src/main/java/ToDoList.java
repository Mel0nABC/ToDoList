
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
/**
 *
 * @author alex
 */
public class ToDoList {

    //Variable para control del menú principal.
    private static boolean mainMenu = true;

    //Variables de control de menus de la aplicación.
    private static boolean dateTypeMainMenu;
    private static int valorMainMenu;

    //Array con tareas
    private static ArrayList<Task> taskList;

    //Variable para leer información del usuario por teclado.
    private static Scanner scan;

    //Variables de control para añadir tarea.
    private static boolean addTaskMenu, addTaskDataType;

    //Variables para eliminar tarea
    private static boolean delTaskMenu, delDataType, valorSearch;
    private static int valorDelTask;

    //Variables para editar tarea
    private static boolean editDataType;
    private static int valorEditTask;

    //Variables para finalizar tarea
    private static boolean finiTaskMenu, finiDataType, valorFini;
    private static int valorFiniTask;

    public static void main(String[] args) {

        taskList = new ArrayList<>();

        loadTaskList();

        scan = new Scanner(System.in);

        System.out.println("################################################");
        System.out.println("## BIENVENIDO A TO LISTA DE TAREAS PENDIENTES ##");
        System.out.println("################################################");
        System.out.println("");

        //Bucle para navegar por el menú principal
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
                        System.out.println("El valor introducido es incorrecto, debe ser un número de una opción válida del menú OPCIONES.");
                        System.out.println("");
                    }
                }

            } else {
                System.out.println("El valor introducido es incorrecto, debe ser un número de una opción válida del menú OPCIONES.");
                System.out.println("");
                scan.nextLine();
            }

        } while (mainMenu);

        System.out.println("####################");
        System.out.println("## ¡HASTA PRONTO! ##");
        System.out.println("####################");

    }

    public static void addTask() {

        System.out.println("########################");
        System.out.println("## AÑADIR NUEVA TAREA ##");
        System.out.println("########################");
        System.out.println("");

        addTaskMenu = true;

        do {
            scan = new Scanner(System.in);
            int id = 0;

            //Comprobamos cuál es el último id utilizado y añadimos el inmediatamente superior.
            if (!taskList.isEmpty()) {
                for (Task t : taskList) {
                    if (id <= t.getId()) {
                        id = t.getId() + 1;
                    }
                }
            }

            GregorianCalendar createdDate = new GregorianCalendar(Locale.ITALY);

            System.out.println("Por favor, indique la descripción de la tarea.");
            String textTask = scan.nextLine();

            if (textTask.equals(" ") | textTask.length() == 0) {
                System.out.println("Debe introducir texto para añadir una tarea.");
            } else {
                Task newTask = new Task(id, textTask, createdDate);
                taskList.add(newTask);
                saveTaskList();
                addTaskMenu = false;
                listTasks();
            }
        } while (addTaskMenu);

    }

    public static void editTask() {

        boolean editTaskMenu = true;

        do {
            System.out.println("##################");
            System.out.println("## EDITAR TAREA ##");
            System.out.println("##################");
            System.out.println("");

            if (!taskList.isEmpty()) {

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

                //Comprobamos que el tipo de dato sea un entero.
                if (editDataType) {

                    valorEditTask = scan.nextInt();

                    //condicional para cancelar con -1 el proceso de borrado.
                    if (valorEditTask >= 0) {

                        //Comprobamos que el ID a borrar solicitado sea correcto y lo borramos.
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
                                saveTaskList();
                                System.out.println("Datos de la tarea editados satisfactoriamente.");
                                System.out.println("");
                                listTasks();
                                break;
                            }
                        }

                        //Si hemos encontrado el ID en el array, para detener el proceso de borrado porque ya ha sucedido.
                        if (valorSearch) {

                            editTaskMenu = false;
                            saveTaskList();

                        } else {
                            System.out.println("El valor introducido es incorrecto,no se encuentra en la lista de ID's");
                            System.out.println("");
                            scan.nextLine();
                        }

                    } else {
                        editTaskMenu = false;
                    }

                } else {
                    System.out.println("El valor introducido es incorrecto, debe ser un número de un ID válido.");
                    System.out.println("");
                    scan.nextLine();
                }

            } else {
                System.out.println("No dispone de ninguna tarea para editar, añada alguna.");
                System.out.println("");
                editTaskMenu = false;
            }

        } while (editTaskMenu);

    }

    public static void listTasks() {

        if (!taskList.isEmpty()) {

            for (Task t : taskList) {
                System.out.println(t.toString());
            }
            System.out.println("");

        } else {
            System.out.println("No dispone de ninguna tarea pendiente, añada alguna.");
            System.out.println("");

        }

    }

    public static void delTask() {

        //Comprobamops si el array de tareas contiene alguna, si no, nos envía a añadir una.
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
                //Comprobamos que el tipo de dato sea un entero.
                if (delDataType) {

                    valorDelTask = scan.nextInt();

                    //condicional para cancelar con -1 el proceso de borrado.
                    if (valorDelTask >= 0) {

                        //Comprobamos que el ID a borrar solicitado sea correcto y lo borramos.
                        valorSearch = false;
                        for (Task t : taskList) {
                            if (valorDelTask == t.getId()) {
                                valorSearch = true;
                                taskList.remove(t);
                                saveTaskList();
                                System.out.println("Tarea con ID " + valorDelTask + " eliminada con éxito.");
                                System.out.println("");
                                listTasks();
                                break;
                            }
                        }

                        //Si hemos encontrado el ID en el array, para detener el proceso de borrado porque ya ha sucedido.
                        if (valorSearch) {

                            delTaskMenu = false;

                        } else {
                            System.out.println("El valor introducido es incorrecto,no se encuentra en la lista de ID's");
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

            //Cuando el array está vacío.
            System.out.println("No dispone de ninguna tarea pendiente, añada alguna.");
            System.out.println("");
            addTask();
        }

    }

    public static void taskFinished() {
        //Comprobamops si el array de tareas contiene alguna, si no, nos envía a añadir una.
        if (!taskList.isEmpty()) {

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
                //Comprobamos que el tipo de dato sea un entero.
                if (finiDataType) {

                    valorFiniTask = scan.nextInt();

                    //condicional para cancelar con -1 el proceso de borrado.
                    if (valorFiniTask >= 0) {

                        //Comprobamos que el ID a borrar solicitado sea correcto y lo borramos.
                        valorFini = false;
                        for (Task t : taskList) {
                            if (valorFiniTask == t.getId()) {
                                valorFini = true;
                                GregorianCalendar finishdDate = new GregorianCalendar(Locale.ITALY);
                                t.setFinishDate(finishdDate);
                                t.setFinish(true);
                                saveTaskList();
                                System.out.println("Tarea con ID " + valorFiniTask + " marcada como finalizada con fecha " + finishdDate.getTime());
                                System.out.println("");
                                listTasks();
                                break;
                            }
                        }

                        //Si hemos encontrado el ID en el array, para detener el proceso de borrado porque ya ha sucedido.
                        if (valorFini) {

                            finiTaskMenu = false;

                        } else {
                            System.out.println("El valor introducido es incorrecto,no se encuentra en la lista de ID's");
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
        } else {

            //Cuando el array está vacío.
            System.out.println("No dispone de ninguna tarea pendiente, añada alguna.");
            System.out.println("");
            addTask();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadTaskList() {

        try {
            FileInputStream file = new FileInputStream("taskList.list");
            ObjectInputStream cargar = new ObjectInputStream(file);
            taskList = (ArrayList<Task>) cargar.readObject();

        } catch (FileNotFoundException ex) {
            System.out.println("INFO: No existe archivo de tareas actualmente guardado.");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ToDoList.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public static void saveTaskList() {
        try {
            FileOutputStream file = new FileOutputStream("taskList.list");
            ObjectOutputStream guardar = new ObjectOutputStream(file);
            guardar.writeObject(taskList);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
