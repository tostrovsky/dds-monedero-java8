package dds.monedero.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RepositorioMovimientos {
  private List<Movimiento> movimientos = new ArrayList<>();
  private final ValidadorMovimientos validador = new ValidadorMovimientos();

  public void agregarMovimiento(Movimiento unMovimiento) {
    movimientos.add(unMovimiento);
  }

  public double getMontoExtraidoA(LocalDate fecha) {
    return getMovimientos().stream()
        .filter(mov -> mov instanceof Extraccion && mov.getFecha().equals(fecha))
        .mapToDouble(movimiento -> movimiento.getMonto().doubleValue())
        .sum();
  }

  public List<Movimiento> getMovimientos() {
    return movimientos;
  }

  public ValidadorMovimientos getValidador() {
    return validador;
  }

  public void setMovimientos(List<Movimiento> movimientos) {
    this.movimientos = movimientos;
  }

}
