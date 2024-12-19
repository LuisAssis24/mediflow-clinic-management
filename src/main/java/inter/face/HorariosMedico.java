/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package inter.face;

import javax.swing.*;
import java.awt.*;
import java.text.*;
import java.util.*;
import java.util.List;

import static inter.face.VistaSecretaria.dataConsultaHorario;
import static medi.flow.Main.getClinica;
import static medi.flow.Text.*;

/**
 *
 * @author draga
 */

// Classe que cria a interface gráfica para a visualização dos horários de um médico
public class HorariosMedico extends JFrame {
    private int idMedico;// Atributo que guarda o id do médico
    private String[] todosDias;// Atributo que guarda os dias da semana
    private List<String[]> horarios;// Atributo que guarda os horários do médico
    private List<String[]> horariosFiltered;// Atributo que guarda os horários filtrados

    // Construtor da classe
    public HorariosMedico(int id) throws ParseException {
        this.idMedico = id;// Atribui o id do médico
        this.horarios = getClinica().getHorarioMedico(id).getHorarios();// Atribui os horários do médico
        this.horariosFiltered = filtrarPorAno(horarios, isolarAno(dataConsultaHorario));// Filtra os horários por ano

        // Inicializa todosDias
        this.todosDias = diasAntesEDepois(dataConsultaHorario);

        initComponents();// Inicializa os componentes da interface
        carregarHorarios(); // Agora todosDias já estará inicializado
        updateHorarios();// Atualiza os horários
        carregarTexto();// Carrega o texto
    }

    // Método que carrega o texto
    public void carregarTexto() throws ParseException {
        String nomeMedicoFortmat = nomeMedicoTransform(getClinica().obterNomeMedicoPorId(idMedico));// Formata o nome do médico
        nomeMedico.setText(nomeMedicoFortmat);// Preenche o nome do médico

        String[] diasAD = diasAntesEDepois(dataConsultaHorario);// Calcula os dias antes e depois

        // Atribui os dias calculados corretamente
        dia1.setText(diasAD[0]);
        dia2.setText(diasAD[1]);
        dia3.setText(diasAD[2]);
        dia4.setText(diasAD[3]);
        dia5.setText(diasAD[4]);

        horariosDias.setText("Horarios dos dias: " + diasAD[0] + " - " + diasAD[4]);// Preenche o texto dos horários dos dias
    }

    // Método que formata o nome do médico
    public String[] diasAntesEDepois(String data) throws ParseException {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");// Formato de data
        Date date = inputFormat.parse(data);// Converte a data para o formato
        Calendar calendar = Calendar.getInstance();// Cria um calendário
        calendar.setTime(date);// Atribui a data ao calendário

        String[] result = new String[5];// Cria um array de strings

        // Calcula dois dias antes
        calendar.add(Calendar.DAY_OF_MONTH, -2);
        result[0] = dataFormat(inputFormat.format(calendar.getTime()));

        // Calcula um dia antes
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Incrementa +1 para corrigir
        result[1] = dataFormat(inputFormat.format(calendar.getTime()));

        // Reseta para a data original
        calendar.setTime(date);
        result[2] = dataFormat(inputFormat.format(calendar.getTime()));

        // Calcula um dia depois
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        result[3] = dataFormat(inputFormat.format(calendar.getTime()));

        // Calcula dois dias depois
        calendar.add(Calendar.DAY_OF_MONTH, 1); // Incrementa +1 para corrigir
        result[4] = dataFormat(inputFormat.format(calendar.getTime()));

        return result;// Retorna o array de strings
    }

