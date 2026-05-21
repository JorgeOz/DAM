package motogp;

import java.util.*;

public class GranPremio {

    // Atributos
    private String nombreCircuito;
    private Set<MiembroEquipo> listadoParticipantes = new HashSet<>();
    private List<Piloto> parrillaSalida = new ArrayList<>();
    private List<Ingeniero> ingenierosCarrera = new ArrayList<>();
    private List<Piloto> abandonos = new ArrayList<>();
    private Map<String, Integer> rendimientoTotalAcumulado = new HashMap<>();

    // Constructor
    public GranPremio(String nombreCircuito) {
        this.nombreCircuito = nombreCircuito;
        cargaInicial();

        for (MiembroEquipo miembroEquipo : listadoParticipantes) {
            if (miembroEquipo instanceof Piloto piloto) {
                parrillaSalida.add(piloto);
            } else if (miembroEquipo instanceof Ingeniero ingeniero) {
                ingenierosCarrera.add(ingeniero);
            }
        }

        Collections.sort(parrillaSalida);
        Collections.sort(ingenierosCarrera);
    }

    // Getters & Setters
    public String getNombreCircuito() {
        return nombreCircuito;
    }

    public void setNombreCircuito(String nombreCircuito) {
        this.nombreCircuito = nombreCircuito;
    }

    public Set<MiembroEquipo> getListadoParticipantes() {
        return listadoParticipantes;
    }

    public void setListadoParticipantes(Set<MiembroEquipo> listadoParticipantes) {
        this.listadoParticipantes = listadoParticipantes;
    }

    public List<Piloto> getParrillaSalida() {
        return parrillaSalida;
    }

    public void setParrillaSalida(List<Piloto> parrillaSalida) {
        this.parrillaSalida = parrillaSalida;
    }

    public List<Ingeniero> getIngenierosCarrera() {
        return ingenierosCarrera;
    }

    public void setIngenierosCarrera(List<Ingeniero> ingenierosCarrera) {
        this.ingenierosCarrera = ingenierosCarrera;
    }

    public List<Piloto> getAbandonos() {
        return abandonos;
    }

    public void setAbandonos(List<Piloto> abandonos) {
        this.abandonos = abandonos;
    }

    public Map<String, Integer> getRendimientoTotalAcumulado() {
        return rendimientoTotalAcumulado;
    }

    public void setRendimientoTotalAcumulado(Map<String, Integer> rendimientoTotalAcumulado) {
        this.rendimientoTotalAcumulado = rendimientoTotalAcumulado;
    }

    // Muestra todos los miembros del equipo, primero los pilotos y luego los ingenieros
    public void mostrarParticipantes() {

        System.out.println("------------------");
        System.out.println("Participantes");
        System.out.println("------------------");

        for (Piloto piloto : parrillaSalida) {
            System.out.println("PILOTO - Nombre: " + piloto.getNombre() + " | Dorsal: " + piloto.getDorsal() + " | Escudería: " + piloto.getEscuderia() + " | Puntos: " + piloto.getPuntosCampeonato());
        }
        System.out.println("------------------");
        for (Ingeniero ingeniero : ingenierosCarrera) {
            System.out.println("INGENIERO - Nombre: " + ingeniero.getNombre() + " | Especialidad: " + ingeniero.getEspecialidad() + " | Experiencia: " + ingeniero.getExperiencia() + " años");
        }
    }

