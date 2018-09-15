package test.bus;

import java.util.HashMap;
import java.util.Map;

public class Bus {

    private String key;
    private Object value;
    private Map<String,Bus> subNode = new HashMap<String, Bus>();

    @Override
    public String toString() {
        return "["+key+":"+value+",subNode:"+subNode.get(key)+"]";
    }

    public boolean put(Object... args) {
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("参数至少为:key,value");
        }
        int length = args.length;
        if (length == 2){
            key = (String) args[0];
            value = args[1];
        }else{
            key = (String) args[0];
            Bus bus = subNode.get(key)==null?new Bus():subNode.get(key);
            Object[] newArgs = new Object[length-1];
            System.arraycopy(args,1,newArgs,0,newArgs.length);
            bus.put(newArgs);
            subNode.put(key,bus);
        }
        return true;
    }
    public Object get(Object... args) {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("参数至少为:key");
        }
        int length = args.length;
        if (length == 1){
            return value;
        }else {
            Bus bus = subNode.get(args[0]);
            if (bus==null){
                return null;
            }
            Object[] newArgs = new Object[length-1];
            System.arraycopy(args,1,newArgs,0,newArgs.length);
            return bus.get(newArgs);
        }

    }
    public boolean remove(Object... args) {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("参数至少为:key");
        }
        int length = args.length;
        if (length == 1){
            key = null;
            value = null;
            subNode.clear();
        }else{
            Bus bus = subNode.get(args[0]);
            if (bus==null){
                return true;
            }
            Object[] newArgs = new Object[length-1];
            System.arraycopy(args,1,newArgs,0,newArgs.length);
            return bus.remove(newArgs);
        }
        return true;
    }


    public static void main(String[] args){
        Bus b = new Bus();
        b.put("1",1);
        b.put("1","2",2);
        b.put("1","2","3",3);
//        b.remove("1");
//        b.remove("1","2");
//        b.remove("1","2","3");
        System.out.println(b.get("1"));
        System.out.println(b.get("1","2"));
        System.out.println(b.get("1","2","3"));
        System.out.println(b.toString());
    }
}
