/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;

/**
 *
 * @author Trong Phu
 */
public abstract class  QLCHBG_DAO<E,K> {
    
    abstract public int insert(E entity);
    abstract public int update(K key,E entity);
    abstract public int delete(K key);
    abstract public List<E> selectAll();
}
