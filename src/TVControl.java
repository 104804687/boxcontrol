import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class TVControl {
    public  static void TVSet(String ip,int Channel,boolean ChangeVol) {
        List<String> list=new ArrayList<>();
        list.add("adb connect "+ip);
        list.add("adb reboot");
        CmdTool.cmd2(list);
        try {
            TimeUnit.SECONDS.sleep(10);
        }catch (InterruptedException e){

        }

        list.clear();
        list.add("adb connect "+ip);
        list.add("adb shell");
        list.add("input keyevent KEYCODE_ENTER");
        list.add("input keyevent KEYCODE_ENTER");
        list.add("input keyevent KEYCODE_ENTER");
        for(int i=1;i<Channel;i++){
            list.add("input keyevent KEYCODE_DPAD_UP");
        }
        if(ChangeVol){
            for(int i=0;i<20;i++){
                list.add("input keyevent KEYCODE_VOLUME_UP");
            }
        }
        list.add("exit");
        CmdTool.cmd2(list);

        list.clear();
        list.add("adb disconnect");
        CmdTool.cmd2(list);
    }

    public static void TVSet(){
        //TVControl.TVSet("10.118.213.112",18);
        //TVControl.TVSet("10.118.213.112",6);
        Path path= Paths.get("tvchannel.txt");
        try {
            List<String> lines= Files.readAllLines(path);
            for (String str:lines) {
                //System.out.println(str);
                String[] aa=str.split(",");
                if (aa.length!=2)continue;
                System.out.println("IP:"+aa[0]+" <---> Channel:"+aa[1]);
                TVControl.TVSet(aa[0],Integer.parseInt(aa[1]),false);
            }
        }catch (IOException e){

        }
    }
}
