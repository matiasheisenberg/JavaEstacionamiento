/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

import java.util.ArrayList;

import ar.edu.ort.tp1.parcial2.exceptions.EstacionamientoException;
import ar.edu.ort.tp1.parcial2.tad.Pila;
import ar.edu.ort.tp1.parcial2.tad.PilaAL;

/**
 * Entidad que engloba el funcionamiento de un estacionamiento de autos y
 * motocicletas. cada tipo de veh�culo ser� estacionado de la forma determinada
 * y en base a su capacidad asignada al momento de la creaci�n del
 * estacionamiento.
 * 
 * Se debe tene en cuenta que el funcionamiento del estacionamiento para cada
 * tipo de veh�culo es del tipo LIFO Utilizar la implementaci�n de TAD que
 * corresponda
 */
public class Estacionamiento {

//TODO COMPLETAR
	//creo las dos pilas
	private Pila<Vehiculo> tadAuto;
	private Pila<Vehiculo> tadMoto;
	//arraylist de vehiculos estacionados
	//este arraylist es para guardar los autos que ingresaron y se fueron, es decir, que fueron estacionados correctamente y fueron retirados
	private ArrayList<RegistroVehiculoEstacionado> autosEstacionados;
	private ArrayList<RegistroVehiculoEstacionado> motosEstacionadas;
	//recaudacion total de cada tipo
	private float recTotAutosEstacionados;
	private float recTotMotosEstacionadas;
   //precio de cada tipo por hora
	private float precioMoto;
	private float precioAuto;

	/**
	 * Constructor del estacionamiento, recibe las capacidades de autos y motos y
	 * los precios por hora completas.
	 * 
	 * @param capacidadAutos
	 * @param capacidadMotocicletas
	 * @param precioAutosPorHora
	 * @param precioMotocicletasPorHora
	 */
	public Estacionamiento(int capacidadAutos, int capacidadMotocicletas, float precioAutosPorHora,
			float precioMotocicletasPorHora) {
		this.tadAuto = new PilaAL<>(capacidadAutos);
		this.tadMoto = new PilaAL<>(capacidadMotocicletas);

		this.setPrecioAutosPorHora(precioAutosPorHora);
		this.setPrecioMotocicletasPorHora(precioMotocicletasPorHora);

		this.autosEstacionados = new ArrayList<>();
		this.motosEstacionadas = new ArrayList<>();
		this.recTotAutosEstacionados = 0;
		this.recTotMotosEstacionadas = 0;
	}

	/**
	 * Setea el precio de la hora de auto, debe ser mayor a 0
	 * 
	 * @param precioAutos the precioAutos to set
	 */
	//seteo los precios por hora de los autos, esto se hace en el constructor
	private void setPrecioAutosPorHora(float precioAutosPorHora) throws EstacionamientoException {
		if (precioAutosPorHora <= 0) {
			throw new EstacionamientoException("Precio de auto inv�lido");
		}
		this.precioAuto = precioAutosPorHora;
	}

	/**
	 * Setea el precio de la hora de la motocicleta , debe ser mayor a 0
	 * 
	 * @param precioMotocicletas the precioMotocicletas to set
	 */
	//seteo los precios por hora de las motos, esto se hace en el constructor
	private void setPrecioMotocicletasPorHora(float precioMotocicletasPorHora) throws EstacionamientoException {
		if (precioMotocicletasPorHora <= 0) {
			throw new EstacionamientoException("Precio de motocicleta inv�lido");
		}
		this.precioMoto = precioMotocicletasPorHora;
	}

	/**
	 * Permite estacionar un veh�culo en el estacionamiento. La patente debe ser
	 * v�lida segun el tipo de veh�culo el horario de ingreso debe ser v�lido
	 * 
	 * @param tipo    de veh�culo
	 * @param patente del veh�culo
	 * @param hora    de ingreso
	 */
	//para estacionar el vehiculo verifico que tipo es, asi se en que pila meterlo
	//
	public void estacionar(TipoVehiculo tipo, String patente, Hora hora) throws EstacionamientoException {

		if (tipo == TipoVehiculo.MOTOCICLETA) {
			this.estacionarMoto(patente, hora);
			System.out.println("Se estacion� correctamente la motocicleta patente: " + patente);
		} else if (tipo == TipoVehiculo.AUTO) {
			this.estacionarAuto(patente, hora);
			System.out.println("Se estacion� correctamente el auto patente: " + patente);
		} else {
			throw new EstacionamientoException("Tipo de veh�culo indicado inv�lido");
		}
	}

	/**
	 * Permite estacionar una motocicleta.
	 * 
	 * @param patente de la moto
	 * @param hora    del ingreso
	 */
	//estacionar moto necesita la patente y la hora de ingreso
	//con esos datos la creamos y la pusheamos en la pila
	private void estacionarMoto(String patente, Hora hora) throws EstacionamientoException {

		Motocicleta moto = new Motocicleta(patente, hora, this.precioMoto);

		try {
			this.tadMoto.push(moto);
		} catch (RuntimeException e) {
			throw new EstacionamientoException(e.getMessage());
		}

	}

	/**
	 * permite estacionar un auto
	 * 
	 * @param patente del auto
	 * @param hora    del Ingreso
	 */
	private void estacionarAuto(String patente, Hora hora) throws EstacionamientoException {
		Auto auto = new Auto(patente, hora, this.precioAuto);

		try {
			this.tadAuto.push(auto);
		} catch (RuntimeException e) {
			throw new EstacionamientoException(e.getMessage());
		}

	}

