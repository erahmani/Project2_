package DA.parse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

public class MyJSONParser implements Parser {
    public Object parse(String address, Class classToBeBounded) {
        org.json.simple.parser.JSONParser parser = new org.json.simple.parser.JSONParser();
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
                    Object list = convertToClassToBeBoundedList((JSONArray) jsonObject.get(fieldName), (Class<?>) ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0]);
                    field.set(object, list);
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

    private List<Object> convertToClassToBeBoundedList(JSONArray jsonArray, Class classToBeBounded) {
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
    }

    public void map(Object object, String address) {

    }
}
