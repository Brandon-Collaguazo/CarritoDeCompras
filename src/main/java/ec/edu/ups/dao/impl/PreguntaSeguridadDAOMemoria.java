package ec.edu.ups.dao.impl;

import ec.edu.ups.dao.PreguntaSeguridadDAO;
import ec.edu.ups.modelo.PreguntaSeguridad;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PreguntaSeguridadDAOMemoria implements PreguntaSeguridadDAO {
    private List<PreguntaSeguridad> preguntas;
    private static int nextId = 1;

    public PreguntaSeguridadDAOMemoria() {
        preguntas = new ArrayList<>();
        inicializarBancoPreguntas();
    }

    private void inicializarBancoPreguntas() {
        crear(new PreguntaSeguridad(nextId++, "¿Cuál es el nombre de tu primera mascota?"));
        crear(new PreguntaSeguridad(nextId++, "¿Cuál es el apellido de soltera de tu madre?"));
        crear(new PreguntaSeguridad(nextId++, "¿En qué ciudad naciste?"));
        crear(new PreguntaSeguridad(nextId++, "¿Cuál es tu color favorito?"));
        crear(new PreguntaSeguridad(nextId++, "¿Nombre de tu mejor amigo de la infancia?"));
        crear(new PreguntaSeguridad(nextId++, "¿Cuál fue tu primer trabajo?"));
        crear(new PreguntaSeguridad(nextId++, "¿Modelo de tu primer vehículo?"));
        crear(new PreguntaSeguridad(nextId++, "¿Nombre de tu escuela primaria?"));
        crear(new PreguntaSeguridad(nextId++, "¿Cuál es tu libro favorito?"));
        crear(new PreguntaSeguridad(nextId++, "¿Personaje histórico que más admiras?"));
    }

    @Override
    public void crear(PreguntaSeguridad pregunta) {
        pregunta.setId(nextId++);
        preguntas.add(pregunta);
    }

    @Override
    public PreguntaSeguridad buscarPorId(int id) {
        for(PreguntaSeguridad pregunta : preguntas) {
            if(pregunta.getId() == id) {
                return pregunta;
            }
        }
        return null;
    }

    @Override
    public List<PreguntaSeguridad> listarTodas() {
        return preguntas;
    }

    @Override
    public void actualizar(PreguntaSeguridad pregunta) {
        for (int i = 0; i < preguntas.size(); i++) {
            if (preguntas.get(i).getId() == pregunta.getId()) {
                preguntas.set(i, pregunta);
            }
        }
    }

    @Override
    public void eliminar(int id) {
        Iterator<PreguntaSeguridad> iterator = preguntas.iterator();
        while(iterator.hasNext()) {
            PreguntaSeguridad pregunta = iterator.next();
            if(pregunta.getId() == id) {
                iterator.remove();
            }
        }
    }
}
