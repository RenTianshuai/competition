package cn.com.yusys.geek.subject1;


import cn.com.yusys.geek.subject1.utils.SQLUtil;

import java.util.List;
import java.util.Map;

public class Ranking {

    public static void main(String[] args){
        long startTime = System.currentTimeMillis();
        try {
            String sql = "select DATA_DT,INDEX_ID from YXKJ_EVAL_RES_LIST group by DATA_DT,INDEX_ID";
            List<Map<String,Object>> groups = SQLUtil.resultQuery(sql, null);
            for (Map<String,Object> group : groups){
                sql = "select DATA_DT,ORG_NO,INDEX_ID from YXKJ_EVAL_RES_LIST where DATA_DT=? and INDEX_ID=? order by INDEX_VAL desc";
                String[] params1 = new String[2];
                params1[0] = (String) group.get("DATA_DT");
                params1[1] = (String) group.get("INDEX_ID");
                List<Map<String,Object>> ranks = SQLUtil.resultQuery(sql, params1);
                if (ranks != null){
                    sql = "update YXKJ_EVAL_RES_LIST set GRP_RANK=? where DATA_DT=? and ORG_NO=? and INDEX_ID=?";
                    for (int i=0; i<=ranks.size(); i++){
                        String[] params2 = new String[4];
                        params2[0] = (i+1)+"";
                        params2[1] = (String) group.get("DATA_DT");
                        params2[2] = (String) ranks.get(i).get("ORG_NO");
                        params2[3] = (String) group.get("INDEX_ID");
                        SQLUtil.insertData(sql,params2);
                    }
                }
            }
            long endTime = System.currentTimeMillis();
            System.out.println("用时ms：" + (endTime - startTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
