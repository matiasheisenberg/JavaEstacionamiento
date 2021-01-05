/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

import ar.edu.ort.tp1.parcial2.exceptions.EstacionamientoException;

/**
 * Representa una motocicleta a ser estacionada. el precio de las motos se
 * fracciona cada 5 minutos.
 */
public class Motocicleta extends Vehiculo {

	private float precioCincoMinutos;

	/**
	 * Constructor de motocicleta
	 * 
	 * @param patente       patente de la motocicleta
	 * @param horaIngreso   hora del ingreso
	 * @param precioPorHora precio de la hora completa de las motocicletas
	 */
	public Motocicleta(String patente, Hora horaIngreso, float precioPorHora) {
		super(patente, horaIngreso);
		this.setPrecioCincoMinutos(precioPorHora);
		
	}

	private void setPrecioCincoMinutos(float precioPorHora) {
		this.precioCincoMinutos = precioPorHora / Vehiculo.CINCO_MINUTOS_POR_HORA;
	}

	/**
	 * Valida que una patente sea válida para el tipo de vehiculo auto. debe tener
	 * formato de tres números y tres letas por ejemplo '182ABC'.
	 */
	public void validarPatente(String patente) throws EstacionamientoException {

		// TODO COMPLETAR
		// FORMA DE VERIFICAR QUE LA PATENTE TIENE EN FORMATO CORRECTO
		if (!patente.matches(TipoVehiculo.MOTOCICLETA.getRegex())) {
			throw new EstacionamientoException("Patente errónea");
		}

	}

	/**
	 * Calcula el importe de la estadia del auto, recibiendo la hora y minutos de
	 * salida. Debe redondearse a 5 minutos la cantidad de minutos de la estadía, si
	 * la duración da 12 minutos, se deben cobrar 15. si la duración da 7 minutos,
	 * se deben cobrar 10.
	 */

	@Override
	public float calcularImporte(Hora horaEgreso) throws IllegalArgumentException {

		this.validarHoraEgreso(horaEgreso);
		Hora hora = super.calcularTiempoEstadia(horaEgreso);
		float importe = 0;
		int cantHoras =hora.getHora();
		int minutos = this.redondear(hora.getMinuto(), CINCO);
		
		int horasEnMinutos = cantHoras * HORA;
		
		int minutosTotales = horasEnMinutos + minutos;
		
		importe = (minutosTotales / CINCO) * this.precioCincoMinutos;  
		
		return importe;
	}

}
