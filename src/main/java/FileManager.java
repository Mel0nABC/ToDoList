import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileManager {

    @SuppressWarnings("unchecked")
    public ArrayList<Task> loadTaskList() {

        ArrayList<Task> taskList = null;
        try {
            FileInputStream file = new FileInputStream("taskList.list");
            ObjectInputStream cargar = new ObjectInputStream(file);
            taskList = (ArrayList<Task>) cargar.readObject();
            cargar.close();
        } catch (FileNotFoundException ex) {
            System.out.println("INFO: No existe la base de datos de tereas, se creará la próxima vez que añada una..");
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ToDoList.class.getName()).log(Level.SEVERE, null, ex);
        }

        return taskList;

    }

    public void saveTaskList(ArrayList<Task> taskList) {
        try {
            FileOutputStream file = new FileOutputStream("taskList.list");
            ObjectOutputStream guardar = new ObjectOutputStream(file);
            guardar.writeObject(taskList);
            guardar.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean checkFileExist() {

        File file = new File("taskList.list");

        if (!file.exists()) {
            System.out.println("NO EXISTE");
            return false;
        }

        return true;

    }

}
