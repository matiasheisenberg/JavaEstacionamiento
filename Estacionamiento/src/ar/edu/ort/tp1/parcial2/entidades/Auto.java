/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

import ar.edu.ort.tp1.parcial2.exceptions.EstacionamientoException;

/**
 * Representa un auto a ser estacionado. El precio de los autos se fracciona
 * cada 10 minutos.
 */
public class Auto extends Vehiculo {

	private float precioDiezMinutos;
	/**
	 * Constructor de auto
	 * 
	 * @param patente       patente del auto
	 * @param horaIngreso   hora del ingreso
	 * @param precioPorHora precio de la hora completa de los autos
	 */
	//constructor con patente, hora de ingreso y precio por hora
	public Auto(String patente, Hora hora, float precioPorHora) {
		super(patente, hora);
		//seteo el valor de 10 min sabiendo el de 60 min
		this.setPrecioDiezMinutos(precioPorHora);
	}
	public void setPrecioDiezMinutos(float precioPorHora) {
		//precio 10 = 1 hora / precio 10 por hora de un vehiculo
		this.precioDiezMinutos = precioPorHora / Vehiculo.DIEZ_MINUTOS_POR_HORA;
	}

	/**
	 * Valida que una patente sea v�lida para el tipo de vehiculo auto. debe tener
	 * formato de tres letras y tres n�meros por ejemplo 'DSA182' o dos letras tres
	 * numeros y dos letras ejemplo 'AB123CD'
	 */
	@Override
	//el throws significa que el metodo tira una excepcion
	public void validarPatente(String patente) throws EstacionamientoException {

		// FORMA DE VERIFICAR QUE LA PATENTE TIENE EN FORMATO CORRECTO
		//devuelve un boolean si es compatible o no
		if (!patente.matches(TipoVehiculo.AUTO.getRegex())) {
			throw new EstacionamientoException("Patente err�nea");
		}
	}

	/**
	 * Calcula el importe de la estadia del auto, recibiendo el horario de salida.
	 * Debe redondearse a 10 minutos la cantidad de minutos de la estad�a, si la
	 * duraci�n da 7 minutos, se deben cobrar 10.
	 */
	@Override
	public float calcularImporte(Hora horaEgreso) throws EstacionamientoException {
//llamo al metodo de vehiculo para verificar que la hora de egreso sea posterior a la de ingreso
		this.validarHoraEgreso(horaEgreso);
		//calcula la cantidad de tiempo que estuvo, como un horaEntrada - horaSalida
		Hora hora = calcularTiempoEstadia(horaEgreso);
		//pongo el importe en cero
		float importe = 0;
		//le paso una hora y me las devuelve en int
		int cantHoras =hora.getHora();
		//ya tengo las horas pero no los minutos, le paso por parametro
		int minutos = this.redondear(hora.getMinuto(), DIEZ);
		
		int horasEnMinutos = cantHoras * HORA;
		
		int minutosTotales = horasEnMinutos + minutos;
		
		importe = (minutosTotales / DIEZ) * this.precioDiezMinutos;  
		
		return importe;
	}

}
