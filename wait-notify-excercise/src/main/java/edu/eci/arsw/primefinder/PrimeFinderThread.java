package edu.eci.arsw.primefinder;

import java.util.LinkedList;
import java.util.List;

public class PrimeFinderThread extends Thread{

	
	int a,b;
	private List<Integer> primes;
	private boolean pause;
	public PrimeFinderThread(int a, int b) {
		super();
		this.primes = new LinkedList<>();
		this.a = a;
		this.b = b;
		this.pause = false;
	}

        @Override
	public void run(){
            for (int i= a;i < b;i++){						
                synchronized (this){
					while(pause){
						try {
							this.wait();
						} catch (InterruptedException e) {
							//e.printStackTrace();
						}
					}
					if (isPrime(i)){
						synchronized (primes) {
							primes.add(i);
						}
						System.out.println(i);
					}
				}

            }
	}
	
	boolean isPrime(int n) {
	    boolean ans;
            if (n > 2) { 
                ans = n%2 != 0;
                for(int i = 3;ans && i*i <= n; i+=2 ) {
                    ans = n % i != 0;
                }
            } else {
                ans = n == 2;
            }
	    return ans;
	}

	public Object[] getObjectPrimes() {
		Object[] ans = null;
		synchronized (primes){
			ans = primes.toArray();
		}
		return ans;
	}


	public synchronized void continuar(){
		this.pause = false;
		this.notify();
	}

	public synchronized void pausar(){
		this.pause = true;
		this.notify();
	}

}
