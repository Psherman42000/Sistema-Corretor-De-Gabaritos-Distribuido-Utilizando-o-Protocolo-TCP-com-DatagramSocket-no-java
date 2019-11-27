
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Cliente extends javax.swing.JFrame {
    public Cliente() {
        initComponents();
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        selecionar = new javax.swing.JButton();
        nome = new javax.swing.JLabel();
        corrigir = new javax.swing.JButton();
        acertos = new javax.swing.JLabel();
        erros = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        selecionar.setText("Selecionar Arquivo");
        selecionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selecionarActionPerformed(evt);
            }
        });

        corrigir.setText("CORRIGIR");
        corrigir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corrigirActionPerformed(evt);
            }
        });

        acertos.setText("Acertos: 0");

        erros.setText("Erros:     0");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(acertos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(erros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(88, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(selecionar)
                        .addGap(120, 120, 120))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(81, 81, 81))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(corrigir)
                        .addGap(140, 140, 140))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(84, 84, 84)
                .addComponent(selecionar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nome, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(corrigir)
                .addGap(14, 14, 14)
                .addComponent(acertos)
                .addGap(18, 18, 18)
                .addComponent(erros)
                .addContainerGap(236, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selecionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selecionarActionPerformed
            //toda essa classe servira para selecionar a prova e armazenar ela na variavel local
            try{
            JFileChooser arquivo=new JFileChooser();
            JLabel tela=new JLabel("");
            tela.setBounds(200,200,200,200);
            arquivo.setDialogTitle("Selecionar Arquivo");
            arquivo.showOpenDialog(tela);
            arq=arquivo.getSelectedFile();
            nome.setText(arq.getName()+" Selecionado");
            }catch(Exception e){
                e.printStackTrace();
            }
    }//GEN-LAST:event_selecionarActionPerformed

    private void corrigirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corrigirActionPerformed
        //aqui iremos enviar o arquivo e receber a resposta do servidor
        try {
            ds=new DatagramSocket();
            FileReader fReader = new FileReader(arq);//preparando para passar o arquivo para string
            BufferedReader bReader = new BufferedReader(fReader);
            String prova="";
            while(bReader.ready()){//passando o arquivo para string, para ser enviada pelo datagrama
                    prova += bReader.readLine() + "\n";
            }
            msg=new byte[100000];
            msg=prova.getBytes();//passando a string para bytes
            dp2=new DatagramPacket(msg,prova.length(),InetAddress.getByName("localhost"),7777);//preaparando datagrama para envio
            ds.send(dp2);//enviando datagrama
            dp=new DatagramPacket(msg,msg.length);//preparando o datagrama para receber a resposta do servidor
            ds.receive(dp);//recebendo resposta
            gab=new String(dp.getData(),0,dp.getLength());//transformando os bytes em string
            int c=0;
            while(!gab.substring(c, c+1).equalsIgnoreCase("-")){//as 5 proximas linhas servirao para mostrar o resultado para o cliente
                c++;
            }
            acertos.setText("Acertos: "+gab.substring(0,c));
            erros.setText("Erros    : "+gab.substring(c+1,gab.length()));
            ds.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"ocorreu um erro, lembresse de selecionar um arquivo");
            e.printStackTrace();
        }
    }//GEN-LAST:event_corrigirActionPerformed

    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cliente().setVisible(true);
            }
        });
    }
    private DatagramPacket dp;
    private DatagramPacket dp2;
    private DatagramSocket ds;
    private String gab;
    private byte[] msg;
    private File arq;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel acertos;
    private javax.swing.JButton corrigir;
    private javax.swing.JLabel erros;
    private javax.swing.JLabel nome;
    private javax.swing.JButton selecionar;
    // End of variables declaration//GEN-END:variables
}
