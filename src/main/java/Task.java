
import java.io.Serializable;
import java.util.GregorianCalendar;


public class Task implements Serializable {

    //Constante de id
    private final int ID;

    //variables de información de la tarea
    private String textTask;
    private boolean finish;
    private GregorianCalendar createdDate;
    private GregorianCalendar finishDate;

    public Task(int id, String textTask, GregorianCalendar createdDate) {
        this.ID = id;
        this.textTask = textTask;
        this.createdDate = createdDate;
    }

    public int getId() {
        return ID;
    }

    public String getTextTask() {
        return textTask;
    }

    public void setTextTask(String textTask) {
        this.textTask = textTask;
    }

    public GregorianCalendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(GregorianCalendar createdDate) {
        this.createdDate = createdDate;
    }

    public boolean getFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    public GregorianCalendar getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(GregorianCalendar finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public String toString() {
        String info = "";

        System.out.println("ID " + ID);
        System.out.println("    - Texto tarea: " + textTask);
        System.out.println("    - Fecha abierta: " + createdDate.getTime());
        if (finish == true) {
            System.out.println("    - Tarea finalizada: Finalizada");
            System.out.println("    - Fechaa cerrada: " + finishDate.getTime());
        } else {
            System.out.println("    - Tarea finalizada: Pendiente");
        }
        System.out.println("");

        return info;
    }

}
