import java.util.List;
import java.awt.geom.Point2D;
import java.time.LocalDate;
import java.util.Map;
import java.io.Serializable;

/**
 * Escreva a descrição da classe CarroGasolina aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
public class CarroGasolina extends Veiculo implements Serializable
{
    // variáveis de instância - substitua o exemplo abaixo pelo seu próprio
    
    

    /**
     * COnstrutor para objetos da classe CarroGasolina
     */
    public CarroGasolina()
    {
        super();
        
    }

    public CarroGasolina(double vel_med,double precobase,double consumo,String matricula,int nif,
    Map<LocalDate,List<Aluguer>> historial,int classificacao,Point2D.Double localizacao,double autonomia,double autonomiaAtual,int uso,String marca,String tipo,double total_faturado)
    {
       super(vel_med,precobase,consumo,matricula,nif,historial,classificacao,localizacao,autonomia,autonomiaAtual,uso,marca,tipo,total_faturado);
    }
    
    public CarroGasolina(CarroGasolina c)
    {
     super(c.getVel_Med(),c.getPrecoBase(),c.getConsumo(),c.getMatricula(),c.getNif(),c.getHistorial(),c.getClassificacao(),c.getLocalizacao(),c.getAutonomia(),c.getAutonomiaAtual(),c.getUso(),c.getMarca(),
     c.getTipo(),c.getTotalFaturado());
    }
    
    
    public CarroGasolina clone()
    {
      return new CarroGasolina(this);   
    }
}
