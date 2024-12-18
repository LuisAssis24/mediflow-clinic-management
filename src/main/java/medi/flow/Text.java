package medi.flow;

import java.text.*;
import java.util.*;

// Classe que contém métodos para manipulação de texto
public class Text {

    // Método para dividir uma string em uma lista de strings
    public static List<String> splitStringToList(String string) {// Recebe uma string
        return new ArrayList<>(Arrays.asList(string.split("\\.")));// Divide a string em uma lista de strings
    }

    // Método para juntar uma lista de strings numa string
    public static String listToString(List<String> lista) {
        StringBuilder sb = new StringBuilder();// Cria um StringBuilder
        for (String item : lista) {// Itera sobre a lista
            sb.append(item).append(" ");// Adiciona o item ao StringBuilder
        }
        return sb.toString().trim();
    }

    // Método para formatar a data
    public static Date dataJavaParaSql(String data, String hora) throws ParseException {
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");// Cria um formato de data e hora
        return dateTimeFormat.parse(data + " " + hora);// Retorna a data e hora formatada
    }

    // Método para formatar a data
    public static String dataSqlParaJava(String dateString) throws ParseException {
        SimpleDateFormat sqlDateFormat = new SimpleDateFormat("yyyy-MM-dd");// Cria um formato de data
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");// Cria um formato de data
        Date date = sqlDateFormat.parse(dateString);// Converte a data
        return dateFormat.format(date);
    }

    // Método para formatar a hora
    public static String dataFormat(String data) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");// Cria um formato de data
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM");// Cria um formato de data
        Date date = inputFormat.parse(data);// Converte a data
        return outputFormat.format(date);
    }

    // Método para formatar a hora
    public static String timeFormat(String time) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("HH:mm:ss");// Cria um formato de hora
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH");// Cria um formato de hora
        Date parsedTime = inputFormat.parse(time);// Converte a hora

        Calendar calendar = Calendar.getInstance();// Cria um calendário
        calendar.setTime(parsedTime);// Define a hora no calendário
        String startHour = outputFormat.format(calendar.getTime());// Formata a hora

        calendar.add(Calendar.HOUR_OF_DAY, 1);// Adiciona uma hora
        String endHour = outputFormat.format(calendar.getTime());// Formata a hora

        return startHour + "h - " + endHour + "h";// Retorna a hora formatada
    }

    // Método para formatar o nome do médico
    public static String nomeMedicoTransform(String nome) {
        String[] partes = nome.split(" ");// Divide o nome em partes
        if (partes.length < 2) {// Verifica se o nome tem menos de 2 partes
            return "Dr. " + nome; // Se o nome só tiver 2 nomes, retorna o nome completo
        }
        String primeiroNome = partes[0];// Obtém o primeiro nome
        String ultimoNome = partes[partes.length - 1];// Obtém o último nome
        return "Dr. " + primeiroNome + " " + ultimoNome;// Retorna o nome formatado
    }
}
