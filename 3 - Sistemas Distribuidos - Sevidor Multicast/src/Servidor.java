
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.JOptionPane;


public class Servidor extends javax.swing.JFrame {


    public Servidor() {
        initComponents();
    }
    
    public static class Multi implements Runnable {//criando uma runable que recebe um datagrama como parametro
        private DatagramPacket dp;

        public Multi(DatagramPacket dp) {
            this.dp = dp;
        }

        public void run() {
                try{
                    news.setText(news.getText()+"uma nova conexao foi feita, recebendo arquivo e preparando resposta...\n");//mostrando mensagem de notificacoes sobre oq acontece no sistema
                    String resp=Corrigir(new String(dp.getData(),0,dp.getLength()));//chamando o metodo que corrige a prova passando a string recebida como parametro
                    byte[] msg=new byte[100000];//preparando mensagem para envio
                    msg=resp.getBytes();//passando a resposta para bytes
                    dp=new DatagramPacket(msg,resp.length(),dp.getAddress(),dp.getPort());//preparando datagrama para envio de resposta
                    ds.send(dp);//enviando resposta
                    news.setText(news.getText()+"prova corrigida e resposta enviada\n");//notificando status do sistema
                }catch(Exception e){
                    e.printStackTrace();
                }
        }
    }
                
 
    
    public static void ligar(){
        try{
            ds=new DatagramSocket(7777);//criando o servidor
            while(true){//fazendo ele ficar sempre executando
                buffer=new byte[100000];
                dp=new DatagramPacket(buffer,buffer.length);//preparando datagrama para receber novas requisicoes
                ds.receive(dp);//recebendo requisicao
                runa=new Multi(dp);//passando o datagrama para a runable
                new Thread(runa).start();//iniciando a thread que executara a runable acima
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null,"desculpe ocorreu um erro, tentando novamente...");
            e.printStackTrace();
        }
    }
    
    private static String Corrigir(String prova) throws FileNotFoundException, IOException {
        File f1=new File("C:\\Users\\lindo\\Music\\gabarito.txt");//pegando o arquivo que contem o gabarito
        FileReader fReader = new FileReader(f1);
        BufferedReader bReader = new BufferedReader(fReader);
        String gab="";
        while(bReader.ready()){//passando esse arquivo para string
                gab += bReader.readLine() + "\n";
        }
        int acertos=0,erros=0;
        for(int c=0;c<gab.length();c++){//corrigindo a prova obs:a prova e o gabarito devem ter o mesmo numero de questoes e alternativas
            if((gab.substring(c, c+1).equalsIgnoreCase("v"))||(gab.substring(c, c+1).equalsIgnoreCase("F"))){
                if(prova.substring(c,c+1).equalsIgnoreCase(gab.substring(c, c+1))){
                    acertos++;
                }else{
                    erros++;
                }
                
            }
        }
        String resp=acertos+"-"+erros;//preparando a resposta
        return resp;//retornando a resposta da prova
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        news = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        news.setColumns(20);
        news.setRows(5);
        news.setText("Aguardando Requisições...\n\n");
        jScrollPane1.setViewportView(news);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 506, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String args[]) throws FileNotFoundException {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Servidor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Servidor().setVisible(true);
            }
        });
        ligar();//chamando o metodo que inicia o servidor
    }
    public static Runnable runa;
    private static DatagramSocket ds;
    private static DatagramPacket dp;
    private static byte[] buffer;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private static javax.swing.JTextArea news;
    // End of variables declaration//GEN-END:variables
}

