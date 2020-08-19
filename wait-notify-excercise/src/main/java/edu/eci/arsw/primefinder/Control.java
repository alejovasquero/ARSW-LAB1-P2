/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.primefinder;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class Control extends Thread {
    
    private final static int NTHREADS = 3;
    private final static int MAXVALUE = 30000000;
    private final static int TMILISECONDS = 5000;

    private final int NDATA = MAXVALUE / NTHREADS;
    
    private PrimeFinderThread pft[];

    private Control() {
        super();
        this.pft = new  PrimeFinderThread[NTHREADS];

        int i;
        for(i = 0;i < NTHREADS - 1; i++) {
            PrimeFinderThread elem = new PrimeFinderThread(i*NDATA, (i+1)*NDATA);
            pft[i] = elem;
        }
        pft[i] = new PrimeFinderThread(i*NDATA, MAXVALUE + 1);
    }
    
    public static Control newControl() {
        return new Control();
    }

    @Override
    public void run() {


        for(int i = 0;i < NTHREADS;i++ ) {
            pft[i].start();
        }


        while(true){
            try {
                Thread.sleep(5000);
                for(PrimeFinderThread p: pft){
                    p.pausar();
                    System.out.println(Arrays.toString(p.getObjectPrimes()));
                }
                System.in.read();
                for(PrimeFinderThread p: pft){
                    p.continuar();
                }
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

}
