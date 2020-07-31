package com.chen.junxiong.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.chen.junxiong.model.FlightInfo;

import com.chen.junxiong.model.CheckinRequest;
import com.chen.junxiong.model.Passenger;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.jayway.jsonpath.JsonPath;
import com.ql.util.express.DefaultContext;
import com.ql.util.express.ExpressRunner;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author junxiong.chen
 * @date 4/27
 */
public class QlExpress {

    public static void main(String[] args) {

        List<String> a = Lists.newArrayList("2","3","4");
        String join = Joiner.on(",").skipNulls().join(a);
        System.out.println(join);
//        test2();
//        test3QL();
//        execute("未知,121");
//        System.out.println(test3());
       /* String s = "[{\"orderNo\":\"xep200616114100165\",\"printer\":\"未知1\",\"name\":\"池嘉敏\",\"eticketNo\":\"784-1495140419\",\"xcdPrintStatus\":\"UN_PRINT\",\"class\":\"com.qunar.flightqm.xcd.dto.XcdPrinterInfoDto\"},{\"orderNo\":\"xep200616114100165\",\"printer\":\"未知\",\"name\":\"吴纲\",\"eticketNo\":\"784-1495140420\",\"xcdPrintStatus\":\"UN_PRINT\",\"class\":\"com.qunar.flightqm.xcd.dto.XcdPrinterInfoDto\"}]";
//        execute(s);

        List<String> read = (List<String>) JSONPath.read(s, "$.printer");
        execute(read);*/

//        System.out.println(read);
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
        context.put("checkinRequest", build("cjx"));
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

    private static CheckinRequest build(String name) {
        Passenger passenger = new Passenger();
        passenger.setName(name);
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

    private static JSONObject getFlightInfo(String carrier){

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("printer", carrier);
        jsonObject.put("flightNO", "123231");
        return jsonObject;
    }

    private static void test3QL(){
        List<JSONObject> checkinRequests = Lists.newArrayList(getFlightInfo("cjx1"), getFlightInfo("cjx2"));
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("common", checkinRequests);
        String express = "import com.alibaba.fastjson.JSONObject;" +
                "import com.google.common.collect.Sets;" +
                "result = \"部分打印\";        " +
                "set = Sets.newHashSet();        " +
                "for (int i = 0; i < common.size(); i++) {            " +
                "jsonObject = common.get(i);            " +
                "printer = jsonObject.getString(\"printer\");            " +
                "set.add(printer);        " +
                "}        " +
                "if (set.size() == 1) {            " +
                "return common.get(0).getString(\"printer\");        " +
                "}        " +
                "return result;";
        try {
            Object execute = runner.execute(express, context, null, false, false);
            System.out.println(execute);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static String test3() {
        List<JSONObject> common = Lists.newArrayList(getFlightInfo("cjx2"), getFlightInfo("cjx2"));

        String result = "部分打印";
        Set<String> set = Sets.newHashSet();
        for (int i = 0; i < common.size(); i++) {
            JSONObject jsonObject = common.get(i);
            String carrier = jsonObject.getString("carrier");
            set.add(carrier);
        }
        if (set.size() == 1) {
            return common.get(0).getString("carrier");
        }
        return result;
    }

    private static String test4(String common){
        String result = "部分打印";
        Set<String> set = new HashSet<String>();
        String[] split = common.split(",");
        for (int i = 0; i < split.length; i++) {
            set.add(split[i]);
        }
        if (set.size() == 1) {
            return split[0];
        }
        return result;
    }

    private static void execute(List<String> common){
        ExpressRunner runner = new ExpressRunner();
        DefaultContext<String, Object> context = new DefaultContext<String, Object>();
        context.put("common", common);
        String express = "import java.util.List;s = common.get(0);for (int i = 1; i < common.size(); i++) {if (!common.get(i).equals(s)) {return \"部分打印\";}}return s;";
        try {
            Object execute = runner.execute(express, context, null, false, false);
            System.out.println(execute);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private String  t(List<String> common){
        String s = common.get(0);
        for (int i = 1; i < common.size(); i++) {
            if (!common.get(i).equals(s)) {
                return "部分打印";
            }
        }
        return s;
    }

}
