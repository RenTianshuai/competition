package test.bus;

import java.util.HashMap;
import java.util.Map;

public class Bus {

    private Map<Object,Object> value = new HashMap<Object, Object>();
    private Map<Object,Bus> subBus = new HashMap<Object, Bus>();

    @Override
    public String toString() {
        return "[" + value.toString() + ":" + subBus.toString() + "]";
    }

    public boolean put(Object... args) {
        if (args == null || args.length < 2) {
            throw new IllegalArgumentException("参数至少为:key,value");
        }
        int length = args.length;
        if (length == 2){
            value.put(args[0],args[1]);
        }else{
            Bus bus = subBus.get(args[0])==null?new Bus():subBus.get(args[0]);
            Object[] newArgs = new Object[length-1];
            System.arraycopy(args,1,newArgs,0,newArgs.length);
            bus.put(newArgs);
            subBus.put(args[0],bus);
        }
        return true;
    }
    public Object get(Object... args) {
        if (args == null || args.length < 1) {
            throw new IllegalArgumentException("参数至少为:key");
        }
        int length = args.length;
        if (length == 1){
            return value.get(args[0]);
        }else {
            Bus bus = subBus.get(args[0]);
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
            value.remove(args[0]);
            subBus.remove(args[0]);
        }else{
            Bus bus = subBus.get(args[0]);
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
        b.put("b",1);
        b.put("1","2",2);
        b.put("1","2","3",3);
        b.put("1","xx","xx");
//        b.remove("1");
//        b.remove("1","2");
//        b.remove("1","2","3");
        System.out.println(b.get("1"));
        System.out.println(b.get("1","2"));
        System.out.println(b.get("1","2","3"));
        System.out.println(b.toString());
    }
}
