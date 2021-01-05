/**
 * 
 */
package ar.edu.ort.tp1.parcial2.entidades;

import java.time.Duration;
import java.time.LocalTime;

import ar.edu.ort.tp1.parcial2.exceptions.EstacionamientoException;

/**
 * Veh�culos que se pueden estacionar en un <code>Estacionamiento</code>
 */
public abstract class Vehiculo {

	protected static final int HORA = 60;
	protected static final int MEDIA_HORA = 30;
	protected static final int CINCO = 5;
	protected static final int CINCO_MINUTOS_POR_HORA = 12;
	protected static final int DIEZ = 10;
	protected static final int DIEZ_MINUTOS_POR_HORA = 6;
	protected static final String REGEX_SOLO_3_NUMEROS = "^\\d{3}$";
	protected static final String REGEX_SOLO_LETRAS = "^[a-zA-Z]+$";

	//estos se los agregamos
	private String patente;
	private Hora horaIngreso;

	/**
	 * Constructor del veh�culo
	 * 
	 * @param patente     Patente
	 * @param horaIngreso Hora del horario de ingreso
	 */
	public Vehiculo(String patente, Hora horaIngreso) {

		this.setPatente(patente);
		this.setHoraIngreso(horaIngreso);

	}

	/**
	 * Setea el horario de ingreso verificando que sea v�lido
	 * 
	 * @param horaIngreso
	 * @throws EstacionamientoException Si el horario es erroneo
	 */
	private void setHoraIngreso(Hora horaIngreso) throws EstacionamientoException {

		this.validarHora(horaIngreso);
		this.horaIngreso = horaIngreso;
	}

	/**
	 * Valida que una hora sea v�lida (hora entre 0 y 23 y minutos entre 0 y 59 todo
	 * inclusive)
	 * 
	 * @param hora
	 * @throws EstacionamientoException
	 */
	//simplemente valido que la hora sea correcta, no mas que eso
	protected void validarHora(Hora hora) throws EstacionamientoException {

		if (hora.getHora() < 0 || hora.getHora() > 23) {
			throw new EstacionamientoException("Hora de ingreso inv�lidos");
		}

		if (hora.getMinuto() < 0 || hora.getMinuto() > 59) {
			throw new EstacionamientoException("Minutos de ingreso inv�lidos");
		}

	}

	/**
	 * Setter de patente, que debe ser validada segun cada tipo de veh�culo
	 * 
	 * @param patente
	 */
	private void setPatente(String patente) {
//polirmofismo, ese metodo en esta clase esta vacia
		this.validarPatente(patente);
		this.patente = patente;

	}

	/**
	 * Valida la patente en forma especifica para cada tipo de veh�culo. Para
	 * realizar la validaci�n pueden utilizar el m�todo
	 * <code>String.matches(regex)</code> utilizando las regex provistas como
	 * constantes REGEX_SOLO_3_NUMEROS y REGEX_SOLO_3_NUMEROS REGEX_SOLO_LETRAS
	 * 
	 * @param patente
	 * @throws EstacionamientoException si la patente es inv�lida
	 */
	protected abstract void validarPatente(String patente) throws EstacionamientoException;

	/**
	 * Calcula el importe que debe abonar el veh�culo en base a su tipo, el horario
	 * de ingreso que posee y el horario de egreso indicado Cada veh�culo debe
	 * poseer su algoritmo de calculo en base al fraccionamiento de minutos
	 * 
	 * @param horaEgreso
	 * @return
	 * @throws EstacionamientoException si existe algun problema al calcular el
	 *                                  importe.
	 */
	protected abstract float calcularImporte(Hora horaEgreso) throws EstacionamientoException;

	/**
	 * Metodo helper que calcula el tiempo de la estadia, siendo devuelto en un
	 * objeto del tipo {@link Hora}
	 * 
	 * @param horaSalida
	 * @return
	 */
	//el horario de ingreso lo recibi en el constructor, asi que ya tengo los daos necesarios
	public Hora calcularTiempoEstadia(Hora horaSalida) {
		LocalTime horarioIngreso = LocalTime.of(this.horaIngreso.getHora(), this.horaIngreso.getMinuto());
		LocalTime horarioEgreso = LocalTime.of(horaSalida.getHora(), horaSalida.getMinuto());
		Duration duration = Duration.between(horarioIngreso, horarioEgreso);

		return new Hora((int) duration.toHours(), (int) duration.toMinutes() % 60);
	}

	public String getPatente() {
		return patente;
	}

	/**
	 * Redondea el n�mero de minutos de egreso a una base, si la base es 5, y el nro
	 * de minutos es 2 devolver� 5, si el nro de minutos es 7 devolvera 10
	 * 
	 * @param minutos
	 * @param i
	 * @return
	 */
	protected int redondear(int minutos, int base) {
		return minutos + (base - minutos % base);
	}

	/**
	 * @param horasEgreso
	 * @param minutosEgreso
	 */
	//se fija si el horario de egreso es mayor al de ingreso
	public void validarHoraEgreso(Hora hora) throws EstacionamientoException {
		LocalTime horarioIngreso = LocalTime.of(this.horaIngreso.getHora(), this.horaIngreso.getMinuto());
		LocalTime horarioEgreso = LocalTime.of(hora.getHora(), hora.getMinuto());

		// Sirve para ver si el horario de egreso es anterior al de ingreso
		if (horarioEgreso.compareTo(horarioIngreso) < 0) {
			throw new EstacionamientoException("Horario de egreso anterior al de ingreso");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Vehiculo [patente=" + patente + ", horaIngreso=" + horaIngreso + "]";
	}

}
