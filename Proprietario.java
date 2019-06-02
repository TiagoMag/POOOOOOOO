import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import java.util.Iterator;

public class Proprietario extends Atores implements Serializable
{
    // variáveis de instância 
    private double classificacao;
    private double total;
    private List<Aluguer> historial;
    private List<Aluguer> pedidos;
    /**
     * Construtores
     */
    public Proprietario()
    {
       super();
       this.classificacao=0;
       this.historial=new ArrayList<Aluguer>();
       this.pedidos=new ArrayList<Aluguer>();
    }
    
    public Proprietario(String email,String nome,String password,
    String morada,String datanascimento,int nif,double classificacao,double total,List<Aluguer>historial,List<Aluguer> pedidos)
    {
       super(email,nome,password,morada,datanascimento,nif);
       this.classificacao=classificacao;
       this.total=total;
       this.historial=new ArrayList<>();
       for(Aluguer al: historial)
       {
        this.historial.add(al.clone());
       }
       this.pedidos=new ArrayList<>();
       for(Aluguer al: pedidos)
       {
        this.pedidos.add(al.clone());
       }
    }
    
    public Proprietario(Proprietario p)
    {
       super(p.getEmail(),p.getNome(),p.getPassword(),p.getMorada(),p.getDataN(),p.getNif());
       this.classificacao=p.getClassificacao();
       this.historial=p.getHistorial();
       this.total=p.getTotal();
       this.pedidos=p.getPedidos();
    }
      /**
     * Getters
     */
    public double getClassificacao ()
    {
      return this.classificacao;    
    }
    public ArrayList<Aluguer> getPedidos ()
    {
      ArrayList<Aluguer> al = new ArrayList<>();
        for(Aluguer alu: this.pedidos)
        {
            al.add(alu.clone());
        }
        return al;  
    }
    public ArrayList<Aluguer> getHistorial(){
        ArrayList<Aluguer> al = new ArrayList<>();
        for(Aluguer alu: this.historial)
        {
            al.add(alu.clone());
        }
        return al;
    }
    public double getTotal()
    {
      return this.total;
    }
     /**
     * Setters
     */
    public void setClassificacao (double classificacao)
    {
      this.classificacao=classificacao;   
    }
    public void setTotal(double total)
    {

     this.total=total;
    }
    public void setHistorial(ArrayList<Aluguer> al){
        ArrayList<Aluguer> alu = new ArrayList<>();
        for(Aluguer l: al)
        {
          alu.add(l.clone());
        }
        this.historial = alu;
    }
   
    public void setPedidos(Aluguer al){
       this.pedidos.add(al.clone());
    }
    public void addToPedidos(Aluguer alu){
     
     
     
     this.pedidos.add(alu.clone());
     
     }
    public void addToHistorial(Aluguer alu){
   
      this.historial.add(alu);
   }
    
    public Proprietario clone()
    {
      return new Proprietario(this);   
    }
   
    public String toString(){
      StringBuilder sb = new StringBuilder();
      sb.append(super.toString()).append("\nClassificação:").append(this.classificacao);
      return sb.toString();
     }
      
}
