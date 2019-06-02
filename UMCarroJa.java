import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.awt.geom.Point2D;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.*;
import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class UMCarroJa implements Serializable
{
    
  
    private Map<Integer, List<Veiculo>> veiculos ;
    private Map<String, Cliente> clientes;
    private Map<String, Proprietario> proprietarios;
    private Cliente clogado;
    private Proprietario plogado;
    private Aluguer alu;
    /**
     * Construtores
     */
    public UMCarroJa (){
      this.veiculos=new HashMap<>();
      this.clientes=new HashMap<>();
      this.proprietarios=new HashMap<>();
      this.clogado=new Cliente();
      this.plogado=new Proprietario();
      this.alu=new Aluguer();
    }
    
    public UMCarroJa(Map<Integer,List<Veiculo>> veiculos,Map<String,Cliente> clientes ,Map<String,Proprietario>proprietarios,Cliente c,Proprietario p)
    {
      this.veiculos = veiculos.entrySet().stream().collect(Collectors.toMap(e->e.getKey(),e->e.getValue()));
      this.clientes = clientes.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
      this.proprietarios =proprietarios.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));
      this.clogado=c.clone();
      this.plogado=p.clone();
      this.alu=alu.clone();
    }

    public UMCarroJa(UMCarroJa c){

      this.veiculos =c.getVeiculos();
      this.clientes = c.getClientes();
      this.proprietarios =c.getProprietarios();
      this.clogado=c.getClogado();
      this.plogado=c.getplogado();
      this.alu=c.getAlu();

    }
     /**
     * Getters
     */
    public Cliente getClogado()
    {
      return this.clogado.clone();    
    }
    public Proprietario getplogado()
    {
      return this.plogado.clone();    
    }
    public Aluguer getAlu()
    {
      return this.alu;    
    } 
    public Map<Integer,List<Veiculo>> getVeiculos()
    {
      return this.veiculos.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue()));   
    }
     public Map<String,Cliente> getClientes()
    {
      return this.clientes.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));   
    }
     public Map<String,Proprietario> getProprietarios()
    {
      return this.proprietarios.entrySet().stream().collect(Collectors.toMap(e->e.getKey(), e->e.getValue().clone()));   
    }
    
    public void registarCliente(Cliente c)throws MailRegistadoException
    {
      if (this.clientes.containsKey(c.getEmail())==true) throw new MailRegistadoException("Email já registado");
      this.clientes.put(c.getEmail(), c);
    }
    
     public void registarProprietario(Proprietario p) throws MailRegistadoException
     {
      if(this.proprietarios.containsKey(p.getEmail())==true) throw new MailRegistadoException("Email já registado");
      this.proprietarios.put(p.getEmail(), p);
     }
    
    public void iniciaSessaoC(String email, String password) throws LoginErradoException
    {
       Cliente c = clientes.get(email);
       if (c==null) throw new LoginErradoException("Email não registado");
       if(c.getPassword().equals(password)) this.clogado = c;
       else  throw new LoginErradoException("Password errada");
    }
    
    public void iniciaSessaoP(String email, String password) throws LoginErradoException
    {
       Proprietario p = proprietarios.get(email);
       if (p==null) throw new LoginErradoException("Email não registado");
       if(p.getPassword().equals(password)) this.plogado = p;
       else  throw new LoginErradoException("Password errada");
    }
    
    public void definirCoor(Point2D.Double orig,Point2D.Double dest)
    {
     this.clientes.get(this.alu.getC().getEmail()).setLocalizacao(orig);
     
     this.alu.setDestino(dest);
    }
    
    public void Registaveiculo (Veiculo v)
    {
     if (!this.veiculos.containsKey(v.getNif())){
     List<Veiculo> res =new ArrayList<>();
     res.add(v);
     this.veiculos.put(v.getNif(),res);}
     else this.veiculos.get(v.getNif()).add(v);
     
    }
  
      public void setClog(Cliente c)
    {
     this.clogado=c;   
    }
    
    public double totalFaturadoPeriodo(String Matricula,LocalDate data)
    {
     double total=0;  
     List<Veiculo> v=this.veiculos.get(this.plogado.getNif());
     Veiculo x=null;  
     for(Veiculo l:v){
      if(l.getMatricula().equals(Matricula))
      x=l;
     }
     total=x.totalFaturadoPeriodo(data);
     return total;
    }
    
    public String veiMaisProx(Point2D.Double orig,Point2D.Double dest,String tipo)
    {
         double distmin=-1;
         double dist=0;
         Veiculo v =null;
         for(Map.Entry<Integer,List<Veiculo>> e: veiculos.entrySet()){
          for(Veiculo l: e.getValue()){ 
           if(l.getTipo().equals(tipo) && l.TemAutonomia(l.getLocalizacao().distance(dest)) ){
           dist=orig.distance(l.getLocalizacao());
          
           if(distmin==-1 || distmin > dist){
             distmin=dist;
             v=l;
           }
            }
         }
        }
      
        Map<Integer,Proprietario>l=proprietarios.entrySet().stream().collect(Collectors.toMap(e->e.getValue().getNif(),e->e.getValue().clone()));
      
   
        v.setAutonomiaAtual(v.getAutonomiaAtual()-(v.getConsumo()*v.getLocalizacao().distance(dest)));
        this.alu.setP(l.get(v.getNif()));   
      
        System.out.println("Nif prop"+(v.getNif()));
        this.alu.addVeiculo(v);
        return v.getMatricula();
        
    }
    public String veiMaisBarato(Point2D.Double dest,String tipo)
    {
      
      double customin=-1;
      double custo=0;
      Veiculo v =null;
     
     for(Map.Entry<Integer,List<Veiculo>> e: veiculos.entrySet()){
       for(Veiculo l: e.getValue()){ 
        if(l.getTipo().equals(tipo)&& l.TemAutonomia(l.getLocalizacao().distance(dest))){
        custo=l.getPrecoBase();
       
        if(customin==-1 || customin > custo){
          customin=custo;
          v=l;
        }
        }
      }
     }

  
   
     Map<Integer,Proprietario>l=proprietarios.entrySet().stream().collect(Collectors.toMap(e->e.getValue().getNif(),e->e.getValue().clone()));
   

     v.setAutonomiaAtual(v.getAutonomiaAtual()-(v.getConsumo()*v.getLocalizacao().distance(dest)));
     this.alu.setP(l.get(v.getNif()));   
     System.out.println("Nif prop"+(v.getNif()));
     
     this.alu.addVeiculo(v);
     return v.getMatricula();
        



    }

    public void atualizaPosVeiculo (Point2D.Double dest)
    {
     this.alu.atualizaPosVeiculo(dest);
    }

    public void addAluguer(){
     
      Aluguer a=this.plogado.getPedidos().get(0);
      for(Aluguer aX:this.plogado.getPedidos())
       System.out.println(aX.toString());
      
       double kms;

       String email;
     
       a.setAceite(true);
      
       kms=this.clientes.get(a.getC().getEmail()).getKmPercorridos()+a.DistanciaAluguer(a.getDestino());
       
       this.clientes.get(a.getC().getEmail()).setKmPercorrido(kms);
       
       a.addTotalFaturado(this.custoViagem(a.getDestino()));
       int nif=a.getP().getNif();
       int flag=0;
       Veiculo x=null;
       Iterator<Veiculo>it=this.veiculos.get(nif).iterator();
       while(it.hasNext() && flag==0){
         Veiculo l=it.next();
         if (l.getMatricula().equals(a.getVeiculo().getMatricula())){
          flag=1;
          x=l;
         }
       }
       
       x.addToHistorial(a.clone()); 
     
       email=a.getP().getEmail();
      
       this.proprietarios.get(email).addToHistorial(a.clone());
      
       email=a.getC().getEmail();
       
       this.clientes.get(email).addToHistorial(a.clone());
  
    }
    
    public void addPedido(Aluguer alu){
    System.out.println(alu.getC().toString());
    this.proprietarios.get(alu.getP().getEmail()).addToPedidos(alu);
    System.out.println(this.proprietarios.get(alu.getP().getEmail()).getPedidos().get(0).getC().toString());
   
  }
    
    public boolean TemAutonomia()
    {
     if (this.alu.getVeiculo().getAutonomiaAtual()>DistanciaAluguer(this.alu.getDestino()))
     return true;
     
     return false;
    }
    public void addKmspercoridos ()
    {
       String email=this.alu.getC().getEmail();
       double kms;
        kms=this.clientes.get(email).getKmPercorridos()+this.alu.DistanciaAluguer(this.alu.getDestino());
        this.clientes.get(email).setKmPercorrido(kms);
    }
    
    
    public double duracaoViagem (Point2D.Double dest){
 
      double duracao;
      duracao=this.alu.getVeiculo().getVel_Med()*this.alu.DistanciaAluguer(dest);
      this.alu.setDuracao(duracao);
      return duracao;
    }

   public void atualizaData() 
   {
   this.alu.atualizaData();

   }

    public double DistanciaAluguer(Point2D.Double dest)
    {
      double res=0;
       res+=this.alu.getVeiculo().getLocalizacao().distance(dest);   
     return res;
     }
    
    public double custoViagem(Point2D.Double dest)
    {
        double custo; 
        custo=this.alu.getVeiculo().getPrecoBase()*this.DistanciaAluguer(dest);
        this.alu.setCusto(custo);
        
        return custo;
    }
    public void showAluguer(LocalDate data,LocalDate data2,int x)
    {
        List<Aluguer> res=new ArrayList<>();
        if (x==1){
        for(Map.Entry<LocalDate,List<Aluguer>> entry:this.clogado.getHistorial().entrySet()){
           if (entry.getKey().equals(data)||entry.getKey().equals(data2)||entry.getKey().isAfter(data)||entry.getKey().isBefore(data2)){
           Iterator<Aluguer> it=entry.getValue().iterator();
           while(it.hasNext()){
           Aluguer l=it.next();    
           res.add(l.clone());
           System.out.println(l.toString());
           }
               
           }
            
        }}else{
           for(Aluguer entry: this.plogado.getHistorial()){

           Iterator<Aluguer> it=this.plogado.getHistorial().iterator();
           while(it.hasNext()){
           Aluguer l=it.next();    
           res.add(l.clone());
           System.out.println(l.toString());
           }
               
           }
        }}
    
    
    public void defCliente(Cliente c){

    this.alu.setC(c);
    
    }
    public void defCliente(){

    this.alu.setC(this.clogado);
   

    }

    public void addClassCli (int Nif,int nota){
    String key;
    Cliente c=new Cliente();
    for(Map.Entry<String,Cliente> e:clientes.entrySet()){
     if (e.getValue().getNif()==Nif){
     key=e.getKey();
     double total=this.clientes.get(key).getTotal();
     this.clientes.get(key).setTotal(total+nota);
     c=this.clientes.get(key).clone();
     List<Aluguer>al= c.getHistorial().values().stream().flatMap(List::stream).collect(Collectors.toList());
     int nmralugueres=al.size();
     double classificacao=(double)c.getTotal()/nmralugueres;
     this.clientes.get(key).setClassificacao(classificacao);
     }
    }
    }

    public void addClassProp (String matricula,int nota){
    int key=0;
    String mail=" ";
    int flag=0;
    for(Map.Entry<Integer,List<Veiculo>> l :this.veiculos.entrySet()){
     for(Veiculo v:l.getValue()){
      if (v.getMatricula().equals(matricula)){
        v.setClassificacao(v.getClassificacao()+nota);
        key=l.getKey();
    
      }
      
    }

    }
    if (key!=0){
    int total=this.veiculos.get(key).stream().mapToInt(v->v.getClassificacao()).sum();
    List<Veiculo>l=this.veiculos.get(key).stream().collect(Collectors.toList());
    int nmrveiculos=l.size();
    Iterator<Proprietario> it =this.proprietarios.values().iterator();
    while(it.hasNext()&& flag==0){
      Proprietario p=it.next();
      if (p.getNif()==key) {mail=p.getEmail();flag=1;}
    }
    this.proprietarios.get(mail).setClassificacao(total/nmrveiculos);
    }  
    
    }
    
        
    public Cliente tiraCliente (int Nif){
    Map<Integer,Cliente> newMap = new HashMap<>();
    newMap=this.clientes.entrySet().stream().collect(Collectors.toMap(e->e.getValue().getNif(),e->e.getValue().clone())); 
    return (newMap.get(Nif).clone());
    
    }
    public void updateLocalViatCli(Point2D.Double dest)
    {
        
     this.alu.atualizaPosVeiculo(dest);   
     this.clientes.get(this.alu.getC().getEmail()).setLocalizacao(dest);
    }
    
    public void addRegistoC ()
    {
    
      String email=this.alu.getC().getEmail();
      this.clientes.get(email).addToHistorial(this.alu);
    }
    public void addRegistoP ()
    {
      String email=this.alu.getP().getEmail();
      this.proprietarios.get(email).addToHistorial(this.alu);
    }
    public void addRegistoV ()
    {
      int nif=this.alu.getVeiculo().getNif();
      int flag=0;
       Veiculo x=null;
       Iterator<Veiculo>it=this.veiculos.get(nif).iterator();
       while(it.hasNext() && flag==0){
         Veiculo l=it.next();
         if (l.getMatricula().equals(this.alu.getVeiculo().getMatricula())){
          flag=1;
          x=l;
         }
       }
       x.addToHistorial(this.alu);  
    }
    public void tempoChegada(Point2D.Double orig)
    {
      double dist;
      double tempo;
      dist=orig.distance(this.alu.getVeiculo().getLocalizacao());
      tempo=dist/4.0;
      this.alu.setTempoclivei(tempo);
    }
    public List<Veiculo> VeiculosProp ()
    {
        List<Veiculo> res=new ArrayList<>();
        Iterator<Veiculo>it;
        for (Map.Entry<Integer,List<Veiculo>> entry : this.veiculos.entrySet()) {
        if(entry.getKey()==this.plogado.getNif()){
         it=entry.getValue().iterator();
         while(it.hasNext()){
          Veiculo l=it.next();
          res.add(l);
         }
        }
        }
        return res;
      
    } 
    
    public void addTotalFaturado()
    {
        
     this.alu.addTotalFaturado(this.custoViagem(this.alu.getDestino()));   
    }
    
    public void setAlu (Aluguer alu){
   
    this.alu=alu.clone();

    }

   
      public List<Cliente> top10Aluguers(){
        Map<String,Integer>res=this.clientes.values().stream().collect(Collectors.toMap(c->c.getEmail(),c->c.contaAlugueres()));
        List<Cliente> Lstc=new ArrayList<>();
        List<Integer>top=res.values().stream().collect(Collectors.toList());
        Collections.sort(top,Collections.reverseOrder());
        top=top.stream().limit(10).collect(Collectors.toList());
        int flag=0;
        Iterator<Integer>it=top.iterator();
        List<String> usados=new ArrayList<>();
         while(it.hasNext()){
          int t=it.next();
          flag=0;
          
          
          for(Map.Entry<String,Integer>l:res.entrySet()){
           if (l.getValue()==t && flag==0&& !usados.contains(l.getKey())){
           Lstc.add(this.clientes.get(l.getKey()).clone());
           usados.add(l.getKey());
           flag=1;
          }
         }
        }
        return Lstc;
        }
    
        public List<Cliente> top10Kms(){
         return this.clientes.values().stream()
         .sorted(new KmComparator())
         .limit(10).collect(Collectors.toList());
         }
 
    
    public double showTotalFaturado(String Matricula){
        Iterator<Veiculo>it;
        int flag=0;
        double total=0;
        for (Map.Entry<Integer,List<Veiculo>> entry : this.veiculos.entrySet()) {
    
          if(entry.getKey()==this.plogado.getNif()){
    
         it=entry.getValue().iterator();
          while(it.hasNext() && flag==0){
        
           Veiculo l=it.next();
           if(l.getMatricula().equals(Matricula)){
         
           total=l.getTotalFaturado();
           flag=1;
           } 
          }
         }
        }
     return total;
    }
    public Cliente ShowDadosC()
    {
     return this.clogado.clone();   
    }
    public Proprietario ShowDadosP()
    {
     return this.plogado.clone();   
    }
    public static UMCarroJa lerDados() throws IOException, ClassNotFoundException{
      ObjectInputStream ois = new ObjectInputStream(new FileInputStream("UMCarroJa.data"));
      UMCarroJa c = (UMCarroJa) ois.readObject();
      ois.close();
      return c;
}
    public void gravar() throws IOException{
      ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("UMCarroJa.data"));
      oos.writeObject(this);
      
      oos.flush();
      oos.close();
}
}