	/**
	 * Retira un veh�culo del estacionamiento. debe detectar el tipo de veh�culo en
	 * base a su patente (ver diferencias entre la patente de los autos y las motos)
	 * el horario de egreso debe ser v�lido si el veh�culo no est� estacionado debe
	 * lanzar una excepci�n.
	 * 
	 * @param patente
	 * @param horaEgreso
	 * @param minutosEgreso
	 * @return
	 */
	
	//me mandan por parametro la pantente y la hora de egreso
	public float retirar(String patente, Hora hora) throws EstacionamientoException {

		/*
		 * Detecta el tipo de veh�culo usando Regular Expressions
		 */
		//detecto el tipo de vehiculo segun patente
		TipoVehiculo tipo = patente.matches(TipoVehiculo.MOTOCICLETA.getRegex()) ? TipoVehiculo.MOTOCICLETA
				: TipoVehiculo.AUTO;
		//ya tengo el tipo de vehiculo para saber en que pila buscarlo
		float importe = 0;
		//pongo el importe a pagar en cero
		Vehiculo v = null;
		switch (tipo) {
		//en caso de que sea auto invoco al metodo retirar, este se fija si el auto esta, sino tira excepcion
		case AUTO:
			v = this.retirar(patente, tadAuto);
			//llamo por polimorfismo al calcular importe co la hora de egreso para que lo calcule solo
			importe = v.calcularImporte(hora);
			//seeo el precio de diez minutos al auto
			((Auto)v).setPrecioDiezMinutos(34);
			//como ya lo saque de la pila, lo creo y agrego al arraylist para guardarlo como un registro
			this.autosEstacionados.add(new RegistroVehiculoEstacionado(v.getPatente(), importe));
			this.recTotAutosEstacionados+= importe;
			break;

		case MOTOCICLETA:
			v = this.retirar(patente, tadMoto);
			importe = v.calcularImporte(hora);
			this.motosEstacionadas.add(new RegistroVehiculoEstacionado(v.getPatente(), importe));
			this.recTotMotosEstacionadas+= importe;
			break;
		}
		return importe;
	}

	/**
	 * Retira el veh�culo de la TAD especificada
	 * 
	 * @param patente a retirar
	 * @param tad     REEMPLAZAR EL TIPO DE ESTE PARAMETRO POR LA TAD QUE
	 *                CORRESPONDA
	 * @throws EstacionamientoException Cuando no se encuentra el veh�culo
	 * 
	 */
	private Vehiculo retirar(String patente, Pila<Vehiculo> tad) throws EstacionamientoException {
		
		Vehiculo encontrado = null;
		Pila<Vehiculo> pilaAux = new PilaAL<>();
		while (!tad.isEmpty() && encontrado == null) {
			Vehiculo v = tad.pop();
			if (v.getPatente().equals(patente)) {
				encontrado = v;
			} else {
				pilaAux.push(v);
			}
			
		}

		while (!pilaAux.isEmpty()) {
			tad.push(pilaAux.pop());
		}
		
		if (encontrado == null) {
			throw new EstacionamientoException("Veh�culo con patente " + patente + " No encontrado");
		}

		return  encontrado;
		
	}

	/**
	 * Muestra por pantalla el resumen del final del d�a (cantidad de autos y motos
	 * estacionados, total recaudado para autos y para motos y los listados de los
	 * autos y motos estacionados durante el d�a con el importe abonado por cada
	 * uno)
	 */
	public void finalizarDia() {
		// TODO DESCOMENTAR Y COMPLETAR
		System.out.println("\r\n--------- Resumen final del d�a --------------\r\n");
		System.out.printf("Se han estacionado %d autos\n", this.autosEstacionados.size());
		System.out.printf("Se han estacionado %d motocicletas\n", this.motosEstacionadas.size());
		System.out.println("\r\n----------------------------------------------");
		System.out.printf("Por estacionamiento de autos se ha recaudado $ %4.2f\n", 
				this.recTotAutosEstacionados);
		System.out.printf("Por estacionamiento de motocicletas se ha recaudado $ %4.2f\n", 
				this.recTotMotosEstacionadas);
		System.out.println("\r\n----------------------------------------------");
		System.out.println("Listado de autos estacionados");
		this.mostrarListadoFinalDia(this.autosEstacionados);
		System.out.println("\r\n----------------------------------------------");
		System.out.println("Listado de motocicletas estacionados");
		this.mostrarListadoFinalDia(this.motosEstacionadas);
		System.out.println("\r\n--------- Fin del reporte resumen final del d�a --------------\r\n");
	}

	/**
	 * @param listado a mostrar
	 */
	//recibo por parametro un arraylist
	//primero el de autos y leugo el de motos estacionadas
	private void mostrarListadoFinalDia(ArrayList<RegistroVehiculoEstacionado> listado) {

		for (RegistroVehiculoEstacionado registro : listado) {
			System.out.printf("- Veh�culo patente: %s - Importe abonado: %4.2f\n", registro.getPatente(),
					registro.getImporte());
		}
	}

	/**
	 * Inner class que posee los datos de un auto que fue estacionado durante el d�a
	 *
	 */
	private class RegistroVehiculoEstacionado {

		private String patente;
		private float importe;

		/**
		 * @param patente
		 * @param importe
		 */
		public RegistroVehiculoEstacionado(String patente, float importe) {
			this.patente = patente;
			this.importe = importe;
		}

		/**
		 * @return the patente
		 */
		public String getPatente() {
			return patente;
		}

		/**
		 * @return the importe
		 */
		public float getImporte() {
			return importe;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			return "RegistroVehiculoEstacionado [patente=" + patente + ", importe=" + importe + "]";
		}

	}
}
