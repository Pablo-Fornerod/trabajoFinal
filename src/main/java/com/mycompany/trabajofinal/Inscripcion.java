/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.trabajofinal;

import java.util.Date;

/**
 *
 * @author stere
 */
public class Inscripcion {
    Materia materia;
    Alumno alumno;
    
    Date fecha = new Date();
    
    boolean aprobada;

    public Inscripcion() {
    }

    public Inscripcion(Materia materia, Alumno alumno, boolean aprobada) {
        this.materia = materia;
        this.alumno = alumno;
        this.aprobada = aprobada;
    }

    public Materia getMateria() {
        return materia;
    }

    public void setMateria(Materia materia) {
        this.materia = materia;
    }

    public Alumno getAlumno() {
        return alumno;
    }

    public void setAlumno(Alumno alumno) {
        this.alumno = alumno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public boolean isAprobada() {
        return aprobada;
    }

    public void setAprobada(boolean aprobada) {
        this.aprobada = aprobada;
    }

    @Override
    public String toString() {
        return "Inscripcion{" + "materia=" + materia + ", alumno=" + alumno + ", fecha=" + fecha + ", aprobada=" + aprobada + '}';
    }
    
    
    public boolean aprobada(Alumno alumno, Materia materia){
        if(alumno.materiasAprobadas.contains(materia.nombre)){
            System.out.println("La materia ya esta aprobada");
            this.aprobada = false;
        }else{
            if (alumno.materiasAprobadas.retainAll(materia.correlativas)){
                System.out.println("Se puede inscribir");
                this.aprobada = true;
            }else{
                System.out.println("No se puede inscribir");
                this.aprobada = false;
            }
        }
        
        return aprobada;
    }
    
}
