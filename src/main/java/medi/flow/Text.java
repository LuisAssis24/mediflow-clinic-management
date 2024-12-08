package medi.flow;

import java.text.*;
import java.util.Date;

public class Text {
    public static void quebraPontos(javax.swing.JList<String> jList, String texto) {
        // Divide o texto usando "." como separador
        String[] partes = texto.split("\\.");

        // Cria um novo modelo para o JList
        javax.swing.DefaultListModel<String> modelo = new javax.swing.DefaultListModel<>();

        // Adiciona cada parte ao modelo, ignorando itens vazios
        for (String parte : partes) {
            if (!parte.trim().isEmpty()) {
                modelo.addElement(parte.trim());
            }
        }

        // Define o modelo processado na JList
        jList.setModel(modelo);
    }

    public static Date dataJavaParaSql(String data, String hora) throws ParseException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateTimeFormat.parse(data + " " + hora);
    }
}