    // Método que carrega os horários
    public void carregarHorarios() {
        if (todosDias == null) {// Verifica se todosDias está inicializado
            throw new IllegalStateException("todosDias não foi inicializado.");// Lança uma exceção
        }

        // Cria os modelos de lista
        DefaultListModel<String> modelDia1 = new DefaultListModel<>();
        DefaultListModel<String> modelDia2 = new DefaultListModel<>();
        DefaultListModel<String> modelDia3 = new DefaultListModel<>();
        DefaultListModel<String> modelDia4 = new DefaultListModel<>();
        DefaultListModel<String> modelDia5 = new DefaultListModel<>();

        // Percorre os horários filtrados
        for (String[] horario : horariosFiltered) {
            String dia = horario[0];
            String hora = horario[1];

            // Adiciona o horário ao modelo do dia correspondente
            if (dia.equals(todosDias[0])) modelDia1.addElement(hora);
            else if (dia.equals(todosDias[1])) modelDia2.addElement(hora);
            else if (dia.equals(todosDias[2])) modelDia3.addElement(hora);
            else if (dia.equals(todosDias[3])) modelDia4.addElement(hora);
            else if (dia.equals(todosDias[4])) modelDia5.addElement(hora);
        }

        // Define os modelos nas JList correspondentes
        horariosDia1.setModel(modelDia1);
        horariosDia2.setModel(modelDia2);
        horariosDia3.setModel(modelDia3);
        horariosDia4.setModel(modelDia4);
        horariosDia5.setModel(modelDia5);
    }

    // Método que atualiza os horários
    public void updateHorarios() {
        horariosDia1.setCellRenderer(new CustomCellRenderer(horariosFiltered, todosDias[0]));
        horariosDia2.setCellRenderer(new CustomCellRenderer(horariosFiltered, todosDias[1]));
        horariosDia3.setCellRenderer(new CustomCellRenderer(horariosFiltered, todosDias[2]));
        horariosDia4.setCellRenderer(new CustomCellRenderer(horariosFiltered, todosDias[3]));
        horariosDia5.setCellRenderer(new CustomCellRenderer(horariosFiltered, todosDias[4]));
    }

    // Classe que define o renderizador de células personalizado
    private static class CustomCellRenderer extends DefaultListCellRenderer {
        private final List<String[]> horariosOcupados;// Atributo que guarda os horários ocupados
        private final String diaAtual;// Atributo que guarda o dia atual

        // Construtor da classe
        public CustomCellRenderer(List<String[]> horariosOcupados, String diaAtual) {
            this.horariosOcupados = horariosOcupados;// Atribui os horários ocupados
            this.diaAtual = diaAtual;// Atribui o dia atual
        }

        // Método que renderiza a célula
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);// Cria um novo JLabel
            String horario = (String) value;// Converte o valor para string

            // Define o fundo padrão para horários disponíveis
            label.setBackground(isSelected ? Color.BLUE : Color.WHITE);
            label.setForeground(isSelected ? Color.WHITE : Color.BLACK);

