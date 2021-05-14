package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoNegativoException;
import dds.monedero.exceptions.SaldoMenorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Cuenta {

  private BigDecimal saldo;
  private final RepositorioMovimientos repo= new RepositorioMovimientos();

  public Cuenta() {
    saldo = BigDecimal.valueOf(0);
  }

  public Cuenta(double montoInicial) {
    saldo = BigDecimal.valueOf(montoInicial);
  }

  public void poner(double cuanto) {
    validarQueMontoSeaPositivo(cuanto);
    validarQueNoExedaDepositosDiarios();

    new Movimiento(LocalDate.now(), new BigDecimal(cuanto), true).agregateA(this);
  }

  public void sacar(double cuanto) {
    validarQueMontoSeaPositivo(cuanto);
    validarQueExtraccionNoExedaSaldo(cuanto);
    validarQueExtraccionNoExedaLimite(cuanto);

    new Movimiento(LocalDate.now(), new BigDecimal(cuanto), false).agregateA(this);
  }

  public void validarQueMontoSeaPositivo(double cuanto){
    if(cuanto <= 0) throw new MontoNegativoException(cuanto + ": el monto a ingresar debe ser un valor positivo");
  }

  public void validarQueNoExedaDepositosDiarios() {
    int cantidadLimite = 3;
    if (repo.getMovimientos().stream().filter(Movimiento::isDeposito).count() >= cantidadLimite)
      throw new MaximaCantidadDepositosException("Ya excedio los " + cantidadLimite + " depositos diarios");
  }

  public void validarQueExtraccionNoExedaSaldo(double cuanto){
    if (getSaldo().doubleValue() - cuanto < 0) {
      throw new SaldoMenorException("No puede sacar mas de " + getSaldo() + " $");
    }
  }

  public void validarQueExtraccionNoExedaLimite(double cuanto){
    double montoExtraidoHoy = repo.getMontoExtraidoA(LocalDate.now());
    double limite = 1000 - montoExtraidoHoy;
    if (cuanto > limite) {
      throw new MaximoExtraccionDiarioException("No puede extraer mas de $ " + 1000
          + " diarios, límite: " + limite);
    }
  }

  public RepositorioMovimientos getRepositorioMovimientos(){
    return this.repo;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void setSaldo(BigDecimal saldo) {
    this.saldo = saldo;
  }

}