    // Simula una carrera de 50 vueltas
    public void simularCarrera() throws InterruptedException {

        Random random = new Random();
        String resultado;

        System.out.println("\n*************************************");
        System.out.println("I N I C I O   D E   L A   C A R R E R A");
        System.out.println("*************************************\n");

        for (int i = 1; i <= 50; i++) {
            Iterator<Piloto> iterator = parrillaSalida.iterator();

            while (iterator.hasNext()) {
                Piloto piloto = iterator.next();
                resultado = piloto.realizarTarea();

                switch (resultado) {

                    case "Accidente":
                        System.out.println("Nombre: " + piloto.getNombre() + " ha sido eliminado por accidente.");
                        abandonos.add(piloto);
                        iterator.remove();
                        break;

                    case "Entrada a Boxes":
                        Ingeniero ingeniero = ingenierosCarrera.get(random.nextInt(ingenierosCarrera.size()));
                        int mejora = ingeniero.aportarMejora();

                        if (mejora >= 0) {
                            piloto.setDesgasteNeumaticos(0.0);
                            System.out.println("Nombre: " + piloto.getNombre() + " entra a boxes. Mejora positiva (" + mejora + "). Neumáticos nuevos.");
                        } else {
                            System.out.println("ELIMINADO: " + piloto.getNombre() + " ha entrado en boxes y ha quedado eliminado.");
                            abandonos.add(piloto);
                            iterator.remove();
                        }
                        break;

                    case "Vuelta Limpia":
                        int competirVuelta = piloto.competir();

                        if (rendimientoTotalAcumulado.get(piloto.getNombre()) == null) {
                            rendimientoTotalAcumulado.put(piloto.getNombre(), competirVuelta);
                        } else {
                            int puntos = rendimientoTotalAcumulado.get(piloto.getNombre());
                            rendimientoTotalAcumulado.put(piloto.getNombre(), puntos + competirVuelta);
                        }
                        break;
                }
            }
        }

        for (Piloto pilotoEliminado : abandonos) {
            rendimientoTotalAcumulado.remove(pilotoEliminado.getNombre());
        }

        // Puntuaciones reales de MotoGP
        int[] puntosMotoGP = {25, 20, 16, 13, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        List<Map.Entry<String, Integer>> clasificacionFin = new ArrayList<>(rendimientoTotalAcumulado.entrySet());
        clasificacionFin.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        rendimientoTotalAcumulado.clear();

        for (int i = 0; i < clasificacionFin.size(); i++) {
            String nombrePiloto = clasificacionFin.get(i).getKey();
            int puntosReales = (i < puntosMotoGP.length) ? puntosMotoGP[i] : 0;
            rendimientoTotalAcumulado.put(nombrePiloto, puntosReales);
        }

        for (Piloto pilotoEliminado : abandonos) {
            rendimientoTotalAcumulado.put(pilotoEliminado.getNombre(), 0);
        }
    }

    // Muestra los resultados de la carrera
    public void mostrarResultados() {

        System.out.println("\n******************************");
        System.out.println("F I N  D E  L A  C A R R E R A");
        System.out.println("******************************");

        System.out.println("\n------------------");
        System.out.println("Pilotos en la Parrilla Final");
        System.out.println("------------------");
        for (Piloto piloto : parrillaSalida) {
            System.out.println("Nombre: " + piloto.getNombre() + " | Dorsal: " + piloto.getDorsal());
        }

        System.out.println("\n------------------");
        System.out.println("Ingenieros de Carrera");
        System.out.println("------------------");
        for (Ingeniero ingeniero : ingenierosCarrera) {
            System.out.println("Nombre: " + ingeniero.getNombre() + " | Especialidad: " + ingeniero.getEspecialidad());
        }

        System.out.println("\n------------------");
        System.out.println("Puntuaciones");
        System.out.println("------------------");
        List<Map.Entry<String, Integer>> listaPuntuaciones = new ArrayList<>(rendimientoTotalAcumulado.entrySet());
        listaPuntuaciones.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        for (Map.Entry<String, Integer> entrada : listaPuntuaciones) {
            System.out.println("Piloto: " + entrada.getKey() + " - Puntos: " + entrada.getValue());
        }

        System.out.println("\n------------------");
        System.out.println("Abandonos");
        System.out.println("------------------");
        if (abandonos.isEmpty()) {
            System.out.println("No hay abandonos, todos los pilotos han terminado la carrera.");
        } else {
            for (Piloto piloto : abandonos) {
                System.out.println("Eliminado: " + piloto.getNombre());
            }
        }
    }

    // Carga inicial de pilotos e ingenieros
    public void cargaInicial() {

        // --- 15 Pilotos (MotoGP) ---
        listadoParticipantes.add(new Piloto("Jorge Martín", 26, 5000000.0, 89, "Pramac Racing", 508));
        listadoParticipantes.add(new Piloto("Francesco Bagnaia", 27, 6000000.0, 1, "Ducati Lenovo", 498));
        listadoParticipantes.add(new Piloto("Marc Márquez", 31, 10000000.0, 93, "Gresini Racing", 392));
        listadoParticipantes.add(new Piloto("Enea Bastianini", 26, 3000000.0, 23, "Ducati Lenovo", 386));
        listadoParticipantes.add(new Piloto("Brad Binder", 28, 2500000.0, 33, "KTM Factory", 217));
        listadoParticipantes.add(new Piloto("Pedro Acosta", 19, 1200000.0, 31, "GasGas Tech3", 215));
        listadoParticipantes.add(new Piloto("Maverick Viñales", 29, 4000000.0, 12, "Aprilia Racing", 190));
        listadoParticipantes.add(new Piloto("Aleix Espargaró", 34, 3500000.0, 41, "Aprilia Racing", 163));
        listadoParticipantes.add(new Piloto("Fabio Di Giannantonio", 25, 1200000.0, 49, "VR46 Racing", 165));
        listadoParticipantes.add(new Piloto("Franco Morbidelli", 29, 1500000.0, 21, "Pramac Racing", 173));
        listadoParticipantes.add(new Piloto("Marco Bezzecchi", 25, 2000000.0, 72, "VR46 Racing", 153));
        listadoParticipantes.add(new Piloto("Alex Márquez", 28, 1800000.0, 73, "Gresini Racing", 173));
        listadoParticipantes.add(new Piloto("Fabio Quartararo", 25, 8000000.0, 20, "Yamaha Factory", 113));
        listadoParticipantes.add(new Piloto("Jack Miller", 29, 2000000.0, 43, "KTM Factory", 87));
        listadoParticipantes.add(new Piloto("Miguel Oliveira", 29, 1500000.0, 88, "Trackhouse Racing", 75));

        // --- 5 Ingenieros ---
        listadoParticipantes.add(new Ingeniero("Gigi Dall'Igna", 58, 2500000.0, "Aerodinámica", "Ducati", 35));
        listadoParticipantes.add(new Ingeniero("Santi Hernández", 48, 950000.0, "Telemetría", "Gresini", 20));
        listadoParticipantes.add(new Ingeniero("Christian Gabarrini", 51, 980000.0, "Electrónica", "Ducati", 25));
        listadoParticipantes.add(new Ingeniero("Alberto Puig", 57, 1100000.0, "Estrategia", "Honda", 30));
        listadoParticipantes.add(new Ingeniero("Massimo Rivola", 52, 1050000.0, "Chasis", "Aprilia", 22));
    }

}