            // Verifica se o horário está ocupado
            for (String[] ocupado : horariosOcupados) {
                if (ocupado[0].equals(diaAtual) && ocupado[1].equals(horario)) {
                    label.setBackground(Color.RED); // Fundo vermelho para horários ocupados
                    label.setForeground(Color.WHITE); // Texto branco
                    break;
                }
            }
            return label;
        }
    }

    // Método que filtra os horários por ano
    public List<String[]> filtrarPorAno(List<String[]> horarios, int ano) throws ParseException {
        List<String[]> horariosFiltrados = new ArrayList<>();// Cria uma nova lista de horários
        for (String[] horario : horarios) {// Percorre os horários
            String data = horario[0];// Atribui a data

            // Adiciona o ano atual se faltar
            if (data.matches("\\d{2}/\\d{2}")) {
                data += "/" + ano;
            }

            // Verifica se o ano é igual ao ano passado como argumento
            if (isolarAno(data) == ano) {
                String[] horarioFormat = new String[]{dataFormat(data), timeFormat(horario[1])};
                horariosFiltrados.add(horarioFormat);
            }
        }
        return horariosFiltrados;
    }

    // Método que isola o ano
    public int isolarAno(String data) throws ParseException {
        SimpleDateFormat dateFormat;// Cria um novo formato de data

        // Verifica se a string contém o ano
        if (data.matches("\\d{2}/\\d{2}/\\d{4}")) { // Formato dd/MM/yyyy
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        } else if (data.matches("\\d{2}/\\d{2}")) { // Formato dd/MM
            data += "/" + Calendar.getInstance().get(Calendar.YEAR); // Adiciona o ano atual
            dateFormat = new SimpleDateFormat("dd/MM/yyyy");// Formato dd/MM/yyyy
        } else {// Formato inválido
            throw new ParseException("Formato de data inválido: " + data, 0);// Lança uma exceção
        }

        // Converte a string para data
        Date date = dateFormat.parse(data);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);// Retorna o ano
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dia2 = new javax.swing.JLabel();
        dia3 = new javax.swing.JLabel();
        dia5 = new javax.swing.JLabel();
        horariosDia2 = new javax.swing.JList<>();
        horariosDia3 = new javax.swing.JList<>();
        horariosDia5 = new javax.swing.JList<>();
        dia4 = new javax.swing.JLabel();
        horariosDia4 = new javax.swing.JList<>();
        dia1 = new javax.swing.JLabel();
        horariosDia1 = new javax.swing.JList<>();
        horariosDias = new javax.swing.JLabel();
        nomeMedico = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(450, 300));
        setPreferredSize(new java.awt.Dimension(450, 350));
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);
        getContentPane().setLayout(new java.awt.GridBagLayout());

        dia2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        dia2.setText(" 27/12");
        dia2.setMaximumSize(new java.awt.Dimension(70, 20));
        dia2.setMinimumSize(new java.awt.Dimension(70, 20));
        dia2.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        getContentPane().add(dia2, gridBagConstraints);

        dia3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        dia3.setText(" 28/12");
        dia3.setMaximumSize(new java.awt.Dimension(70, 20));
        dia3.setMinimumSize(new java.awt.Dimension(70, 20));
        dia3.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        getContentPane().add(dia3, gridBagConstraints);

        dia5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        dia5.setText(" 31/12");
        dia5.setMaximumSize(new java.awt.Dimension(70, 20));
        dia5.setMinimumSize(new java.awt.Dimension(70, 20));
        dia5.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        getContentPane().add(dia5, gridBagConstraints);

        horariosDia2.setBackground(new java.awt.Color(242, 242, 242));
        horariosDia2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 149, 218), 2, true));
        horariosDia2.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        horariosDia2.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "9h-10h", "10h-11h", "11h-12h", "12h-13h", "14h-15h", "15h-16h", "16h-17h", "17h-18h" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        horariosDia2.setMaximumSize(new java.awt.Dimension(70, 200));
        horariosDia2.setMinimumSize(new java.awt.Dimension(70, 200));
        horariosDia2.setPreferredSize(new java.awt.Dimension(70, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        getContentPane().add(horariosDia2, gridBagConstraints);

        horariosDia3.setBackground(new java.awt.Color(242, 242, 242));
        horariosDia3.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 149, 218), 2, true));
        horariosDia3.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        horariosDia3.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "9h-10h", "10h-11h", "11h-12h", "12h-13h", "14h-15h", "15h-16h", "16h-17h", "17h-18h" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        horariosDia3.setMaximumSize(new java.awt.Dimension(70, 200));
        horariosDia3.setMinimumSize(new java.awt.Dimension(70, 200));
        horariosDia3.setPreferredSize(new java.awt.Dimension(70, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        getContentPane().add(horariosDia3, gridBagConstraints);

        horariosDia5.setBackground(new java.awt.Color(242, 242, 242));
        horariosDia5.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 149, 218), 2, true));
        horariosDia5.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        horariosDia5.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "9h-10h", "10h-11h", "11h-12h", "12h-13h", "14h-15h", "15h-16h", "16h-17h", "17h-18h" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        horariosDia5.setMaximumSize(new java.awt.Dimension(70, 200));
        horariosDia5.setMinimumSize(new java.awt.Dimension(70, 200));
        horariosDia5.setPreferredSize(new java.awt.Dimension(70, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        getContentPane().add(horariosDia5, gridBagConstraints);

        dia4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        dia4.setText(" 30/12");
        dia4.setMaximumSize(new java.awt.Dimension(70, 20));
        dia4.setMinimumSize(new java.awt.Dimension(70, 20));
        dia4.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        getContentPane().add(dia4, gridBagConstraints);

        horariosDia4.setBackground(new java.awt.Color(242, 242, 242));
        horariosDia4.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 149, 218), 2, true));
        horariosDia4.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        horariosDia4.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "9h-10h", "10h-11h", "11h-12h", "12h-13h", "14h-15h", "15h-16h", "16h-17h", "17h-18h" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        horariosDia4.setMaximumSize(new java.awt.Dimension(70, 200));
        horariosDia4.setMinimumSize(new java.awt.Dimension(70, 200));
        horariosDia4.setPreferredSize(new java.awt.Dimension(70, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        getContentPane().add(horariosDia4, gridBagConstraints);

        dia1.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        dia1.setText(" 26/12");
        dia1.setMaximumSize(new java.awt.Dimension(70, 20));
        dia1.setMinimumSize(new java.awt.Dimension(70, 20));
        dia1.setPreferredSize(new java.awt.Dimension(70, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(dia1, gridBagConstraints);

        horariosDia1.setBackground(new java.awt.Color(242, 242, 242));
        horariosDia1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 149, 218), 2, true));
        horariosDia1.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        horariosDia1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "9h-10h", "10h-11h", "11h-12h", "12h-13h", "14h-15h", "15h-16h", "16h-17h", "17h-18h" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        horariosDia1.setMaximumSize(new java.awt.Dimension(70, 200));
        horariosDia1.setMinimumSize(new java.awt.Dimension(70, 200));
        horariosDia1.setPreferredSize(new java.awt.Dimension(70, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        getContentPane().add(horariosDia1, gridBagConstraints);

        horariosDias.setFont(new java.awt.Font("Yu Gothic UI", 1, 12)); // NOI18N
        horariosDias.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        horariosDias.setText("Horarios dos dias: dd/mm - dd/mm");
        horariosDias.setMaximumSize(new java.awt.Dimension(250, 30));
        horariosDias.setMinimumSize(new java.awt.Dimension(250, 30));
        horariosDias.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        getContentPane().add(horariosDias, gridBagConstraints);

        nomeMedico.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        nomeMedico.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nomeMedico.setText("Dr. xxxxxxxxxyyyyyyyyyyyyyyyyy");
        nomeMedico.setMaximumSize(new java.awt.Dimension(250, 30));
        nomeMedico.setMinimumSize(new java.awt.Dimension(250, 30));
        nomeMedico.setPreferredSize(new java.awt.Dimension(250, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        getContentPane().add(nomeMedico, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */



    public void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(HorariosMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(HorariosMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(HorariosMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(HorariosMedico.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {// Cria e exibe o formulário
            try {// Tenta
                new HorariosMedico(idMedico).setVisible(true);// Cria e exibe o formulário
            } catch (ParseException e) {// Captura uma exceção de análise
                throw new RuntimeException(e);// Lança uma exceção de tempo de execução
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel dia1;
    private javax.swing.JLabel dia2;
    private javax.swing.JLabel dia3;
    private javax.swing.JLabel dia4;
    private javax.swing.JLabel dia5;
    private javax.swing.JList<String> horariosDia1;
    private javax.swing.JList<String> horariosDia2;
    private javax.swing.JList<String> horariosDia3;
    private javax.swing.JList<String> horariosDia4;
    private javax.swing.JList<String> horariosDia5;
    private javax.swing.JLabel horariosDias;
    private javax.swing.JLabel nomeMedico;
    // End of variables declaration//GEN-END:variables
}
