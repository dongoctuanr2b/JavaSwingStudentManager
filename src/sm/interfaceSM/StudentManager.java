/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sm.interfaceSM;

import java.util.List;

/**
 *
 * @author Ngoc Tuan
 */
public interface StudentManager<T> {

    List<T> getAll();

    boolean save(T t);

    boolean update(T t);

    boolean delete(String s);

    List<T> search(String s);

    int count();
}
