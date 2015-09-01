package com.weson.util.date;

import com.weson.util.db.DBUtil;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

public class ClearDateBase {

    public static void main(String[] args) {
        Log log = LogFactory.getLog("cleardatebase");
        DateUtil date = new DateUtil();
        System.out.println(date);
        DateUtil dateUtil = date.addMonths(-1);
        DateUtil dateUtil1 = date.addMonths(-2);
        DateUtil dateUtil2 = date.addDays(-6);
        String timeStamp = dateUtil.toString("yyyy-MM-dd HH:mm:ss");
        String timeStamp1 = dateUtil1.toString("yyyy-MM-dd HH:mm:ss");
        String timeStamp2 = dateUtil2.toString("yyyy-MM-dd HH:mm:ss");

        System.out.println(timeStamp);

        DBUtil dbUtil = new DBUtil();
        String activitysncode_sql = "delete from t_activitysncode where aid in (select id from t_activity where enddate<\"" + timeStamp + "\")limit 5000";
        String bargainactorrecord_sql = "delete from t_bargainactorrecord where actorId in (select id from t_bargainactor where configId in (select id from t_bargainconfig where delistTime <\"" + timeStamp + "\"))limit 5000";
        String redpacksendlog_sql = "delete from t_redpacksendlog where reqtime<\"" + timeStamp1 + "\" limit 5000";
        String crmftalk_sql = "delete from t_crmftalk where time<\"" + timeStamp2 + "\" limit 5000";
        try {
            int total = 0;
            for (int i = 0; i < 20000; i++) {
                int count = dbUtil.delete(activitysncode_sql);
                total += count;
                if (count < 5000) break;
            }
            log.info("删除activitysncode表" + total + "条记录");
            total = 0;
            for (int i = 0; i < 10000; i++) {
                int count = dbUtil.delete(bargainactorrecord_sql);
                total += count;
                if (count < 5000) break;
            }
            log.info("删除bargainactorrecord表" + total + "条记录");
            total = 0;
            for (int i = 10; i < 10000; i++) {
                int count = dbUtil.delete(redpacksendlog_sql);
                total += count;
                if (count < 5000) break;
            }
            log.info("删除redpacksendlog表" + total + "条记录");
            total = 0;

            for (int i = 0; i < 10000; i++) {
                int count = dbUtil.delete(crmftalk_sql);
                total += count;
                if (count < 5000) break;
            }
            log.info("删除crmftalk表" + total + "条记录");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
