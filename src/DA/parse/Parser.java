package DA.parse;

interface Parser{
    Object parse(String address, Class classToBeBounded);

    void map(Object object, String address);
}
