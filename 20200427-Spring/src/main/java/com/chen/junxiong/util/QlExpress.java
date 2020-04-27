package com.chen.junxiong.util;

import com.chen.junxiong.model.FlightInfo;

import com.chen.junxiong.model.CheckinRequest;
import com.chen.junxiong.model.Passenger;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.apache.commons.lang.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author junxiong.chen
 * @date 4/27
 */
public class QlExpress {

    public static void main(String[] args) {
        test2();
    }

    private static void test2(){
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        try {
            runner.addFunctionOfClassMethod("取绝对值", Math.class.getName(), "abs", new String[]{"int"}, null);
            String exp = "取绝对值(-100)";
            Object execute = runner.execute(exp, context, null, false, false);
            System.out.println(execute);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * qlExpress 基础方法
     */
    private static void test1() {
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("checkinRequest", build());
        String express = "import com.chen.junxiong.model.CheckinRequest;" +
                "import com.chen.junxiong.model.Passenger;" +
                "import com.chen.junxiong.model.FlightInfo;" +
                "import org.apache.commons.lang.StringUtils;" +
                " map = new HashMap();" +
                "map.put(\"token\", \"dawewwds\");\n" +
                "passengers = checkinRequest.getPassengers();\n" +
                "for (int i = 0; i < passengers.size(); i++) {\n" +
                "  passenger = passengers.get(i);\n" +
                "  map.put(\"name\" + i, passenger.getName());\n" +
                "  map.put(\"birthday\" + i, passenger.getBirthday().substring(0, 4));\n" +
                "  map.put(\"ticketNo\" + i, passenger.getTicketNo().replaceAll(\"-\", \"\"));\n" +
                "}" +
                " sb = new StringBuilder();\n" +
                "        keySet = map.keySet();\n" +
                "        objects = keySet.toArray();\n" +
                "        for (int i = 0; i < objects.length; i++) {\n" +
                "            object = objects[i];\n" +
                "            s = map.get(object);\n" +
                "            if (StringUtils.isEmpty(sb.toString())) {\n" +
                "                sb.append(object).append(\"=\").append(s);\n" +
                "                continue;\n" +
                "            }\n" +
                "            sb.append(\"&\").append(object).append(\"=\").append(s);\n" +
                "        }" +
                "return sb.toString()";
        try {
            Object execute = runner.execute(express, context, null, false, false);
            System.out.println(execute);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static CheckinRequest build() {
        Passenger passenger = new Passenger();
        passenger.setName("cjx");
        passenger.setTicketNo("781-122223133");
        passenger.setBirthday("2020-04-01");

        Passenger passenger1 = new Passenger();
        passenger1.setName("cjx2");
        passenger1.setTicketNo("781-1222231345");
        passenger1.setBirthday("2020-05-01");

        FlightInfo flightInfo = new FlightInfo();
        flightInfo.setCarrier("MU");
        flightInfo.setFlightNo("MU1234");
        flightInfo.setDeptCode("PEK");
        flightInfo.setDeptDate("2020-01-01");

        CheckinRequest request = new CheckinRequest();
        request.setPassengers(Lists.newArrayList(passenger, passenger1));
        request.setFlightInfo(flightInfo);

        return request;
    }


}
