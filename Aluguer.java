import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.Serializable;

public class Aluguer implements Serializable
{
    // variáveis de instância 
    private Point2D.Double destino;
    private Point2D.Double origem;
    private double custo;
    private double duracao;
    private double tempoclivei;
    private Veiculo veiculo;
    private LocalDate data; 
    private Cliente c;
    private Proprietario p;
    private boolean aceite;
     /**
     * Construtores
     */
    public Aluguer()
    {
      this.destino=new Point2D.Double();
      this.origem = new Point2D.Double();
      this.custo=0.0;
      this.duracao=0.0;
      this.tempoclivei=0.0;
      this.veiculo=null;
      this.data = LocalDate.MIN;
      this.c=new Cliente();
      this.p=new Proprietario();
      this.aceite=false;
    }
    
    public Aluguer(Point2D.Double destino,Point2D.Double origem,double custo,double duracao,double tempoclivei,Veiculo veiculo,LocalDate data,Cliente c,Proprietario p,boolean aceite)
    {
      this.destino=new Point2D.Double(destino.getX(),destino.getY());
      this.origem=new Point2D.Double(origem.getX(),origem.getY());
      this.custo=custo;
      this.duracao=duracao;
      this.tempoclivei=tempoclivei;
      this.veiculo=veiculo;
      this.data = LocalDate.of(data.getYear(), data.getMonth(), data.getDayOfMonth());
      this.c=c.clone();
      this.p=p.clone();
      this.aceite=aceite;
    }
    
    public Aluguer (Aluguer al)
    {
      this.destino=al.getDestino();
      this.origem=al.getOrigem();
      this.custo=al.getCusto();
      this.duracao=al.getDuracao();
      this.tempoclivei=al.getTempo();
      this.veiculo=al.getVeiculo();
      this.data=al.getData();
      this.c=getC();
      this.p=getP();
      this.aceite=getAceite();
    }
     /**
     * Getters
     */

    public Proprietario getP()
    { 
      return this.p;
    }
    public Point2D.Double getDestino()
    {
      return new Point2D.Double(this.destino.getX(),this.destino.getY());   
    }
    public Point2D.Double getOrigem()
    {
      return new Point2D.Double(this.origem.getX(),this.origem.getY());   
    }
    public double getCusto()
    {
      return this.custo;   
    }
    public LocalDate getData()
    {
      return LocalDate.of(this.data.getYear(), this.data.getMonth(), this.data.getDayOfMonth());
    }
    public double getDuracao()
    {
      return this.duracao;   
    }
    public double getTempo()
    {
      return this.tempoclivei;   
    }
    public boolean getAceite()
    {
      return this.aceite;   
    }
    public Cliente getC(){

     return this.c;   
    
    }
    /**
     * Setters
    */
    public void setVeiculo(Veiculo v)
    {
      this.veiculo=v.clone();
    }
    public void setAceite(boolean t){
     this.aceite=t;   
    }
    public void addVeiculo(Veiculo v)
    {
      this.veiculo=v;
    }
    public void setP(Proprietario p)
    {
      this.p=p.clone();
    }
    public Veiculo getVeiculo() 
    {
      return this.veiculo;
    }
    public void setCusto(double custo) 
    {
      this.custo=custo;
    }
    public void setDuracao(double duracao) 
    {
      this.duracao=duracao;
    }
    public void setTempoclivei(double tempo) 
    {
      this.tempoclivei=tempo;
    }
    public void setOrigem(Point2D.Double ori)
    {
      this.origem=new Point2D.Double(ori.getX(),ori.getY());   
    }
    public void setDestino(Point2D.Double dest)
    {
      this.destino=new Point2D.Double(dest.getX(),dest.getY());  
    }
    public void setC(Cliente c)
    {
      this.c=c.clone();
    }
    
    public void addTotalFaturado(double faturado)
    {
      this.veiculo.addTotalFaturado(faturado) ; 
    }

    public void atualizaPosVeiculo(Point2D.Double localizacao)
    {
      this.veiculo.setLocalizacao(localizacao.getX(),localizacao.getY());
    } 

    //public void atualizaPosClie(Point2D.Double localizacao)
   // {
     // this.cliente.setLocalizacao(localizacao);
    //} 

    public void atualizaData()
    {
      LocalDate date = LocalDate.now();
      this.data=date;
    }
    
    public double DistanciaAluguer (Point2D.Double dest)
    {
      return this.getVeiculo().getLocalizacao().distance(dest);   
    }

    public Aluguer clone ()
    {
      return new Aluguer(this);   
    }
    
    public String dataToString (LocalDate date)
    {
      DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      String strDate=dtf.format(date);
      return strDate;
    }
    public boolean equals(Object o)
    {
      if(o==this) return true;
        if(o==null || o.getClass() != this.getClass()) return false;
        Aluguer a = (Aluguer) o;
        return a.getP().equals(this.p) && a.getDestino().equals(this.destino)&&
        a.getOrigem().equals(this.origem)&&a.getVeiculo().equals(this.veiculo)&&
        a.getCusto()==this.custo&&a.getTempo()==this.tempoclivei&&a.getCusto()==this.custo
        &&a.getData().equals(this.data);
    }

    public String toString ()
    {
        StringBuilder sb = new StringBuilder ();
        sb.append("Destino final X: ").append(this.destino.getX()).append("\nY: ").append(this.destino.getY())
            .append("\nCusto: ").append(this.custo)
            .append("\nDuração: ").append(this.duracao)
            .append("\nTempo que o cliente demora até ao veiculo: ").append(this.tempoclivei)
            .append("\nVeiculo: ").append(this.veiculo.toString())
            .append("\nData: ").append(this.dataToString(this.data));
        return sb.toString();
    }
 }

