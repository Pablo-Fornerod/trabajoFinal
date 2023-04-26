/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinal;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stere
 */
public class Materia {
    String nombre;
    
    List<String> correlativas = new ArrayList<>();

    public Materia() {
    }

    public Materia(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getCorrelativas() {
        return correlativas;
    }

    public void setCorrelativas(List<String> correlativas) {
        this.correlativas = correlativas;
    }

    @Override
    public String toString() {
        return "Materia{" + "nombre=" + nombre + ", correlativas=" + correlativas + '}';
    }
    
}
