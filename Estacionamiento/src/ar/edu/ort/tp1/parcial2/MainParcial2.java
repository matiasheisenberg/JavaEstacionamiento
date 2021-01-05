/**
 * 
 */
package ar.edu.ort.tp1.parcial2;

import java.util.ArrayList;

import ar.edu.ort.tp1.parcial2.entidades.Estacionamiento;
import ar.edu.ort.tp1.parcial2.entidades.Hora;
import ar.edu.ort.tp1.parcial2.entidades.TipoVehiculo;
import ar.edu.ort.tp1.parcial2.exceptions.EstacionamientoException;

/**
 * Clase Main del examen, debe ser ejecutada y mostrar la salida especificada
 * TODO: Completar el tratamiento de excepciones
 */
public class MainParcial2 {

	//defino los precios por hora de los autos y motos
	private static final int PRECIO_MOTOCICLETAS_POR_HORA = 30;
	private static final int PRECIO_AUTOS_POR_HORA = 60;
	//defino la capacidad de las pilas
	private static final int CAPACIDAD_MOTOCICLETAS = 5;
	private static final int CAPACIDAD_AUTOS = 6;

	public static void main(String[] args) {
//cre un nuevo test
		MainParcial2 test = new MainParcial2();
		//coloco una excepcion por si estan mal los datos por parametros
		try {
			Estacionamiento e = new Estacionamiento(CAPACIDAD_AUTOS, CAPACIDAD_MOTOCICLETAS, PRECIO_AUTOS_POR_HORA,
					PRECIO_MOTOCICLETAS_POR_HORA);

			test.estacionarAutos(e);

			System.out.println("-------------------------------------------------------------");

			test.estacionarMotos(e);

			System.out.println("-------------------------------------------------------------");

			test.retirarAutos(e);

			System.out.println("-------------------------------------------------------------");

			test.retirarMotos(e);

			System.out.println("-------------------------------------------------------------");

			e.finalizarDia();
		}//reviso si agarro una excepcion 
		catch (RuntimeException e) {
			System.out.println("Hubo un error " + e.getMessage());

		}
		//fin del main
	}

	//creo un arraylist de motos, agrego elementos y a la vez los creo
	private void estacionarMotos(Estacionamiento e) {
		ArrayList<VehiculoDTO> motos = new ArrayList<MainParcial2.VehiculoDTO>();

		motos.add(new VehiculoDTO("324ADS", 10, 55));
		motos.add(new VehiculoDTO("654grt", 0, 87));
		motos.add(new VehiculoDTO("444fef", 24, 0));
		motos.add(new VehiculoDTO("432htf", 15, 25));
		motos.add(new VehiculoDTO("2ff444", 12, 5));
		motos.add(new VehiculoDTO("675BGf", 9, 15));
		motos.add(new VehiculoDTO("894NNC", 7, 10));
		motos.add(new VehiculoDTO("321HRS", 14, 15));
//una vez creadas las motos las estaciono, coloco excepcion por si no se pueden estacionar 
	//	reocorro el arraylist de motos tratando cada elemento como un vehiculo porque el estacionamiento estaciona vehiculos
		for (VehiculoDTO vehiculoDTO : motos) {
			try {
				//llamo al metodo estacionar de Estacionamiento y le paso el tipo de vehiculo, patente y hora de entrada
				e.estacionar(TipoVehiculo.MOTOCICLETA, vehiculoDTO.getPatente(), vehiculoDTO.getHora());
			} catch (EstacionamientoException ex) {
				System.out.println("No se pudo estacionar el veh�culo - " + ex.getMessage());
			}
		}

	}

