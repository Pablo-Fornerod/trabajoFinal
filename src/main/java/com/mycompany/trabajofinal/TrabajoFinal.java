/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.trabajofinal;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Pablito
 */
//https://github.com/Pablo-Fornerod/trabajoFinal
public class TrabajoFinal {
    public static void validarInscripcion() throws SQLException, IOException {
        System.out.println("Legajo:");
        int legajo = sc.nextInt();
        
        System.out.println("Materia a inscribirse:");
        String nombre = sc.next();
        
        Inscripcion inscripcion = new Inscripcion();
        boolean aprobada = inscripcion.aprobada(buscarAlumno(legajo), buscarMateria(nombre));
        
    }
    
    public static Materia buscarMateria(String nombre) throws SQLException, JsonProcessingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        
        conexion.estableceConexion();
        Statement stmt = conexion.conectar.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM materias WHERE nombre=\""+nombre+"\"");
        rs.next();
        Materia materia = new Materia(rs.getString("nombre"));
        materia.setCorrelativas(objectMapper.readValue(objectMapper.writeValueAsString(rs.getString("correlativas")), ArrayList.class));
        
        conexion.cerrarConexion();
        
        return materia;
    }

    public static Alumno buscarAlumno(int legajo) throws SQLException, JsonProcessingException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        conexion.estableceConexion();
        Statement stmt = conexion.conectar.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM alumnos WHERE legajo=" + legajo + ";");
        rs.next();
        Alumno alumno = new Alumno(rs.getString("nombre"), rs.getInt("legajo"));

        alumno.setMateriasAprobadas(mapper.readValue(rs.getString("materiasAprobadas"), ArrayList.class));

        conexion.cerrarConexion();

        return alumno;
    }

    private static final Conexion conexion = new Conexion();

    private static final Scanner sc = new Scanner(System.in);

    public static int opciones() {

        System.out.println("1. Ingresar.");
        System.out.println("2. Modificar.");
        System.out.println("3. Eliminar.");
        int opcion = sc.nextInt();
        return opcion;
    }

    public static void main(String[] args) throws SQLException, JsonProcessingException, IOException {
        
        ObjectMapper objectMapper = new ObjectMapper();
        conexion.estableceConexion();
        Statement stmt = conexion.conectar.createStatement();
        int input;

        do {

            System.out.println("Menu de opciones");
            System.out.println("1. Alumno.");
            System.out.println("2. Materia.");
            System.out.println("3. Inscripcion.");

            System.out.println("4.Salir");

            input = sc.nextInt();
            switch (input) {
                case 1:

                    System.out.println("------Alumno------");
                    System.out.println("¿Que desea hacer?");
                    Alumno alumno = new Alumno();
                    Alumno alumnoB;
                    
                    switch (opciones()) {
                        case 1:
                            System.out.println("Nombre:");
                            String nombre = sc.next();
                            String legajo;
                            do {
                                System.out.println("Recuerde que el Legajo debe tener 5 digitos...");
                                System.out.println("Legajo: ");

                                legajo = sc.next();
                            } while (!legajo.matches("[0-9]{5}"));

                            int legajoint = Integer.parseInt(legajo);//Legajo se vuelve int
                            alumno.setNombre(nombre);
                            alumno.setLegajo(legajoint);
                            String aprobadas;
                            do {
                                System.out.println("Materias aprobadas:");
                                System.out.println("Corta con \"0\"");

                                aprobadas = sc.next();
                                if (!aprobadas.equals("0")) {
                                    alumno.materiasAprobadas.add(aprobadas);
                                }

                            } while (!aprobadas.equals("0"));
                            String jsonText = objectMapper.writeValueAsString(alumno.materiasAprobadas);

                            stmt.executeUpdate("INSERT INTO alumnos VALUES (\"" + nombre + "\"," + legajo + ",'[" + jsonText + "]')");
                            break;

                        case 2:
                            System.out.println("Ingrese el Legajo del Alumno: ");
                            int legajoB = sc.nextInt();
                            alumnoB = buscarAlumno(legajoB);
                            System.out.println(alumnoB.toString());
                            System.out.println("¿Que desea modificar?");
                            System.out.println("1. Nombre");
                            System.out.println("2. Legajo");
                            System.out.println("3. Materias Aprobadas");

                            switch (sc.nextInt()) {
                                case 1:
                                    System.out.println("Ingrese el nuevo nombre:");
                                    alumnoB.setNombre(sc.next());
                                    stmt.execute("UPDATE alumnos SET nombre= '" + alumnoB.getNombre() + "' WHERE legajo=" + legajoB + ";");
                                    break;
                                case 2:
                                    System.out.println("Ingrese el nuevo legajo:");
                                    alumnoB.setLegajo(sc.nextInt());
                                    stmt.execute("UPDATE alumnos SET legajo= '" + alumnoB.getLegajo() + "' WHERE legajo=" + legajoB + ";");
                                    break;
                                case 3:
                                    System.out.println("Nombre de la Materia Aprobada:");
                                    alumnoB.materiasAprobadas.add(sc.next());
                                    String jsonTextB = objectMapper.writeValueAsString(alumnoB.materiasAprobadas);
                                    stmt.execute("UPDATE alumnos SET materiasAprobadas= '[" + jsonTextB + "]' WHERE legajo=" + alumnoB.getLegajo() + ";");
                                    break;
                                    
                            }

                            break;
                        case 3:
                            System.out.println("Ingrese el legajo del Alumno a eliminar:");
                            alumnoB = buscarAlumno(sc.nextInt());
                            stmt.execute("DELETE FROM alumnos WHERE legajo="+alumnoB.getLegajo()+";");
                            break;
                            
                    }
                    break;

                case 2:
                    Materia materia = new Materia();
                    System.out.println("------Materia------");
                    System.out.println("1. Ingresar");
                    System.out.println("2. Eliminar");
                    String correlativa;
                    switch(sc.nextInt()){
                        case 1:
                            System.out.println("Nombre:");
                            String nombre = sc.next();
                            materia.setNombre(nombre);
                            
                            do {
                                System.out.println("Correlativas:");
                                System.out.println("Corta con \"0\"");
                                correlativa = sc.next();
                            if (!correlativa.equals("0")) {
                                    materia.correlativas.add(correlativa);
                                }
                            }while (!correlativa.equals("0"));
                            
                            String jsonTextC = objectMapper.writeValueAsString(materia.correlativas);
                            stmt.execute("INSERT INTO materias VALUE (\""+nombre+"\", '["+jsonTextC+"]')");
                            break;

                        case 2:
                            System.out.println("Nombre:");
                            stmt.execute("DELETE FROM materias WHERE nombre='"+sc.next()+"'");
                            break;
                    }
                    break;

                case 3:
                    System.out.println("------Inscripcion------");
                    validarInscripcion();
                    break;
                case 4:
                    conexion.cerrarConexion();
                    break;
            }

        } while (input != 4);
    }
}
