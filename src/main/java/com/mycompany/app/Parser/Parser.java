package com.mycompany.app.Parser;

public interface Parser {
    Object parse(String address);

    void map(Object object);
}
