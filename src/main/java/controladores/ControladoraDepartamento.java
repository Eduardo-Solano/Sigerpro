/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import entidad.Departamento;
import entidad.Docente;
import java.util.ArrayList;
import persistencia.ControladoraPersistencia;

/**
 *
 * @author es982
 */
    public class ControladoraDepartamento {

        ControladoraPersistencia controlPersis = new ControladoraPersistencia();
        //ControladoraPersona controlP = new ControladoraPersona();

        public void guardarDepartamento(Departamento dep) {
            controlPersis.guardarDepartamento(dep);
        }

        public void eliminarDepartamento(Integer id) {
            controlPersis.eliminarDepartamento(id);
        }

        public void editarDepartamento(Departamento dep) {
            controlPersis.editarDepartamento(dep);
        }

        public Departamento buscarPorId(Integer id) {
            return controlPersis.buscarDepartamentoPorID(id);
        }

        public ArrayList<Departamento> listarDepartamentos() {
            return controlPersis.listarDepartamentos();
        }

        public Departamento buscarDepartamentoPorNombre(String depart) {
            Departamento dpa=new Departamento();
            ArrayList<Departamento> listaDepa=listarDepartamentos();
            for(Departamento dp:listaDepa){
                if(dp.getNombreDepartamento().equals(depart)){
                    dpa=dp;
                    return dpa;
                }
            }
            return null;
        }



    }
