import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class CmdTool {

    public static void cmd2(List<String> list) {
        String[] command={"cmd",};
        try {
            Process p=Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(),System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(),System.out)).start();
            PrintWriter stdin=new PrintWriter(p.getOutputStream());

            int returnCode;
            for (String s:list) {
                stdin.println(s);
                /*try {
                    Thread.sleep(500);
                }catch (InterruptedException e){

                }*/
                //TimeUnit.SECONDS.sleep(1);
            }
            stdin.println("");

            stdin.close();
            try {
                returnCode=p.waitFor();
                System.out.println("Return code="+returnCode);
            }catch (InterruptedException e){
            }
            p.getErrorStream().close();
            p.getInputStream().close();
            p.getOutputStream().close();
        }catch (IOException e){
        }
    }


    public static void cmd()  {
        String[] command={"cmd",};
        try {
            Process p=Runtime.getRuntime().exec(command);
            new Thread(new SyncPipe(p.getErrorStream(),System.err)).start();
            new Thread(new SyncPipe(p.getInputStream(),System.out)).start();
            PrintWriter stdin=new PrintWriter(p.getOutputStream());

            stdin.println("dir C:\\/Q/A");
            stdin.println("");
            stdin.println("");
            stdin.println("dir");

            stdin.close();
            try {
                int returnCode=p.waitFor();
                System.out.println("Return code="+returnCode);
            }catch (InterruptedException e){
            }
        }catch (IOException e){
        }
    }
}
