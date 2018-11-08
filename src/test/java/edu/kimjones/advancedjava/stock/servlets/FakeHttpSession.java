package edu.kimjones.advancedjava.stock.servlets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * This class was created to facilitate testing of the {@code StockSearch servlet, and in particular whether or not it
 * sets an attribute as expected. Only two methods of this class are used ({@code getAttribute} and
 * {@code setAttribute}) but the rest are implemented as required by {@code HttpSession}.
 *
 * @author Kim Jones
 */
public class FakeHttpSession implements HttpSession {
    Map<String, Object> map = new HashMap<>();

    @Override
    public long getCreationTime() {
        return 0;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public ServletContext getServletContext() {
        return null;
    }

    @Override
    public void setMaxInactiveInterval(int i) {
    }

    @Override
    public int getMaxInactiveInterval() {
        return 0;
    }

    @Override
    public HttpSessionContext getSessionContext() {
        return null;
    }

    @Override
    public Object getAttribute(String name) {
        return map.get(name);
    }

    @Override
    public Object getValue(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public String[] getValueNames() {
        return new String[0];
    }

    @Override
    public void setAttribute(String name, Object value) {
        map.put(name, value);
    }

    @Override
    public void putValue(String s, Object o) {
    }

    @Override
    public void removeAttribute(String s) {
    }

    @Override
    public void removeValue(String s) {
    }

    @Override
    public void invalidate() {
    }

    @Override
    public boolean isNew() {
        return false;
    }
}
