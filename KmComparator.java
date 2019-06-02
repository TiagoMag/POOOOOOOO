import java.util.Comparator;
import java.io.Serializable;

public class KmComparator implements Comparator <Cliente>,Serializable
{
    public int compare(Cliente c1,Cliente c2){
        if (c1.getKmPercorridos()>c2.getKmPercorridos())return -1;
        if (c1.getKmPercorridos()<c2.getKmPercorridos()) return 1;
        return 0;
     }

}
