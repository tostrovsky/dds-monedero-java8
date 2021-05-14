package dds.monedero.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Deposito extends Movimiento{
  public Deposito(LocalDate fecha, BigDecimal monto) {
    super.setFecha(fecha);
    super.setMonto(monto);
  }

  @Override
  public Movimiento crearMovimiento(LocalDate fecha, BigDecimal monto){
    return new Deposito(fecha, monto);
  }

  @Override
  public BigDecimal modificarSaldo(BigDecimal saldo){
    return saldo.add(super.getMonto());
  }
}
