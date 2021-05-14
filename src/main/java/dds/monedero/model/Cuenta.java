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
    this.repo.getValidador().validarMontoDeposito(cuanto, this.repo);
    BigDecimal monto =  new BigDecimal(cuanto);
    Deposito deposito = new Deposito(LocalDate.now(), monto);
    modificarSaldo(deposito);
    this.repo.agregarMovimiento(deposito);
  }

  public void sacar(double cuanto) {
    this.repo.getValidador().validarMontoExtraccion(cuanto, this.repo, getSaldo());
    BigDecimal monto =  new BigDecimal(cuanto);
    Extraccion extraccion = new Extraccion(LocalDate.now(), monto);
    modificarSaldo(extraccion);
    this.repo.agregarMovimiento(extraccion);
  }

  public RepositorioMovimientos getRepositorioMovimientos(){
    return this.repo;
  }

  public BigDecimal getSaldo() {
    return saldo;
  }

  public void modificarSaldo(Movimiento unMovimiento) {
    this.saldo = unMovimiento.modificarSaldo(saldo);
  }

}
