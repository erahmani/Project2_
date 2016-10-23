package DA.parse;

import BL.server.Deposit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

public class MyJSONParser implements Parser {
    public Object parse(String address, Class classToBeBounded) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(address));
            return convertToClassToBeBounded(jsonObject, classToBeBounded);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Object convertToClassToBeBounded(JSONObject jsonObject, Class classToBeBounded) {
        try {
            Field[] fields = (classToBeBounded).getDeclaredFields();
            Object object = classToBeBounded.newInstance();
            for (Field field : fields) {
                String fieldName = field.getName();
                field.setAccessible(true);
                if (jsonObject.get(fieldName) instanceof Number) {
                    field.set(object, ((Number) (jsonObject.get(fieldName))).intValue());
                } else if (jsonObject.get(fieldName) instanceof List) {
                    HashMap map = convertToDepositMap((JSONArray) jsonObject.get(fieldName));
                    field.set(object, map);
                } else {
                    field.set(object, jsonObject.get(fieldName));
                }
            }
            return object;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*private Object convertToClassToBeBounded(JSONObject jsonObject, Class classToBeBounded) {
        try {
            Field[] fields = (classToBeBounded).getDeclaredFields();
            Object object = classToBeBounded.newInstance();
            for (Field field : fields) {
                String fieldName = field.getName();
                field.setAccessible(true);
                if (jsonObject.get(fieldName) instanceof Number) {
                    field.set(object, ((Number) (jsonObject.get(fieldName))).intValue());
                } else if (jsonObject.get(fieldName) instanceof List) {
                 //   Object list = convertToClassToBeBoundedList((JSONArray) jsonObject.get(fieldName), (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
                    HashMap<String, Deposit> map = convertToDepositMap((JSONArray) jsonObject.get(fieldName));
                    field.set(object, map);
                } else {
                    field.set(object, jsonObject.get(fieldName));
                }
            }
            return object;
        } catch (InstantiationException e) {
            e.printStackTrace();
            return null;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }
*/
   /* private List<Object> convertToClassToBeBoundedList(JSONArray jsonArray, Class classToBeBounded) {
        List list = new ArrayList();
        for (Object jO : jsonArray) {
            Field[] fields = classToBeBounded.getDeclaredFields();
            try {
                Constructor constructor = classToBeBounded.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object object = constructor.newInstance();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    field.set(object, ((JSONObject) jO).get(fieldName));
                }
                list.add(object);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return list;
    }*/

    private HashMap<String, Deposit> convertToDepositMap(JSONArray jsonArray) {
        HashMap<String, Deposit> map = new HashMap<String, Deposit>();
        for (Object jO : jsonArray) {
            Field[] fields = Deposit.class.getDeclaredFields();
            try {
                Deposit deposit = new Deposit();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    field.setAccessible(true);
                    if(field.getGenericType() == java.math.BigDecimal.class){
                        BigDecimal bigDecimal = new BigDecimal(((String)((JSONObject) jO).get(fieldName)).replace(",",""));
                        field.set(deposit, bigDecimal);
                    }else {
                        field.set(deposit, ((JSONObject) jO).get(fieldName));
                    }
                }
                map.put(deposit.getId(),deposit);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return map;
    }

    public void map(Object object, String address) {

    }
}