	private void estacionarAutos(Estacionamiento e) {

		ArrayList<VehiculoDTO> autos = new ArrayList<MainParcial2.VehiculoDTO>();

		autos.add(new VehiculoDTO("fff444", 10, 0));
		autos.add(new VehiculoDTO("fff424", 0, 77));
		autos.add(new VehiculoDTO("fef444", 27, 0));
		autos.add(new VehiculoDTO("ogy384", 12, 5));
		autos.add(new VehiculoDTO("2ff444", 12, 5));
		autos.add(new VehiculoDTO("BGf444", 20, 55));
		autos.add(new VehiculoDTO("NNC894", 6, 25));
		autos.add(new VehiculoDTO("HRS875", 7, 25));
		autos.add(new VehiculoDTO("LTC824", 11, 25));
		autos.add(new VehiculoDTO("WRG833", 14, 45));

		for (VehiculoDTO vehiculoDTO : autos) {
			try {
				e.estacionar(TipoVehiculo.AUTO, vehiculoDTO.getPatente(), vehiculoDTO.getHora());
			} catch (EstacionamientoException ex) {
				System.out.println("No se pudo estacionar el veh�culo - " + ex.getMessage());
			}
		}

	}

	private void retirarAutos(Estacionamiento e) {
		float importe;
		//para retirar los autos los guardo en un arraylist
		ArrayList<VehiculoDTO> autos = new ArrayList<MainParcial2.VehiculoDTO>();
        //creo los autos que retiro
		autos.add(new VehiculoDTO("BGf444", 23, 10));
		autos.add(new VehiculoDTO("BGf444", 23, 25));
		autos.add(new VehiculoDTO("LTC824", 2, 25));
		autos.add(new VehiculoDTO("NNC894", 14, 13));
		autos.add(new VehiculoDTO("HRS875", 18, 40));

		for (VehiculoDTO vehiculoDTO : autos) {

			try {
				//e.retirar lo que hace es fijarse si el vehiculo se encontraba estacionado, retirarlo y calcular el importe
				importe = e.retirar(vehiculoDTO.getPatente(), vehiculoDTO.getHora());
				System.out.printf("Se retir� correctamente el veh�culo patente %s, debe abonar $ %4.2f \n",
						vehiculoDTO.getPatente(), importe);
			} catch (EstacionamientoException ex) {
				System.out.println("No se pudo retirar el veh�culo - " + ex.getMessage());
			}
		}

	}

	private void retirarMotos(Estacionamiento e) {
		float importe;
		ArrayList<VehiculoDTO> motos = new ArrayList<MainParcial2.VehiculoDTO>();

		motos.add(new VehiculoDTO("432htf", 18, 10));
		motos.add(new VehiculoDTO("432htf", 18, 25));
		motos.add(new VehiculoDTO("675BGf", 20, 05));
		motos.add(new VehiculoDTO("321HRS", 17, 54));

		for (VehiculoDTO vehiculoDTO : motos) {
			try {
				importe = e.retirar(vehiculoDTO.getPatente(), vehiculoDTO.getHora());
				System.out.printf("Se retir� correctamente el veh�culo patente %s, debe abonar $ %4.2f \n",
						vehiculoDTO.getPatente(), importe);
			} catch (EstacionamientoException ex) {
				System.out.println("No se pudo retirar el veh�culo - " + ex.getMessage());
			}
		}
	}

	
	//clase para crear vehiculis estacionados, mots o autos
	//cuando el metodo de estacionamiento "estacionar" se ejecuta, crea el vehiculo en especifico y lo guarda en la pila
	private class VehiculoDTO {

		private String patente;
		private Hora hora;

		/**
		 * @param patente
		 * @param hora
		 * @param minuto
		 */
		public VehiculoDTO(String patente, int hora, int minuto) {
			super();
			this.patente = patente;
			this.hora = new Hora(hora, minuto);
		}

		/**
		 * @return the patente
		 */
		public String getPatente() {
			return patente;
		}

		/**
		 * @return the hora
		 */
		public Hora getHora() {
			return hora;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "VehiculoDTO [patente=" + patente + ", hora=" + hora + "]";
		}

	}
}
