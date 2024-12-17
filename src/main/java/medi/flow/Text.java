package medi.flow;

import java.text.*;
import java.util.*;

public class Text {

    public static List<String> splitStringToList(String string) {
        return new ArrayList<>(Arrays.asList(string.split("\\.")));
    }

    public static String listToString(List<String> lista) {
        StringBuilder sb = new StringBuilder();
        for (String item : lista) {
            sb.append(item).append(" ");
        }
        return sb.toString().trim();
    }

    public static Date dataJavaParaSql(String data, String hora) throws ParseException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return dateTimeFormat.parse(data + " " + hora);
    }

    public static String dataSqlParaJava(String dateString) throws ParseException {
        SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = sqlDateFormat.parse(dateString);
        return dateFormat.format(date);
    }

    public static String dataFormat(String data) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM");
        Date date = inputFormat.parse(data);
        return outputFormat.format(date);
    }

    public static String timeFormat(String time) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH");
        Date parsedTime = inputFormat.parse(time);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(parsedTime);
        String startHour = outputFormat.format(calendar.getTime());

        calendar.add(Calendar.HOUR_OF_DAY, 1);
        String endHour = outputFormat.format(calendar.getTime());

        return startHour + "h - " + endHour + "h";
    }

    // Método para formatar o nome do médico
    public static String nomeMedicoTransform(String nome) {
        String[] partes = nome.split(" ");
        if (partes.length < 2) {
            return "Dr. " + nome; // Se o nome só tiver 2 nomes, retorna o nome completo
        }
        String primeiroNome = partes[0];
        String ultimoNome = partes[partes.length - 1];
        return "Dr. " + primeiroNome + " " + ultimoNome;
    }
}
