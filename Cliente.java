import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Cliente extends Atores implements Serializable
{
    // variáveis de instância 
    private Point2D.Double localizacao;
    private double kmpercorridos;
    private double classificacao;
    private double total;
    private Map<LocalDate,List<Aluguer>> historial;
    /**
     * Construtores
     */
    public Cliente()
    {
       super();
       this.localizacao=new Point2D.Double();
       this.kmpercorridos=0.0;
       this.historial=new HashMap<>();
       this.total=0.0;
       this.classificacao=0.0;
    }
    
    public Cliente(String email,String nome,String password,
    String morada,String datanascimento,int nif,Point2D.Double localizacao,Map<LocalDate,List<Aluguer>>historial,
    double kmpercorridos,double classificacao,double total)
    {
       super(email,nome,password,morada,datanascimento,nif);
       this.localizacao=new Point2D.Double(localizacao.getX(),localizacao.getY());
       this.historial=historial.values().stream().flatMap(List::stream).map(Aluguer::clone).collect(Collectors.groupingBy(Aluguer::getData));
       this.kmpercorridos=kmpercorridos;
       this.classificacao=classificacao;
       this.total=total;
    }
    
    public Cliente(Cliente c)
    {
       super(c.getEmail(),c.getNome(),c.getPassword(),c.getMorada(),c.getDataN(),c.getNif());
       this.localizacao=c.getLocalizacao();
       this.historial=c.getHistorial();
       this.kmpercorridos=c.getKmPercorridos();
       this.classificacao=c.getClassificacao();
       this.total=c.getTotal();
    }
    /**
     * Getters
     */
    public Point2D.Double getLocalizacao ()
    {
      return new Point2D.Double(this.localizacao.getX(),this.localizacao.getY());    
    }
    public double getTotal()
    {
      return this.total;
    }
    public double getClassificacao()
    {
      return this.classificacao;
    }
    public Map<LocalDate,List<Aluguer>> getHistorial()
    {
       return  this.historial.values().stream().flatMap(List::stream).map(Aluguer::clone).collect(Collectors.groupingBy(Aluguer::getData));   
    }
    public double getKmPercorridos()
    {
        return this.kmpercorridos;   
    }
      /**
     * Setters
     */
    public void setLocalizacao (Point2D.Double localizacao)
    {
      this.localizacao=new Point2D.Double(localizacao.getX(),localizacao.getY());    
    }
    public void setTotal(double total)
    {
      this.total=total;
    }
    public void setClassificacao(double classificacao)
    {
      this.classificacao=classificacao;
    }
    public void setKmPercorrido (double kmpercorridos)
    {
        this.kmpercorridos=kmpercorridos;
    }
    
    //public void setHistorial(ArrayList<Aluguer> al){
      //  ArrayList<Aluguer> alu = new ArrayList<>();
      //  for(Aluguer l: al){
      //    alu.add(l.clone());
      //  }
      //  this.historial = alu;
    //}
    public int contaAlugueres ()
    {
      int res=0;
      List<Aluguer> aux=new ArrayList<>();
      aux=this.getHistorial().values().stream().flatMap(List::stream).collect(Collectors.toList());
      res=aux.size();
      return res;
    }
    
    
    public void addToHistorial(Aluguer alu){
        if(this.historial.containsKey(alu.getData()))
          this.historial.get(alu.getData()).add(alu);
        else 
        {
         this.historial.put(alu.getData(),new ArrayList<>());
         this.historial.get(alu.getData()).add(alu);
        }
    }

    public Cliente clone ()
    {
        return new Cliente (this);
    }
    
    public String toString(){
     StringBuilder sb = new StringBuilder();
     sb.append(super.toString() ).append("\n Posição X: ").append(this.localizacao.getX())
     .append("\n Posição Y: ").append(this.localizacao.getY()).append("\nKmsPercorridos: ")
     .append(this.kmpercorridos).append("\nClassificação: ").append(this.classificacao);
     return sb.toString();
    }
    
}
