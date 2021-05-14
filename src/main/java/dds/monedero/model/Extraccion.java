package dds.monedero.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Extraccion extends Movimiento{
  public Extraccion(LocalDate fecha, BigDecimal monto) {
    super.setFecha(fecha);
    super.setMonto(monto);
  }

  @Override
  public Movimiento crearMovimiento(LocalDate fecha, BigDecimal monto){
    return new Extraccion(fecha, monto);
  }

  @Override
  public BigDecimal modificarSaldo(BigDecimal saldo){
    return saldo.subtract(super.getMonto());
  }
}
