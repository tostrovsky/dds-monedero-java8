package dds.monedero.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public abstract class Movimiento {
  private LocalDate fecha;
  private BigDecimal monto;
  private boolean esDeposito;

  public abstract Movimiento crearMovimiento(LocalDate fecha, BigDecimal monto);

  public void validarMonto(LocalDate fecha, BigDecimal monto){}

  public boolean esDeLaFecha(LocalDate fecha) {
    return this.fecha.equals(fecha);
  }

  public abstract BigDecimal modificarSaldo(BigDecimal saldo);

  public BigDecimal getMonto() { return monto; }
  public LocalDate getFecha() { return fecha; }
  public void setFecha(LocalDate fecha) { this.fecha = fecha; }
  public void setMonto(BigDecimal monto) { this.monto = monto; }
}
