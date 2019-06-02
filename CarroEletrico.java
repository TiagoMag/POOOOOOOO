import java.util.List;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.util.Map;
import java.io.Serializable; 

public class CarroEletrico extends Veiculo implements Serializable 
{
    // variáveis de instância
    
    public CarroEletrico()
    {
        super();
    }

    public CarroEletrico(double vel_med,double precobase,double consumo,String matricula,int nif,
    Map<LocalDate,List<Aluguer>> historial,int classificacao,Point2D.Double localizacao,double autonomia,double autonomiaAtual,int uso,String marca,String tipo,double total_faturado)
    {
       super(vel_med,precobase,consumo,matricula,nif,historial,classificacao,localizacao,autonomia,autonomiaAtual,uso,marca,tipo,total_faturado);
    }
    
    public CarroEletrico(CarroEletrico c)
    {
     super(c.getVel_Med(),c.getPrecoBase(),c.getConsumo(),c.getMatricula(),c.getNif(),c.getHistorial(),c.getClassificacao(),c.getLocalizacao(),c.getAutonomia(),c.getAutonomiaAtual(),c.getUso(),c.getMarca(),
     c.getTipo(),c.getTotalFaturado());
    }
    
    
    public CarroEletrico clone()
    {
      return new CarroEletrico(this);   
    }

}
