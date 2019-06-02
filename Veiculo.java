import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.util.TreeMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.Serializable;

public abstract class Veiculo implements Serializable
{
    // variáveis de instância 
    private double vel_media;
    private double precobase;
    private double consumo;
    private String matricula;
    private int nif;
    private Map<LocalDate,List<Aluguer>> historial;
    private int classificacao;
    private Point2D.Double localizacao;
    private double autonomia;
    private double autonomiaAtual;
    private int uso;
    private String marca;
    private String tipo;
    double total_faturado;
     /**
     * Construtores
     */
    public Veiculo()
    {
        this.vel_media=0.0;
        this.precobase=0.0;
        this.consumo=0.0;
        this.matricula="";
        this.nif=0;
        this.historial=new TreeMap<>();
        this.classificacao=0;
        this.localizacao=new Point2D.Double();
        this.autonomia=0.0;
        this.autonomiaAtual=0.0;
        this.uso=0;
        this.marca="";
        this.tipo="";
        this.total_faturado=0.0;

        
    }

    public Veiculo(double vel_med,double precobase,double consumo,String matricula,int nif,
    Map<LocalDate,List<Aluguer>> historial,int classificao,Point2D.Double localizacao,double autonomia,double autonimiaAtual,int uso,String marca,String tipo,double total_faturado)
    {
         this.vel_media=vel_med;
         this.precobase=precobase;
         this.consumo=consumo;
         this.matricula=matricula;
         this.nif=nif;
         this.historial=historial.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
         this.classificacao=classificacao;
         this.localizacao=new Point2D.Double(localizacao.getX(),localizacao.getY());
         this.autonomia=autonomia;
         this.autonomiaAtual=autonomiaAtual;
         this.uso=uso;
         this.marca=marca;
         this.tipo=tipo;
         this.total_faturado=total_faturado;
    }
    
    public Veiculo (Veiculo v)
    {
         this.vel_media=v.getVel_Med();
         this.precobase=v.getPrecoBase();
         this.consumo=v.getConsumo();
         this.matricula=v.getMatricula();
         this.nif=v.getNif();
         this.historial=v.getHistorial();
         this.classificacao=v.getClassificacao();
         this.localizacao=v.getLocalizacao();
         this.autonomia=v.getAutonomia();
         this.autonomiaAtual=v.getAutonomiaAtual();
         this.uso=v.getUso();
         this.marca=v.getMarca();
         this.tipo=v.getTipo();
         this.total_faturado=getTotalFaturado();
    }
      /**
     * Getters
     */
    public int getUso()
    {
     return this.uso;   
    }
    public String getMarca()
    {
      return this.marca;
    }
    public double getTotalFaturado()
    {
      return this.total_faturado;   
    }
    public String getTipo()
    {
      return this.tipo;
    }
    public double getVel_Med()
    {
      return this.vel_media;   
    }
    public double getPrecoBase()
    {
      return this.precobase;   
    }
    public double getConsumo()
    {
      return this.consumo;   
    }
    public String getMatricula()
    {
      return this.matricula;   
    }
    public int getNif()
    {
      return this.nif;   
    }
    public int getClassificacao()
    {
      return this.classificacao;   
    }
    public double getAutonomia()
    {
      return this.autonomia;   
    }
    public double getAutonomiaAtual()
    {
      return this.autonomiaAtual;   
    }
    public Map<LocalDate,List<Aluguer>> getHistorial()
    {
     return this.historial.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));  
    }
     /**
     * Setters
     */
    public void setTotal_Faturado (double tf)
    {
      this.total_faturado=tf;   
    }
    public void setVel_Media(double vel_med)
    {
      this.vel_media=vel_med;   
    }
    public void setPrecoBase(double precobase)
    {
      this.precobase=precobase;   
    }
    public void setConsumo (double consumo)
    {
      this.consumo=consumo;   
    }
    public void setMatricula (String matricula)
    {
      this.matricula=matricula;
    }
    public void setNif (int nif)
    {
      this.nif=nif;   
    }
    public void setClassificacao (int classificacao)
    {
      this.classificacao=classificacao;   
    }
    public void setLocalizacao (double x,double y)
    {
      this.localizacao=new Point2D.Double(x,y);   
    }
    public void setMarca (String marca)
    {
      this.marca=marca;   
    }
    public void setTipo (String tipo)
    {
      this.tipo=tipo;   
    }
    public void setAutonomia (double autonomia)
    {
      this.autonomia=autonomia;   
    }
    public void setAutonomiaAtual (double autonomiaAtual)
    {
      this.autonomiaAtual=autonomiaAtual;   
    }
    public Point2D.Double getLocalizacao()
    {
      return new Point2D.Double(this.localizacao.getX(),this.localizacao.getY());
    }
    
    public abstract Veiculo clone();
    
    public void addTotalFaturado(double faturado)
    {
     this.total_faturado+=faturado;
    
    }
    public void addToHistorial(Aluguer alu){
   
       if (!this.historial.containsKey(alu.getData())){
        List<Aluguer> l=new ArrayList<>();
        l.add(alu);
        this.historial.put(alu.getData(),l);
        }else
        this.historial.get(alu.getData()).add(alu);
    }

    public boolean TemAutonomia(double dist){
    if (this.getAutonomiaAtual()>dist*this.consumo) return true;
    return false;
    
    }
    
    public double totalFaturadoPeriodo(LocalDate data){
        return this.historial.entrySet().stream().filter(e->e.getKey().equals(data)).map(e->e.getValue()).flatMap(List::stream).mapToDouble(Aluguer::getCusto).sum();
    }
    
    public String toString(){
        return  "Tipo : " + this.tipo + "\n" +
                "Matricula : " + this.matricula + "\n" +
                "Velocidade média por km : " + this.vel_media + "\n" +
                "Consumo : " + this.consumo    + 
                "\n"+ "Nif : " + this.nif    + "\n"+
                "Classificação : " + this.classificacao    + "\n"+
                "Preço base : " + this.precobase   + "\n" +
                "Autonomia : " + this.autonomiaAtual    + "\n"+
                "Total faturado"+this.total_faturado+
                "Localizacao"+this.localizacao.getX()+","+this.localizacao.getY();
    }
    
    public boolean equals(Object o)
    {
      if(o==this) return true;
        if(o==null || o.getClass() != this.getClass()) return false;
        Veiculo v = (Veiculo) o;
        return v.getMatricula().equals(this.matricula) &&
              v.getNif()==this.nif;    
    }
}
