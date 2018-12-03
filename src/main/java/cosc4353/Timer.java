package cosc4353;

public class Timer extends Thread { 
	public void run(){ 
        try{ 
            //System.out.println ("Timer Thread " + Thread.currentThread().getId() + " is running");
            System.out.println ("30 Seconds left");
            sleep(10000);
            System.out.println ("20 Seconds left");
            sleep(10000);
            System.out.println ("10 Seconds left");
            sleep(5000);
            System.out.println ("5 Seconds left");
            sleep(1000);
            System.out.println ("4 Seconds left");
            sleep(1000);
            System.out.println ("3 Seconds left");
            sleep(1000);
            System.out.println ("2 Seconds left");
            sleep(1000);
            System.out.println ("1 Second left");
            sleep(1000);
            //System.out.println ("Timer Thread " + Thread.currentThread().getId() + " Ended");
        }catch (Exception e) { 
            //System.out.println ("Exception is caught --timer interupted"); 
        } 
    }  
}