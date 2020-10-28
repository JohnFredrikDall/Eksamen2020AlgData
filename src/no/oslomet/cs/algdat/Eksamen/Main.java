package no.oslomet.cs.algdat.Eksamen;


import java.util.Comparator;

public class Main {
    public static void main(String[]args){
        int[] a = {4,7,2,9,4,10,8,7,4,6,1};
        EksamenSBinTre<Integer> tre = new EksamenSBinTre<>(Comparator.naturalOrder());
        for (int verdi : a) tre.leggInn(verdi);

        tre.fjernAlle(7);
    }
}
