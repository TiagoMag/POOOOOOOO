import java.util.Comparator;

public class KmComparator implements Comparator <Cliente>
{
    public int compare(Cliente c1,Cliente c2){
        if (c1.getKmPercorridos()>c2.getKmPercorridos())return -1;
        if (c1.getKmPercorridos()<c2.getKmPercorridos()) return 1;
        return 0;
     }

}
